package testrunner;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.platform.runner.JUnitPlatform;

import systemname.dir.InputRoot;
import systemname.dir.TSRoot;
import systemname.global.CurrentTSTable;
import testreport.TestReport;
import testsuite.LoginTestSuiteTest;
import testsuite.UserSettingsTestSuiteTest;

@RunWith(JUnitPlatform.class)
public class SystemTestRunnerTest {

    private final static Logger logger = Logger.getLogger(SystemTestRunnerTest.class);

    private static InputRoot inputRoot = new InputRoot();
    private static TSRoot tsRoot = new TSRoot();

    private static CurrentTSTable currentTSTable = new CurrentTSTable();

    @BeforeAll
    public static void initializeTestRun(){

        logger.info("Starting Test Runs for all Scenarios @ : " + tsRoot.getTSRootPath());

        logger.info("Set up systemname.global variables database");
        currentTSTable.setUp();

        logger.info("Initialize Input root folder");
        inputRoot.removeFilesFromInputRoot();

        logger.info("Delete rows for previous runs of our Tests from all systems table(s).");
        //ToDo: Delete all rows for our Test Scenarios from MSSQL database. So that we have a clean start for our tests
        // Without impacting other's tests.

        // ToDo: Run Docker and Run application.
    }
    @AfterAll
    public static void endOfTestRun() {
        logger.info("All Test Runs completed!");
    }
    @Test
    public void systemTestRunner() throws InterruptedException {

        logger.info("Prerequisite: Application should already be up and running for this test to work!");

        logger.info("Get list of all TSNames to run");
        List<String> TSNames = tsRoot.getAllTSNamesFromTSFolder();

        logger.info("For each Test Scenario, do System Test Validations on (rome,alexandria,babyloan,sparta,troy)");
        for (int i = 0; i < TSNames.size(); i++) {

            Integer runNr = i + 1;
            logger.info("This is run nr # : " + runNr);

            logger.info("Copy the current Test Scenario to inputRoot folder");
            tsRoot.copyFilesFromTSFolderToInputRoot(TSNames.get(i));
            TimeUnit.SECONDS.sleep(1);

            logger.info("Update current Test Scenario record in systemname.global variables");
            String path = tsRoot.getTSRootPath() + "\\" + TSNames.get(i) + "\\" + "ftproot";
            currentTSTable.updateCurrentTSRecord(TSNames.get(i), path);
            logger.info("Running TS: " + path);

            logger.info("Wait for a little more than the Input ftp config (2 secs) to process all files...");
            TimeUnit.SECONDS.sleep(10);

            logger.info("Run all the test classes that we want to validate");

            logger.info("Validate Login");
            Result result = JUnitCore.runClasses(LoginTestSuiteTest.class);
            TestReport.resultReport(result);

            logger.info("Validate UserSettings");
            result = JUnitCore.runClasses(UserSettingsTestSuiteTest.class);
            TestReport.resultReport(result);

            //... And similarly add all other Systems that you want to validate here...
        }
    }
}
