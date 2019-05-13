package testsuite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import login.*;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        LoginAdminUserTest.class ,
        LoginGuestUserTest.class ,
        LoginNormalUserTest.class
})
public class LoginTestSuiteTest
{

}