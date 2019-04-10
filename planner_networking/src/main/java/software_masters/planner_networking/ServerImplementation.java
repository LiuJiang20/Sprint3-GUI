package software_masters.planner_networking;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerImplementation.
 */

public class ServerImplementation implements Server
{

	/** The login map. */
	private ConcurrentHashMap<String, Account> loginMap = new ConcurrentHashMap<String, Account>();
	
	/** The cookie map. */
	private ConcurrentHashMap<String, Account> cookieMap = new ConcurrentHashMap<String, Account>();
	
	/** The department map. */
	private ConcurrentHashMap<String, Department> departmentMap = new ConcurrentHashMap<String, Department>();
	
	/** The plan template map. */
	private ConcurrentHashMap<String, PlanFile> planTemplateMap = new ConcurrentHashMap<String, PlanFile>();

	/**
	 * Instantiates a new server implementation.
	 *
	 * @throws RemoteException the remote exception
	 */
	public ServerImplementation() throws RemoteException
	{
		Department dpt = new Department();
		this.departmentMap.put("default", dpt);

		Account admin = new Account("admin", "0", dpt, true);
		Account user = new Account("user", "1", dpt, false);
		this.loginMap.put("admin", admin);
		this.loginMap.put("user", user);
		this.cookieMap.put("0", admin);
		this.cookieMap.put("1", user);

		Plan plan = new Centre();
		plan.setName("Centre_Plan_1");
		PlanFile planfile = new PlanFile("2019", true, plan);
		dpt.addPlan("2019", planfile);

		Plan plan2 = new Centre();
		plan2.setName("Centre_Plan_2");
		PlanFile planfile2 = new PlanFile("2018", false, plan2);
		dpt.addPlan("2018", planfile2);
		
		
		Plan defaultCentre = new Centre();
		Plan defaultVMOSA = new VMOSA();
		this.planTemplateMap.put("Centre", new PlanFile(null, true, defaultCentre));
		this.planTemplateMap.put("VMOSA", new PlanFile(null, true, defaultVMOSA));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#logIn(java.lang.String,
	 * java.lang.String)
	 */

	public String logIn(String username, String password)
	{
		if (!this.loginMap.containsKey(username))// checks username is valid
		{
			throw new IllegalArgumentException("Invalid username and/or password");

		}
		Account userAccount = this.loginMap.get(username);

		String cookie = userAccount.testCredentials(password);
		return cookie;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#getPlan(java.lang.String,
	 * java.lang.String)
	 */

	public PlanFile getPlan(String year, String cookie)
	{
		cookieChecker(cookie);// checks that cookie is valid

		Account userAccount = this.cookieMap.get(cookie);
		Department department = userAccount.getDepartment();
		if (!department.containsPlan(year))
		{
			throw new IllegalArgumentException("Plan doesn't exist within your department");

		}
		return department.getPlan(year);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * software_masters.planner_networking.Server#getPlanOutline(java.lang.String,
	 * java.lang.String)
	 */

	public PlanFile getPlanOutline(String name, String cookie)
	{
		cookieChecker(cookie);// checks that cookie is valid

		if (!this.planTemplateMap.containsKey(name))// checks plan template exists
		{
			throw new IllegalArgumentException("Plan outline doesn't exist");

		}
		return this.planTemplateMap.get(name);

	}

	
	


