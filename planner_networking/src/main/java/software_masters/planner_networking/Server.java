package software_masters.planner_networking;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Auto-generated Javadoc
/**
 * The Interface Server.
 */

public interface Server extends Remote
{

	/**
	 * Log in.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the string
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	String logIn(String username, String password) throws IllegalArgumentException, RemoteException;

	/**
	 * Gets the plan.
	 *
	 * @param year the year
	 * @param cookie the cookie
	 * @return the plan
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	PlanFile getPlan(String year, String cookie) throws IllegalArgumentException, RemoteException;

	/**
	 * Gets the plan outline.
	 *
	 * @param name the name
	 * @param cookie the cookie
	 * @return the plan outline
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	PlanFile getPlanOutline(String name, String cookie) throws IllegalArgumentException, RemoteException;

	
	/**
	 * Gets the all years.
	 *
	 * @param cookie the cookie
	 * @return the all years
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	ArrayList<String> getAllYears(String cookie)throws IllegalArgumentException, RemoteException;
	
	/**
	 * Save plan.
	 *
	 * @param plan the plan
	 * @param cookie the cookie
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	void savePlan(PlanFile plan, String cookie) throws IllegalArgumentException, RemoteException;

	/**
	 * Adds the user.
	 *
	 * @param username the username
	 * @param password the password
	 * @param departmentName the department name
	 * @param isAdmin the is admin
	 * @param cookie the cookie
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	void addUser(String username, String password, String departmentName, boolean isAdmin, String cookie)
			throws IllegalArgumentException, RemoteException;

	/**
	 * Flag plan.
	 *
	 * @param departmentName the department name
	 * @param year the year
	 * @param canEdit the can edit
	 * @param cookie the cookie
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	void flagPlan(String departmentName, String year, boolean canEdit, String cookie)
			throws IllegalArgumentException, RemoteException;

	/**
	 * Adds the department.
	 *
	 * @param departmentName the department name
	 * @param cookie the cookie
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	void addDepartment(String departmentName, String cookie) throws IllegalArgumentException, RemoteException;

	/**
	 * Adds the plan template.
	 *
	 * @param name the name
	 * @param plan the plan
	 * @throws RemoteException the remote exception
	 */
	void addPlanTemplate(String name, PlanFile plan) throws RemoteException;

	/**
	 * Save.
	 *
	 * @throws RemoteException the remote exception
	 */
	void save() throws RemoteException;

	/**
	 * Gets the login map.
	 *
	 * @return the login map
	 * @throws RemoteException the remote exception
	 */
	ConcurrentHashMap<String, Account> getLoginMap() throws RemoteException;

	/**
	 * Sets the login map.
	 *
	 * @param loginMap the login map
	 * @throws RemoteException the remote exception
	 */
	void setLoginMap(ConcurrentHashMap<String, Account> loginMap) throws RemoteException;

	/**
	 * Gets the cookie map.
	 *
	 * @return the cookie map
	 * @throws RemoteException the remote exception
	 */
	ConcurrentHashMap<String, Account> getCookieMap() throws RemoteException;

	/**
	 * Sets the cookie map.
	 *
	 * @param cookieMap the cookie map
	 * @throws RemoteException the remote exception
	 */
	void setCookieMap(ConcurrentHashMap<String, Account> cookieMap) throws RemoteException;

	/**
	 * Gets the department map.
	 *
	 * @return the department map
	 * @throws RemoteException the remote exception
	 */
	ConcurrentHashMap<String, Department> getDepartmentMap() throws RemoteException;

	/**
	 * Sets the department map.
	 *
	 * @param departmentMap the department map
	 * @throws RemoteException the remote exception
	 */
	void setDepartmentMap(ConcurrentHashMap<String, Department> departmentMap) throws RemoteException;

	/**
	 * Gets the plan template map.
	 *
	 * @return the plan template map
	 * @throws RemoteException the remote exception
	 */
	ConcurrentHashMap<String, PlanFile> getPlanTemplateMap() throws RemoteException;

	/**
	 * Sets the plan template map.
	 *
	 * @param planTemplateMap the plan template map
	 * @throws RemoteException the remote exception
	 */
	void setPlanTemplateMap(ConcurrentHashMap<String, PlanFile> planTemplateMap) throws RemoteException;

}