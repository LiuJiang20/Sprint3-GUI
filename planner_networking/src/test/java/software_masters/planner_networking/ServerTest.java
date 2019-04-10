package software_masters.planner_networking;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerTest.
 */
public class ServerTest
{

	/** The test server. */
	static Server testServer;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		testServer = (Server) new ServerImplementation();
	}

	/**
	 * Tear down after class.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	/**
	 * Test add plan template.
	 *
	 * @throws RemoteException the remote exception
	 */
	@Test
	public void testAddPlanTemplate() throws RemoteException
	{
		Plan Iowa_State = new IowaState();
		PlanFile Iowa_test = new PlanFile(null, true, Iowa_State);
		testServer.addPlanTemplate("IowaState", Iowa_test);
		PlanFile other = testServer.getPlanOutline("IowaState", "1");
		assertEquals(Iowa_test, other);
	}

	/**
	 * Test serialization.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSerialization() throws Exception
	{
		testServer.save();
		Server temp = ServerImplementation.load();
		assertEquals(testServer, temp);

	}

}
