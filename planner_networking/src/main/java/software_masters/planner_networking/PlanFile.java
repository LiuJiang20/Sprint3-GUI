package software_masters.planner_networking;

import java.io.Serializable;
import java.rmi.RemoteException;

// TODO: Auto-generated Javadoc
/**
 * The Class PlanFile.
 */
public class PlanFile implements Serializable // extends UnicastRemoteObject
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8679415216780269027L;
	
	/** The year. */
	private String year;
	
	/** The can edit. */
	private boolean canEdit;
	
	/** The plan. */
	private Plan plan;

	/**
	 * Instantiates a new plan file.
	 *
	 * @param year the year
	 * @param canEdit the can edit
	 * @param plan the plan
	 * @throws RemoteException the remote exception
	 */
	public PlanFile(String year, boolean canEdit, Plan plan) throws RemoteException
	{
		this.year = year;
		this.canEdit = canEdit;
		this.plan = plan;
	}

	/**
	 * Instantiates a new plan file.
	 *
	 * @throws RemoteException the remote exception
	 */
	public PlanFile() throws RemoteException
	{
		this.year = null;
		this.canEdit = false;
		this.plan = null;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(String year)
	{
		this.year = year;
	}

	/**
	 * Checks if is can edit.
	 *
	 * @return true, if is can edit
	 */
	public boolean isCanEdit()
	{
		return canEdit;
	}

	/**
	 * Sets the can edit.
	 *
	 * @param canEdit the new can edit
	 */
	public void setCanEdit(boolean canEdit)
	{
		this.canEdit = canEdit;
	}

	/**
	 * Gets the plan.
	 *
	 * @return the plan
	 */
	public Plan getPlan()
	{
		return plan;
	}

	/**
	 * Sets the plan.
	 *
	 * @param plan the new plan
	 */
	public void setPlan(Plan plan)
	{
		this.plan = plan;
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
		PlanFile other = (PlanFile) obj;
		if (canEdit != other.canEdit)
			return false;
		if (plan == null)
		{
			if (other.plan != null)
				return false;
		} else if (!plan.equals(other.plan))
			return false;
		if (year == null)
		{
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

}