	/* (non-Javadoc)
	 * @see software_masters.planner_networking.Server#getAllYears(java.lang.String)
	 */
	@Override
	public ArrayList<String> getAllYears(String cookie) throws IllegalArgumentException, RemoteException
	{
		cookieChecker(cookie);
		Account userAccount = cookieMap.get(cookie);
		ArrayList<String> years = new ArrayList<String>();
		
		for (String string : userAccount.getDepartment().getPlanFileMap().keySet())
		{
			PlanFile file = userAccount.getDepartment().getPlanFileMap().get(string);
			years.add(file.getYear());
		}
		return years;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#savePlan(software_masters.
	 * planner_networking.PlanFile, java.lang.String)
	 */
	public void savePlan(PlanFile plan, String cookie)
	{
		cookieChecker(cookie);// checks that cookie is valid

		if (plan.getYear() == null)// checks planFile is given a year
		{
			throw new IllegalArgumentException("This planFile needs a year!");
		}

		Account userAccount = this.cookieMap.get(cookie);
		Department dept = userAccount.getDepartment();

		if (dept.containsPlan(plan.getYear()))
		{
			PlanFile oldPlan = dept.getPlan(plan.getYear());
			if (!oldPlan.isCanEdit())// checks planFile is editable
			{
				throw new IllegalArgumentException("Not allowed to edit this plan");
			}

		}
		dept.addPlan(plan.getYear(), plan);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#addUser(java.lang.String,
	 * java.lang.String, java.lang.String, boolean, java.lang.String)
	 */

	public void addUser(String username, String password, String departmentName, boolean isAdmin, String cookie)
	{
		cookieChecker(cookie);// checks that cookie is valid and that user is admin
		adminChecker(cookie);

		departmentChecker(departmentName);

		String newCookie = cookieMaker();
		Department newDept = this.departmentMap.get(departmentName);
		Account newAccount = new Account(password, newCookie, newDept, isAdmin);
		this.loginMap.put(username, newAccount);
		this.cookieMap.put(newAccount.getCookie(), newAccount);

	}

	/**
	 * Cookie maker.
	 *
	 * @return the string
	 */
	private String cookieMaker()
	{
		int leftLimit = 33; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 25;
		Random random = new Random();
		String generatedString;
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++)
		{
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		generatedString = buffer.toString();

		while (this.cookieMap.containsKey(generatedString))
		{
			buffer = new StringBuilder(targetStringLength);
			for (int i = 0; i < targetStringLength; i++)
			{
				int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
				buffer.append((char) randomLimitedInt);
			}
			generatedString = buffer.toString();
		}
		return generatedString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#flagPlan(java.lang.String,
	 * java.lang.String, boolean, java.lang.String)
	 */

