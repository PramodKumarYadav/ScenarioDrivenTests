package testreport;

import org.json.JSONArray;
import org.json.JSONObject;
import systemname.global.CurrentTSTable;
import systemname.global.TestRunTable;
import testsuite.LoginTestSuiteTest;

import org.apache.log4j.Logger;

import org.junit.jupiter.api.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;

public class TestReport
{
    private final static Logger logger = Logger.getLogger(TestReport.class);

    private static CurrentTSTable currentTSTable = new CurrentTSTable();

    private static TestRunTable testRunTable = new TestRunTable();
    private static String testResultsPath = testRunTable.getTestResultsPath();

    public static void createResultReport(String nameTestSuite, String nameTestScenario, Result result) {

        // Log result report
        resultReport(result);

        // If any of the runs fail, mark the overall Scenario status as Failed.
        if (!result.wasSuccessful()) {
            currentTSTable.updateTSRunStatus("Fail");
        }

        // create result node
        JSONObject jsonObjResult = new JSONObject();
        jsonObjResult.put("Successful", result.wasSuccessful());
        jsonObjResult.put("RunCount", result.getRunCount());
        jsonObjResult.put("RunTime (ms)", result.getRunTime());
        jsonObjResult.put("IgnoreCount", result.getIgnoreCount());
        jsonObjResult.put("FailureCount", result.getFailureCount());

        // create failure node
        int i= 1;
        JSONObject jsonFailure = new JSONObject();
        for (Failure failure : result.getFailures()) {
            jsonFailure.put("Failure" + i, failure);
            i++;
        }

        // Add failure node to Results node
        jsonObjResult.put("Failures", jsonFailure);

        // nameTestSuiteTests node
        JSONObject jsonObjResults = new JSONObject();
        jsonObjResults.put(nameTestSuite + "Tests", jsonObjResult);

        // Add jsonObject to jsonArray
        JSONArray jsonArrResults = new JSONArray();
        jsonArrResults.put(jsonObjResults);

        // Write result to a JSON file
        String fileName = testResultsPath + nameTestScenario + ".json";
        String textToWrite = jsonArrResults.toString();
        writeResultsToJsonFile(fileName,textToWrite, true);

    }
    public static void resultReport(Result result) {
        logger.info("Result Summary start: --------------------------------------------");
        logger.info("result.wasSuccessful(): " + result.wasSuccessful());
        logger.info("result.getRunCount: " + result.getRunCount());
        logger.info("result.getRunTime() (in milli seconds): " + result.getRunTime());
        logger.info("result.getIgnoreCount(): " + result.getIgnoreCount());
        logger.info("result.getFailureCount: " + result.getFailureCount());
        for (Failure failure : result.getFailures()) {
            logger.info("Failure: " + failure.toString());
        }
        logger.info("Result Summary End:   ---------------------------------------------");
    }
    public static void writeResultsToJsonFile(String fileName, String textToWrite, boolean flagAppend)
    {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, flagAppend) );
            writer.newLine();
            writer.write(textToWrite);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void renameTSFileWithFinalRunStatusAndReformatContentAsSingleJsonArray(String nameTestScenario, String finalRunStatus){

        // Construct old and new file names from TSname and runStatus
        String oldFileName = testResultsPath + nameTestScenario + ".json";
        String newFilename = testResultsPath + finalRunStatus + " " + nameTestScenario + ".json";

        // Reformat the file to convert all individual Json arrays into one
        reformatFileAsOneJsonArray(oldFileName);

        File oldfile =new File(oldFileName);
        File newfile =new File(newFilename);

        if(oldfile.renameTo(newfile)){
            logger.info("Rename successful to: " + finalRunStatus);
        }else{
            logger.info("Rename failed to: " + finalRunStatus);
        }
    }
    @Test
    public static void reformatFileAsOneJsonArray(String fileName)  {

//            String fileName = "C:\\TestRuns\\TR029\\TS001.json";
        File file = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String concatenatedOutput = "";
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                logger.info(line);
                concatenatedOutput = concatenatedOutput + line;
            }

            String singleJsonArray = concatenatedOutput.replace("][",",");
            logger.info("singleJsonArray: " + singleJsonArray);
            writeResultsToJsonFile(fileName, singleJsonArray, false);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void driver() {
        for (int i = 0; i < 2; i++) {
            Integer runNr = i + 1;
            logger.info("This is run nr # : " + runNr);

            Result result = JUnitCore.runClasses(LoginTestSuiteTest.class);
            resultReport(result);
            createResultReport("Login", "TS001",result);
        }
    }
    @Test
    public void test() {
        renameTSFileWithFinalRunStatusAndReformatContentAsSingleJsonArray("TS001","Pass");
    }
}