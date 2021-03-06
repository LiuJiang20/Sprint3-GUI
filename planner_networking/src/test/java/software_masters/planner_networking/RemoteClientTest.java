package software_masters.planner_networking;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class RemoteClientTest.
 */
public class RemoteClientTest
{

	/** The test server. */
	static Server testServer;
	
	/** The test client. */
	static Client testClient;
	
	/** The registry. */
	static Registry registry;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		System.out.println("Starting Test");
		try
		{
			String hostName = "10.14.1.80";
			registry = LocateRegistry.getRegistry(hostName, 1060);
			testServer = (Server) registry.lookup("PlannerServer");
			testClient = new Client(testServer);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Tear down after class.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
//		registry.unbind("PlannerServer");
//        // Unexport; this will also remove us from the RMI runtime
//        UnicastRemoteObject.unexportObject(testServer, true);
//        System.out.println("closing server");
	}

	/**
	 * Test login.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testLogin() throws IllegalArgumentException, RemoteException
	{

		// Checks invalid cases
		assertThrows(IllegalArgumentException.class, () -> testClient.login("invalidUsername", "invalidPassword"));
		assertThrows(IllegalArgumentException.class, () -> testClient.login("user", "invalidPassword"));
		assertThrows(IllegalArgumentException.class, () -> testClient.login("invalidUsername", "user"));

		// Checks valid logins
		testClient.login("user", "user");
		assertEquals("1", testClient.getCookie());
		testClient.login("admin", "admin");
		assertEquals("0", testClient.getCookie());
	}

	/**
	 * Test add user.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testAddUser() throws IllegalArgumentException, RemoteException
	{

		// tests non-admin addUser
		testClient.login("user", "user");
		testClient.getPlan("2019");
		PlanFile base = testClient.getCurrPlanFile();
		assertThrows(IllegalArgumentException.class, () -> testClient.addUser("newUser", "newUser", "default", false));

		// tests admin can add new account.
		testClient.login("admin", "admin");
		testClient.addUser("newUser", "newUser", "default", false);

		// test if account was actually created and correct department was added
//		ConcurrentHashMap<String, Account> loginMap = actualServer.getLoginMap();
//		assertTrue(loginMap.containsKey("newUser"));
//		ConcurrentHashMap<String, Department> departmentMap = actualServer.getDepartmentMap();
//		assertEquals(departmentMap.get("default"),loginMap.get("newUser").getDepartment());

		// verify can successfully obtain default plan using the created account
		testClient.login("newUser", "newUser");
		testClient.getPlan("2019");
		PlanFile test = testClient.getCurrPlanFile();
		assertEquals(base, test);

		// Verifies an exception is thrown when admins attempt to add a user with a
		// non-existent department
		testClient.login("admin", "admin");
		assertThrows(IllegalArgumentException.class,
				() -> testClient.addUser("anotherUser", "anotherUser", "invalidDepartment", false));

	}

	/**
	 * Test add department.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testAddDepartment() throws IllegalArgumentException, RemoteException
	{
		// tests non-admin addDepartment
		testClient.login("user", "user");
		assertThrows(IllegalArgumentException.class, () -> testClient.addDepartment("newDepartment"));

		// tests admin can add new department.
		testClient.login("admin", "admin");
		testClient.addDepartment("newDepartment");

		// verifies the department object was created and added to hash
//		ConcurrentHashMap<String, Department> departmentMap = actualServer.getDepartmentMap();
//		assertTrue(departmentMap.containsKey("newDepartment"));

		// verifies department was created because user cannot be assign a department
		// that does not exist. That would throw an exception.
		testClient.addUser("testDepartment", "testDepartment", "newDepartment", false);

	}

	/**
	 * Test flag plan.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testFlagPlan() throws IllegalArgumentException, RemoteException
	{
		// tests non-admin flagFile
		testClient.login("user", "user");
		assertThrows(IllegalArgumentException.class, () -> testClient.flagPlan("default", "2019", false));

		// tests admin can flag file.
		testClient.login("admin", "admin");
		testClient.flagPlan("default", "2019", true);
//		ConcurrentHashMap<String, Department> departmentMap = actualServer.getDepartmentMap();
//		PlanFile file=departmentMap.get("default").getPlan("2019");
		testClient.getPlan("2019");
		PlanFile file = testClient.getCurrPlanFile();
		assertTrue(file.isCanEdit());

		// tests exception is thrown if try to flag invalid file.
		assertThrows(IllegalArgumentException.class, () -> testClient.flagPlan("default", "2000", true));

	}

	/**
	 * Test get plan.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testGetPlan() throws IllegalArgumentException, RemoteException
	{
		// plan does not exist throws exception
		testClient.login("user", "user");
		assertThrows(IllegalArgumentException.class, () -> testClient.getPlan("2000"));

		// verify obtained plan is as expected
//		ConcurrentHashMap<String, Department> departmentMap = actualServer.getDepartmentMap();
		Plan plan = new Centre();
		plan.setName("Centre_Plan_1");
		PlanFile planfile = new PlanFile("2016", true, plan);
		testClient.pushPlan(planfile);
		testClient.getPlan("2016");
		assertEquals(planfile, testClient.getCurrPlanFile());
	}

	/**
	 * Test get plan outline.
	 *
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testGetPlanOutline() throws RemoteException
	{

		testClient.login("user", "user");

		// build expected file.
		PlanFile centreBase = new PlanFile(null, true, new Centre());

		// test that retrieved business plan outline matches expected
		testClient.getPlanOutline("Centre");
		assertEquals(centreBase, testClient.getCurrPlanFile());

		// if plan outline does not exist throw exception
		assertThrows(IllegalArgumentException.class, () -> testClient.getPlanOutline("invalid_outline"));

	}

	/**
	 * Testpush plan.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testpushPlan() throws IllegalArgumentException, RemoteException
	{
		// change canEdit flag to false for default planfile
		testClient.login("admin", "admin");
		testClient.flagPlan("default", "2019", false);

		// edit existing default planfile
		testClient.login("user", "user");
		testClient.getPlan("2019");
		PlanFile test = testClient.getCurrPlanFile();
		Plan temp = test.getPlan(); // if issues later verify shallow not deep copy
		temp.setName("Centre_Plan_2");

		// throws exception when pushing edited planfile if canEdit is false
		assertThrows(IllegalArgumentException.class, () -> testClient.pushPlan(test));

		// change can edit flag to true for default planfile
		testClient.login("admin", "admin");
		testClient.flagPlan("default", "2019", true);

		// verifies test file now on server is the same as the object that was pushed
		testClient.login("user", "user");
		testClient.pushPlan(test);
		testClient.getPlan("2019");
		assertEquals(test, testClient.getCurrPlanFile());

	}

	/**
	 * Test add branch.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testAddBranch() throws IllegalArgumentException, RemoteException
	{
		testClient.login("user", "user");
		//////////////////////////////////// Centre
		//////////////////////////////////// example/////////////////////////////////////////////
		testClient.getPlan("2019");
		PlanFile test = testClient.getCurrPlanFile();
		Node root = test.getPlan().getRoot();
		// try adding second mission should throw exception
		testClient.setCurrNode(root);
		assertThrows(IllegalArgumentException.class, () -> testClient.addBranch());
		// try adding second goal
		testClient.setCurrNode(root.getChildren().get(0));// at goal level
		testBranchCopy();
		/////////////////////////////////// VMOSA
		/////////////////////////////////// example///////////////////////////////////////////////
		Plan VMOSA_test = new VMOSA();
		root = VMOSA_test.getRoot();
		PlanFile vmosaTest = new PlanFile("2018", true, VMOSA_test);
		testClient.setCurrPlanFile(vmosaTest);
		// try adding second mission should throw exception
		testClient.setCurrNode(root.getChildren().get(0));
		assertThrows(IllegalArgumentException.class, () -> testClient.addBranch());
		// try adding second objective
		testClient.setCurrNode(testClient.getCurrNode().getChildren().get(0));// at objective level
		testBranchCopy();
		/////////////////////////////////// Iowa state
		/////////////////////////////////// example///////////////////////////////////////////////
		Plan IOWA_test = new IowaState();
		root = IOWA_test.getRoot();
		PlanFile iowaTest = new PlanFile("2017", true, IOWA_test);
		testClient.setCurrPlanFile(iowaTest);
		// try adding second mission should throw exception
		testClient.setCurrNode(root.getChildren().get(0));
		assertThrows(IllegalArgumentException.class, () -> testClient.addBranch());
		// try adding second core value
		testClient.setCurrNode(testClient.getCurrNode().getChildren().get(0));// at core value level
		testBranchCopy();
	}

	/**
	 * Test branch copy.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	private void testBranchCopy() throws IllegalArgumentException, RemoteException
	{
		testClient.addBranch();
		assertEquals(testClient.getCurrNode(), testClient.getCurrNode().getParent().getChildren().get(1));
		// assures deep copy not shallow. this is tested by changing one copy and
		// verifying that the original was not changed.
		testClient.getCurrNode().getParent().getChildren().get(1).setData("some text");
		assertNotEquals(testClient.getCurrNode(), testClient.getCurrNode().getParent().getChildren().get(1));
	}

	/**
	 * Test centre remove branch.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testCentreRemoveBranch() throws IllegalArgumentException, RemoteException
	{
		testClient.login("user", "user");
		//////////////////////////////////// Centre
		//////////////////////////////////// example/////////////////////////////////////////////
		testClient.getPlan("2019");
		PlanFile test = testClient.getCurrPlanFile();
		Node root = test.getPlan().getRoot();
		// try removing mission should throw exception
		testClient.setCurrNode(root);// mission level
		assertThrows(IllegalArgumentException.class, () -> testClient.removeBranch());
		// try removing goal should throw exception bc only one exists
		testClient.setCurrNode(root.getChildren().get(0));// goal level
		assertThrows(IllegalArgumentException.class, () -> testClient.removeBranch());
		// add second goal and verify that it can be removed
		testClient.setCurrNode(root.getChildren().get(0));// at goal level
		testBranchCopy();
		testClient.removeBranch();
		assertEquals("some text", testClient.getCurrNode().getParent().getChildren().get(0).getData());
	}

	/**
	 * Test VMOSA remove branch.
	 *
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testVMOSARemoveBranch() throws RemoteException
	{
		/////////////////////////////////// VMOSA
		/////////////////////////////////// example///////////////////////////////////////////////
		Plan VMOSA_test = new VMOSA();
		Node root = VMOSA_test.getRoot();
		PlanFile vmosaTest = new PlanFile("2018", true, VMOSA_test);
		testClient.setCurrPlanFile(vmosaTest);
		// try removing mission should throw exception
		testClient.setCurrNode(root.getChildren().get(0));
		assertThrows(IllegalArgumentException.class, () -> testClient.removeBranch());
		// try removing objective should throw exception bc only one exists
		testClient.setCurrNode(testClient.getCurrNode().getChildren().get(0));// objective level
		assertThrows(IllegalArgumentException.class, () -> testClient.removeBranch());
		// add second objective
		testClient.setCurrNode(testClient.getCurrNode());// at objective level
		testBranchCopy();
		testClient.removeBranch();
		assertEquals("some text", testClient.getCurrNode().getParent().getChildren().get(0).getData());
	}

	/**
	 * Test iowa remove branch.
	 *
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testIowaRemoveBranch() throws RemoteException
	{
		/////////////////////////////////// Iowa state
		/////////////////////////////////// example///////////////////////////////////////////////
		Plan IOWA_test = new IowaState();
		Node root = IOWA_test.getRoot();
		PlanFile iowaTest = new PlanFile("2017", true, IOWA_test);
		testClient.setCurrPlanFile(iowaTest);
		// try remove mission should throw exception
		testClient.setCurrNode(root.getChildren().get(0));
		assertThrows(IllegalArgumentException.class, () -> testClient.removeBranch());
		// try removing core value should throw exception bc only one exists
		testClient.setCurrNode(testClient.getCurrNode().getChildren().get(0));// objective level
		assertThrows(IllegalArgumentException.class, () -> testClient.removeBranch());
		// add second core value
		testClient.setCurrNode(testClient.getCurrNode().getChildren().get(0));// at core value level
		testBranchCopy();
		testClient.removeBranch();
		assertEquals("some text", testClient.getCurrNode().getParent().getChildren().get(0).getData());
	}

}
