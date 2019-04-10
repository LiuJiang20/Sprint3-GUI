package software_masters.planner_networking;

import java.rmi.RemoteException;

// TODO: Auto-generated Javadoc
/**
 * The Class IowaState.
 */
public class IowaState extends Plan
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3096239674948462908L;

	/**
	 * Instantiates a new iowa state.
	 *
	 * @throws RemoteException the remote exception
	 */
	public IowaState() throws RemoteException
	{
		super();
	}

	// set strings for default stages IowaState plan
	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Plan#setDefaultStrings()
	 */
	protected void setDefaultStrings()
	{
		this.getList().add("Vision");
		this.getList().add("Mission");
		this.getList().add("Core Value");
		this.getList().add("Strategy");
		this.getList().add("Goal");
		this.getList().add("Objective");
		this.getList().add("Action Plan");
		this.getList().add("Assessment");
	}

	/* (non-Javadoc)
	 * @see software_masters.planner_networking.Plan#addNode(software_masters.planner_networking.Node)
	 */
	public boolean addNode(Node parent) throws RemoteException, IllegalArgumentException
	{
		if (parent.getName() == "Vision" || parent == null)
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

	/* (non-Javadoc)
	 * @see software_masters.planner_networking.Plan#removeNode(software_masters.planner_networking.Node)
	 */
	public boolean removeNode(Node nodeRemove) throws IllegalArgumentException
	{
		if (nodeRemove.getName() == this.getRoot().getName() || nodeRemove.getParent().getChildren().size() == 1
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