	public void flagPlan(String departmentName, String year, boolean canEdit, String cookie)
	{
		cookieChecker(cookie);// checks that cookie is valid and that user is admin
		adminChecker(cookie);
		departmentChecker(departmentName);

		Department dept = this.departmentMap.get(departmentName);
		if (!dept.containsPlan(year))
		{
			throw new IllegalArgumentException("Plan doesn't exist");

		}

		dept.getPlan(year).setCanEdit(canEdit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * software_masters.planner_networking.Server#addDepartment(java.lang.String,
	 * java.lang.String)
	 */

	public void addDepartment(String departmentName, String cookie)
	{
		cookieChecker(cookie);// checks that cookie is valid and that user is admin
		adminChecker(cookie);

		this.departmentMap.put(departmentName, new Department());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * software_masters.planner_networking.Server#addPlanTemplate(java.lang.String,
	 * software_masters.planner_networking.PlanFile)
	 */

	public void addPlanTemplate(String name, PlanFile plan)
	{
		this.planTemplateMap.put(name, plan);
	}

	/**
	 * Load.
	 *
	 * @return the server implementation
	 * @throws FileNotFoundException the file not found exception
	 */
	public static ServerImplementation load() throws FileNotFoundException
	{
		String filepath = "PlannerServer.serv";
		XMLDecoder decoder = null;
		decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filepath)));
		ServerImplementation server = (ServerImplementation) decoder.readObject();
		decoder.close();
		return server;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#save()
	 */

	public void save()
	{
		String filename = "PlannerServer.serv";
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File " + filename);
		}
		encoder.writeObject(this);
		encoder.close();

	}

	/**
	 * Cookie checker.
	 *
	 * @param cookie the cookie
	 */
	private void cookieChecker(String cookie)
	{
		if (!this.cookieMap.containsKey(cookie))
		{
			throw new IllegalArgumentException("Need to log in");

		}

	}

	/**
	 * Admin checker.
	 *
	 * @param cookie the cookie
	 */
	private void adminChecker(String cookie)
	{
		if (!this.cookieMap.get(cookie).isAdmin())// Checks that user is admin
		{
			throw new IllegalArgumentException("You're not an admin");
		}
	}

	/**
	 * Department checker.
	 *
	 * @param name the name
	 */
	private void departmentChecker(String name)
	{
		if (!this.departmentMap.containsKey(name))
		{
			throw new IllegalArgumentException("Deparment doesn't exist");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#getLoginMap()
	 */

	public ConcurrentHashMap<String, Account> getLoginMap()
	{
		return loginMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * software_masters.planner_networking.Server#setLoginMap(java.util.concurrent.
	 * ConcurrentHashMap)
	 */

	public void setLoginMap(ConcurrentHashMap<String, Account> loginMap)
	{
		this.loginMap = loginMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#getCookieMap()
	 */

	public ConcurrentHashMap<String, Account> getCookieMap()
	{
		return cookieMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * software_masters.planner_networking.Server#setCookieMap(java.util.concurrent.
	 * ConcurrentHashMap)
	 */

	public void setCookieMap(ConcurrentHashMap<String, Account> cookieMap)
	{
		this.cookieMap = cookieMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#getDepartmentMap()
	 */

	public ConcurrentHashMap<String, Department> getDepartmentMap()
	{
		return departmentMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#setDepartmentMap(java.util.
	 * concurrent.ConcurrentHashMap)
	 */

	public void setDepartmentMap(ConcurrentHashMap<String, Department> departmentMap)
	{
		this.departmentMap = departmentMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#getPlanTemplateMap()
	 */

	public ConcurrentHashMap<String, PlanFile> getPlanTemplateMap()
	{
		return planTemplateMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#setPlanTemplateMap(java.util.
	 * concurrent.ConcurrentHashMap)
	 */

	public void setPlanTemplateMap(ConcurrentHashMap<String, PlanFile> planTemplateMap)
	{
		this.planTemplateMap = planTemplateMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Server#equals(java.lang.Object)
	 */

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerImplementation other = (ServerImplementation) obj;
		if (cookieMap == null)
		{
			if (other.cookieMap != null)
				return false;
		} else if (!ServerImplementation.<String, Account>hashesEqual(cookieMap, other.cookieMap))
			return false;
		if (departmentMap == null)
		{
			if (other.departmentMap != null)
				return false;
		} else if (!ServerImplementation.<String, Department>hashesEqual(departmentMap, other.departmentMap))
			return false;
		if (loginMap == null)
		{
			if (other.loginMap != null)
				return false;
		} else if (!ServerImplementation.<String, Account>hashesEqual(loginMap, other.loginMap))
			return false;
		if (planTemplateMap == null)
		{
			if (other.planTemplateMap != null)
				return false;
		} else if (!ServerImplementation.<String, PlanFile>hashesEqual(planTemplateMap, other.planTemplateMap))
			return false;
		return true;
	}

	/**
	 * Hashes equal.
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map1 the map 1
	 * @param map2 the map 2
	 * @return true, if successful
	 */
	private static <K, V> boolean hashesEqual(ConcurrentHashMap<K, V> map1, ConcurrentHashMap<K, V> map2)
	{
		for (Enumeration<K> keyList = map1.keys(); keyList.hasMoreElements();)
		{
			K key = keyList.nextElement();
			if (!map1.containsKey(key))
				return false;
			if (!map2.containsKey(key))
				return false;
			if (!map1.get(key).equals(map2.get(key)))
				return false;
		}
		return true;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws RemoteException the remote exception
	 */
	public static void main(String[] args) throws RemoteException
	{
		System.out.println("Start Server");
		ServerImplementation server = new ServerImplementation();
		server.save();
		Registry registry;
		try
		{
			registry = LocateRegistry.createRegistry(1060);
			server = ServerImplementation.load();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return;
		}

		Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
		try
		{
			registry.bind("PlannerServer", stub);
		} catch (java.rmi.AlreadyBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
