package systemname.global;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import systemname.Config;
import systemname.db.JDBC;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class TestRunTable extends Config {

    private final static Logger logger = Logger.getLogger(TestRunTable.class);

    private static final String connectionStringGlobal = getConnectionStringGlobal();
    private static JDBC jdbcSQLITE = new JDBC(connectionStringGlobal);

    @Test
    public void setUp(){
        logger.info("Initialising TestRun Table for the Full Test Run!");

        makeDirectories(getPathGlobal());
        dropTestRunTable();
        createTestRunTable();
        initialiseTestRunTable();

        logger.info("Initialisation of TestRun Table Complete! \n");
    }
    @Test
    public void dropTestRunTable(){
        logger.info("Dropping TestRun Table...");

        String query = "DROP TABLE TestRun";
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void createTestRunTable(){
        logger.info("Creating TestRunTable...");

        String query = "CREATE TABLE IF NOT EXISTS TestRun (\n"
                + "	LastRun text PRIMARY KEY NOT NULL,\n"
                + "	ResultsLocation text NOT NULL,\n"
                + "	LastModified text NOT NULL\n"
                + ");";
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void initialiseTestRunTable(){
        logger.info("Initializing TestRunTable...");

        deleteAllRowsFromTestRunTable();
        String query = "INSERT INTO TestRun (LastRun, ResultsLocation, LastModified) \n"
                + "	VALUES ('TR000','C:\\TestRuns\\', datetime('now', 'localtime')) \n ;" ;

        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void updateTestRunRecord(String nameTS){
        logger.info("Updating TestRun TestRun and ResultsLocation variables with latest Test Run details...");

        String query = String.format("UPDATE TestRun SET LastRun = '%s' , \n" +
                "                 LastModified = datetime('now', 'localtime') \n" +
                "                 ;" ,nameTS );
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public JSONArray selectTestRunDetails(){
        logger.info("Selecting TestRun details...");

        String query = String.format("SELECT * FROM TestRun \n;" );
        return jdbcSQLITE.runQuery(query);
    }
    @Test
    public void deleteAllRowsFromTestRunTable(){
        logger.info("Deleting all records from TestRun Table...");

        String query = "DELETE FROM TestRun  \n;";
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void incrementTestRun() {
        // Fetch last result
        JSONArray jsonArray = selectTestRunDetails();
        logger.info(jsonArray);

        // Increment LastRun
        String lastRun = jsonArray.getJSONObject(0).getString("LastRun");
        logger.info("lastRun: " + lastRun);

        Long oldValue = Long.valueOf(lastRun.substring(2));
        logger.info("oldValue: " + oldValue);

        Long incrementedValue = oldValue + 1;
        logger.info("incValue: " + incrementedValue);

        String incrementedRunValue = "TR" + StringUtils.leftPad(String.valueOf(incrementedValue),3,"0");
        logger.info("incrementedRunValue" + incrementedRunValue);

        // Update LastRun and LastModified fields
        updateTestRunRecord(incrementedRunValue);
    }
    public String getTestResultsPath() {

        JSONObject jsonObject = selectTestRunDetails().getJSONObject(0);
        return jsonObject.getString("ResultsLocation")+jsonObject.getString("LastRun")+ "\\";
    }
    @Test
    public void createResultsDirectory() {

        String testResultsPath = getTestResultsPath();

        // Make Results directory
        File file = new File(testResultsPath);
        file.mkdirs();
    }
}
