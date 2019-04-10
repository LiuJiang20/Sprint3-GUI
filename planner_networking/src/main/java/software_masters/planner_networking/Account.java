/**
 * 
 */
package software_masters.planner_networking;

// TODO: Auto-generated Javadoc
/**
 * The Class Account.
 */
public class Account
{

	/** The password. */
	private String password;
	
	/** The cookie. */
	private String cookie;
	
	/** The department. */
	private Department department;
	
	/** The is admin. */
	private boolean isAdmin;

	/**
	 * Instantiates a new account.
	 */
	public Account()
	{
		this.password = null;
		this.cookie = null;
		this.department = null;
		this.isAdmin = false;
	}

	/**
	 * Instantiates a new account.
	 *
	 * @param password the password
	 * @param cookie the cookie
	 * @param department the department
	 * @param isAdmin the is admin
	 */
	public Account(String password, String cookie, Department department, boolean isAdmin)
	{
		this.password = password;
		this.cookie = cookie;
		this.department = department;
		this.isAdmin = isAdmin;
	}

	/**
	 * Test credentials.
	 *
	 * @param password the password
	 * @return the string
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public String testCredentials(String password) throws IllegalArgumentException // returns cookie
	{
		if (this.getPassword().equals(password))
		{
			return cookie;
		}
		throw new IllegalArgumentException("Invalid username and/or password");
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password)
	{
		this.password = password;
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
	 * Gets the department.
	 *
	 * @return the department
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * Sets the department.
	 *
	 * @param department the new department
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	 * Checks if is admin.
	 *
	 * @return true, if is admin
	 */
	public boolean isAdmin()
	{
		return isAdmin;
	}

	/**
	 * Sets the admin.
	 *
	 * @param isAdmin the new admin
	 */
	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
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
		Account other = (Account) obj;
		if (cookie == null)
		{
			if (other.cookie != null)
				return false;
		} else if (!cookie.equals(other.cookie))
			return false;
		if (department == null)
		{
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (password == null)
		{
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
