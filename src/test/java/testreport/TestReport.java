package testreport;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import testsuite.LoginTestSuiteTest;

public class TestReport
{
    private final static Logger logger = Logger.getLogger(TestReport.class);

    @BeforeAll
    public static void runBeforeClass(){
        logger.info("BEFORE Running All Tests:");
    }
    @AfterAll
    public static void runAfterClass(){
        logger.info("AFTER Running All Tests:");
    }
    @Test
    public void driver() {
        for (int i = 0; i < 2; i++) {
            Integer runNr = i + 1;
            logger.info("This is run nr # : " + runNr);

            Result result = JUnitCore.runClasses(LoginTestSuiteTest.class);
            resultReport(result);
        }
    }
    public static void resultReport(Result result) {
        logger.info("Result Summary start: ---------------------------------------------------------------");
        logger.info("Result Status: " + result.wasSuccessful());
        logger.info("Total Runs: " + result.getRunCount());
        logger.info("Total Run time (in milli seconds): " + result.getRunTime());
        logger.info("Total nr of Failures: " + result.getFailureCount());
        logger.info("Total nr Ignored: " + result.getIgnoreCount());
        for (Failure failure : result.getFailures()) {
            logger.info("Failure: " + failure.toString());
        }
        logger.info("Result Summary End:   ---------------------------------------------------------------");
    }
}