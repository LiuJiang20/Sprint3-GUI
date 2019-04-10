package software_masters.planner_networking;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class LocalTestSuite.
 */
@RunWith(Suite.class)
@SuiteClasses({ CentreTest.class, IowaStateTest.class, LocalClientTest.class, NodeTest.class, ServerTest.class,
		VMOSATest.class })
public class LocalTestSuite
{

}
