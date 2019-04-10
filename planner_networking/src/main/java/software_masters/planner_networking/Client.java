package software_masters.planner_networking;

import java.rmi.RemoteException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */

public class Client
{

	/** The cookie. */
	private String cookie;
	
	/** The curr plan file. */
	private PlanFile currPlanFile;
	
	/** The curr node. */
	private Node currNode;
	
	/** The server. */
	private Server server;
	
	/** The observers. */
	private ArrayList<Observer> observers;
	
	/**
	 * Instantiates a new client.
	 *
	 * @param server the server
	 */
	public Client(Server server)
	{
		this.server = server;
		observers = new ArrayList<Observer>();
	}

	/**
	 * Login.
	 *
	 * @param username the username
	 * @param password the password
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	public void login(String username, String password) throws IllegalArgumentException, RemoteException
	{
		this.currPlanFile = null;
		this.currNode = null;
		this.cookie = server.logIn(username, password);
	}

	/**
	 * Gets the plan.
	 *
	 * @param year the year
	 * @return the plan
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	public void getPlan(String year) throws IllegalArgumentException, RemoteException
	{
		this.currPlanFile = server.getPlan(year, this.cookie);
		this.currNode = this.currPlanFile.getPlan().getRoot();
	}

	/**
	 * Gets the plan outline.
	 *
	 * @param name the name
	 * @return the plan outline
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	public void getPlanOutline(String name) throws IllegalArgumentException, RemoteException
	{
		this.currPlanFile = server.getPlanOutline(name, this.cookie);
		this.currNode = this.currPlanFile.getPlan().getRoot();
	}

	/**
	 * Gets the all years.
	 *
	 * @return the all years
	 */
	public ArrayList<String> getAllYears()
	{
		try
		{
			return server.getAllYears(this.cookie);
		} catch (IllegalArgumentException | RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Push plan.
	 *
	 * @param plan the plan
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	public void pushPlan(PlanFile plan) throws IllegalArgumentException, RemoteException
	{
		server.savePlan(plan, this.cookie);
	}

	/**
	 * Adds the user.
	 *
	 * @param username the username
	 * @param password the password
	 * @param departmentName the department name
	 * @param isAdmin the is admin
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	public void addUser(String username, String password, String departmentName, boolean isAdmin)
			throws IllegalArgumentException, RemoteException
	{
		server.addUser(username, password, departmentName, isAdmin, this.cookie);
	}

	/**
	 * Flag plan.
	 *
	 * @param departmentName the department name
	 * @param year the year
	 * @param canEdit the can edit
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	public void flagPlan(String departmentName, String year, boolean canEdit)
			throws IllegalArgumentException, RemoteException
	{
		server.flagPlan(departmentName, year, canEdit, this.cookie);

	}

	/**
	 * Adds the department.
	 *
	 * @param departmentName the department name
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	public void addDepartment(String departmentName) throws IllegalArgumentException, RemoteException
	{
		server.addDepartment(departmentName, this.cookie);

	}

	/**
	 * Adds the branch.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	public void addBranch() throws IllegalArgumentException, RemoteException
	{
		this.currPlanFile.getPlan().addNode(this.currNode.getParent());
	}

	/**
	 * Removes the branch.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void removeBranch() throws IllegalArgumentException
	{
		Node temp = this.currNode.getParent();
		this.currPlanFile.getPlan().removeNode(this.currNode);
		this.currNode = temp.getChildren().get(0);
	}

	/**
	 * Edits the data.
	 *
	 * @param data the data
	 */
	public void editData(String data)
	{
		this.currNode.setData(data);
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData()
	{
		return this.currNode.getData();
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(String year)
	{
		this.currPlanFile.setYear(year);
	}

	/**
	 * Gets the cookie.
	 *
	 * @return the cookie
	 */
	public String getCookie()
	{
		return cookie;
	}

	/**
	 * Sets the cookie.
	 *
	 * @param cookie the new cookie
	 */
	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	/**
	 * Gets the curr plan file.
	 *
	 * @return the curr plan file
	 */
	public PlanFile getCurrPlanFile()
	{
		return currPlanFile;
	}

	/**
	 * Sets the curr plan file.
	 *
	 * @param currPlanFile the new curr plan file
	 */
	public void setCurrPlanFile(PlanFile currPlanFile)
	{
		this.currPlanFile = currPlanFile;
	}

	/**
	 * Gets the curr node.
	 *
	 * @return the curr node
	 */
	public Node getCurrNode()
	{
		return currNode;
	}

	/**
	 * Sets the curr node.
	 *
	 * @param currNode the new curr node
	 */
	public void setCurrNode(Node currNode)
	{
		this.currNode = currNode;
	}

	/**
	 * Gets the server.
	 *
	 * @return the server
	 */
	public Server getServer()
	{
		return server;
	}

	/**
	 * Sets the server.
	 *
	 * @param server the new server
	 */
	public void setServer(Server server)
	{
		this.server = server;
	}
	
	/**
	 * Register.
	 *
	 * @param observer the observer
	 * @return true, if successful
	 */
	public boolean register(Observer observer)
	{
		if (observer == null)
		{
			return false;
		}
		observers.add(observer);
		return true;
	}
	
	/**
	 * Deregistry.
	 *
	 * @param observer the observer
	 * @return true, if successful
	 */
	public boolean deregistry(Observer observer)
	{
		return observers.remove(observer);
	}
	
	
	/**
	 * Notify observers.
	 */
	public void notifyObservers()
	{
		for (Observer observer : observers)
		{
			observer.respond(currPlanFile.getPlan().getRoot());
		}
	}

}
