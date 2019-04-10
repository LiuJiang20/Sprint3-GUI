package software_masters.planner_networking;

import java.lang.ref.Cleaner.Cleanable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */

public class Controller
{
	
	/** The client. */
	Client client;


	/**
	 * Instantiates a new controller.
	 *
	 * @throws RemoteException the remote exception
	 * @throws NotBoundException the not bound exception
	 */
	public Controller() throws RemoteException, NotBoundException
	{
		String host = "10.14.1.82";
		String name = "PlannerServer";
		String username = "user";
		String password = "user";
		String year = "2019";
		 
	
			Registry registry;
			registry = LocateRegistry.getRegistry(host,1060);
			System.out.println("Get here!");
			Server server;
			server = (Server) registry.lookup(name);
			client = new Client(server);
			client.login(username, password);
			client.getPlan(year);
			System.out.println(client);
			System.out.println("Current Plan file is : "+client.getCurrPlanFile());
		
	}
	
	/**
	 * Gets the root node.
	 *
	 * @return the root node
	 */
	public Node getRootNode()
	{
		return client.getCurrPlanFile().getPlan().getRoot();
	}
	
	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public String getYear()
	{
		return client.getCurrPlanFile().getYear();
	}
	
	/**
	 * Adds the node.
	 *
	 * @param parent the parent
	 * @return the string
	 */
	public String addNode(Node parent)
	{
		try
		{
			client.getCurrPlanFile().getPlan().addNode(parent);
		} catch (RemoteException | IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			return e.toString();
		}
		client.notifyObservers();
		return null;
	}
	
	/**
	 * Delete node.
	 *
	 * @param removedNode the removed node
	 * @return the string
	 */
	public String deleteNode(Node removedNode)
	{
		try
		{
			client.getCurrPlanFile().getPlan().removeNode(removedNode);
		} catch (Exception e)
		{
			return e.toString();
		}
		client.notifyObservers();
		return null;
	}
	
	/**
	 * Push to server.
	 *
	 * @return the string
	 */
	public String pushToServer()
	{
		try
		{
			client.pushPlan(client.getCurrPlanFile());
		} catch (IllegalArgumentException | RemoteException e)
		{
			// TODO Auto-generated catch block
			return e.toString();
		}
		return null;
	}
	
	/**
	 * Editable.
	 *
	 * @return true, if successful
	 */
	public boolean editable()
	{
		return client.getCurrPlanFile().isCanEdit();
	}
	
	/**
	 * Register.
	 *
	 * @param observer the observer
	 */
	public void register(Observer observer)
	{
		client.register(observer);
	}
}
