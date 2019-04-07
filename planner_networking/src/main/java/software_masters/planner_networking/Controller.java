package software_masters.planner_networking;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller
{
	Client client;
	

	public Controller()
	{
		String host = "";
		String name = "";
		String username = "";
		String password = "";
		String year = "";
		 
		try
		{
			Registry registry;
			registry = LocateRegistry.getRegistry(host);
			Server server;
			server = (Server) registry.lookup(name);
			client = new Client(server);
			client.login(username, password);
			client.getPlan(year);
		} catch (RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Node getRootNode()
	{
		return client.getCurrPlanFile().getPlan().getRoot();
	}
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
		return null;
	}
	
	public String deleteNode(Node removedNode)
	{
		try
		{
			client.getCurrPlanFile().getPlan().removeNode(removedNode);
		} catch (Exception e)
		{
			return e.toString();
		}
		return null;
	}
	
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
	
	
}
