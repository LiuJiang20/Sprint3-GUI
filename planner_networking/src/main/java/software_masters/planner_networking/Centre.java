package software_masters.planner_networking;

import java.rmi.RemoteException;

// TODO: Auto-generated Javadoc
/**
 * The Class Centre.
 */
public class Centre extends Plan
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8094008350302564337L;

	/**
	 * Instantiates a new centre.
	 *
	 * @throws RemoteException the remote exception
	 */
	public Centre() throws RemoteException
	{
		super();
	}

	// set strings for default stages Centre plan
	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Plan#setDefaultStrings()
	 */
	protected void setDefaultStrings()
	{
		this.getList().add("Mission");
		this.getList().add("Goal");
		this.getList().add("Learning Objective");
		this.getList().add("Assessment Process");
		this.getList().add("Results");
	}

	/* (non-Javadoc)
	 * @see software_masters.planner_networking.Plan#addNode(software_masters.planner_networking.Node)
	 */
	public boolean addNode(Node parent) throws RemoteException, IllegalArgumentException
	{
		if (parent == null)
		{
			throw new IllegalArgumentException("Cannot add to this parent");
		} else
		{
			for (int i = (this.getList().indexOf(parent.getName())) + 1; i < this.getList().size(); i++)
			{

				Node newNode = new Node(parent, this.getList().get(i), null, null);

				parent.addChild(newNode);
				parent = newNode;

			}
			return true;
		}
	}

	// remove a node if it is allowed to be removed
	// cannot be removed if it is the only child of its parent
	// or if it is the root node
	/* (non-Javadoc)
	 * @see software_masters.planner_networking.Plan#removeNode(software_masters.planner_networking.Node)
	 */
	public boolean removeNode(Node nodeRemove) throws IllegalArgumentException
	{
		if ((nodeRemove.getName() == this.getRoot().getName()) || nodeRemove.getParent().getChildren().size() == 1
				|| nodeRemove == null)
		{

			throw new IllegalArgumentException("Cannot remove this node");

		} else
		{
			nodeRemove.getParent().removeChild(nodeRemove);
			nodeRemove.setParent(null);
			return true;

		}
	}

}