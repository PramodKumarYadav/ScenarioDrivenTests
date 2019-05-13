package testsuite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import usersettings.*;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        ChangeAddressTest.class ,
        ChangePasswordTest.class ,
        ChangePhoneNumberTest.class
})

public class UserSettingsTestSuiteTest {
}
