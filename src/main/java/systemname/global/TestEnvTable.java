package systemname.global;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import systemname.Config;
import systemname.db.JDBC;

public class TestEnvTable extends Config {

    private final static Logger logger = Logger.getLogger(TestEnvTable.class);

    private static final String connectionStringGlobal = getConnectionStringGlobal();
    private static JDBC jdbcSQLITE = new JDBC(connectionStringGlobal);

    private static final String ChosenTestEnvironment = getChosenTestEnvironment();

    public void setUp(){
        logger.info("Initialising TestEnv Table for the Full Test Run!");

        makeDirectories(getPathGlobal());
        dropTestEnvTable();
        createTestEnvTable();
        initialiseTestEnvTable();

        logger.info("Initialisation of TestEnv Complete! \n");
    }
    @Test
    public void dropTestEnvTable(){
        logger.info("Dropping TestEnv Table...");

        String query = "DROP TABLE TestEnv";
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void createTestEnvTable(){
        logger.info("Creating TestEnv Table...");

        String query = "CREATE TABLE IF NOT EXISTS TestEnv (\n"
                + "	TestEnvironment text PRIMARY KEY NOT NULL,\n"
                + "	ConnectionStringMSSQL text NOT NULL,\n"
                + "	LastModified text NOT NULL\n"
                + ");";
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void initialiseTestEnvTable(){
        logger.info("Initializing TestEnv Table...");

        deleteAllRowsFromTestEnvTable();
        String query = "";
        if (ChosenTestEnvironment == "localhost") {
            query = "INSERT INTO TestEnv (TestEnvironment, connectionStringMSSQL, LastModified) \n"
                    + "	VALUES ('localhost','jdbc:sqlserver://localhost;user=sa;password=Test123.', datetime('now', 'localtime')) \n";
        }
        if (ChosenTestEnvironment == "acceptance") {
            query = "INSERT INTO TestEnv (TestEnvironment, connectionStringMSSQL, LastModified) \n"
                    + "	VALUES ('acceptance','jdbc:sqlserver://localhost;user=sa;password=Test123.', datetime('now', 'localtime')) \n";
        }
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public JSONArray selectTestEnv(){
        logger.info("Selecting TestEnv ...");

        String query = String.format("SELECT * FROM TestEnv ; \n");
        return jdbcSQLITE.runQuery(query);
    }
    @Test
    public void deleteAllRowsFromTestEnvTable(){
        logger.info("Deleting all records from TestEnv Table...");

        String query = "DELETE FROM TestEnv  \n;";
        jdbcSQLITE.runUpdate(query);
    }
}
