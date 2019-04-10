package software_masters.planner_networking;

import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.RemoteException;

// TODO: Auto-generated Javadoc
/**
 * The Class Node.
 */

public class Node implements Serializable
{
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return name ;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5908372020728915437L;
	
	/** The parent. */
	private Node parent;
	
	/** The name. */
	private String name;
	
	/** The data. */
	private String data;
	
	/** The children. */
	private ArrayList<Node> children = new ArrayList<Node>();

	// constructor is data is not known
	/**
	 * Instantiates a new node.
	 *
	 * @param parent the parent
	 * @param name the name
	 * @param data the data
	 * @param child the child
	 * @throws RemoteException the remote exception
	 */
	public Node(Node parent, String name, String data, ArrayList<Node> child) throws RemoteException
	{
		this.name = name;
		this.parent = parent;
		this.data = data;

	}

	/**
	 * Instantiates a new node.
	 *
	 * @throws RemoteException the remote exception
	 */
	// empty constructor for XML
	public Node() throws RemoteException
	{
		this(null, "blank", "empty", null);
	}

	// Getter and setters
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

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData()
	{
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(String data)
	{
		this.data = data;
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Node getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public ArrayList<Node> getChildren()
	{
		return children;
	}

	//

	// add a Node child to another node
	/**
	 * Adds the child.
	 *
	 * @param child the child
	 */
	public void addChild(Node child)
	{
		this.children.add(child);
	}

	// remove child node from a node's children list
	/**
	 * Removes the child.
	 *
	 * @param child the child
	 */
	public void removeChild(Node child)
	{
		this.children.remove(child);
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
		Node other = (Node) obj;
		if (children == null)
		{
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (data == null)
		{
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null)
		{
			if (other.parent != null)
				return false;
		}
		return true;
	}

}
