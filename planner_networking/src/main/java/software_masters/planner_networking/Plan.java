package software_masters.planner_networking;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Plan.
 */
public abstract class Plan implements Serializable// extends UnicastRemoteObject
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1538776243780396317L;
	
	/** The name. */
	private String name;
	
	/** The default nodes. */
	private ArrayList<String> defaultNodes = new ArrayList<String>();
	
	/** The root. */
	private Node root;

	/**
	 * Instantiates a new plan.
	 *
	 * @throws RemoteException the remote exception
	 */
	public Plan() throws RemoteException
	{
		defaultNodes = new ArrayList<String>();
		setDefaultStrings();
		addDefaultNodes();
	}

	/**
	 * Sets the default strings.
	 */
	// creates string array node hierarchy in subclass
	abstract protected void setDefaultStrings();

	/**
	 * Adds the default nodes.
	 *
	 * @throws RemoteException the remote exception
	 */
	protected void addDefaultNodes() throws RemoteException
	{
		root = new Node(null, defaultNodes.get(0), null, null);
		Node newParent = new Node(root, defaultNodes.get(1), null, null);
		root.addChild(newParent);
		addNode(newParent);
	}

	/**
	 * Adds the node.
	 *
	 * @param parent the parent
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	abstract public boolean addNode(Node parent) throws RemoteException, IllegalArgumentException;

	/**
	 * Removes the node.
	 *
	 * @param Node the node
	 * @return true, if successful
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	abstract public boolean removeNode(Node Node) throws IllegalArgumentException;

	/**
	 * Sets the node data.
	 *
	 * @param node the node
	 * @param data the data
	 */
	public void setNodeData(Node node, String data)
	{
		node.setData(data);
	}

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public Node getRoot()
	{
		return root;
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<String> getList()
	{
		return defaultNodes;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plan other = (Plan) obj;
		if (defaultNodes == null)
		{
			if (other.defaultNodes != null)
				return false;
		} else if (!defaultNodes.equals(other.defaultNodes))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (root == null)
		{
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}

}