package software_masters.planner_networking;

import java.rmi.RemoteException;

// TODO: Auto-generated Javadoc
/**
 * The Class VMOSA.
 */
public class VMOSA extends Plan
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8514352878071159404L;

	/**
	 * Instantiates a new vmosa.
	 *
	 * @throws RemoteException the remote exception
	 */
	public VMOSA() throws RemoteException
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see software_masters.planner_networking.Plan#setDefaultStrings()
	 */
	protected void setDefaultStrings()
	{
		this.getList().add("Vision");
		this.getList().add("Mission");
		this.getList().add("Objective");
		this.getList().add("Strategy");
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