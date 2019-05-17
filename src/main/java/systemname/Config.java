package systemname;

import org.apache.log4j.Logger;
import systemname.global.CurrentTSTable;
import systemname.global.TestEnvTable;

import java.io.File;

public class Config {

    private final static Logger logger = org.apache.log4j.Logger.getLogger(Config.class);

    private static final String ChosenTestEnvironment = "localhost";
    public final static String getChosenTestEnvironment() {
        return ChosenTestEnvironment;
    }

    private static final String pathGlobal = "C:\\global\\sqlite\\db\\";
    public final static String getPathGlobal() {
        return pathGlobal;
    }

    private final static String FTP_ROOT = "C:\\SourceCode\\MyApplication\\ftproot\\";
    public final static String getFTPRootPath() {
        return FTP_ROOT;
    }

    private final static String TS_ROOT = "C:\\TestScenarios\\TestScenariosToRun\\";
    public final static String getTSRootPath(){
        return TS_ROOT;
    }

    // SQLLITE ConnectionString: "jdbc:sqlite:sqlite_database_file_path";
    // SQLLITE ConnectionString: "jdbc:sqlite:C:/global/sqlite/db/global.db";
    private static final String connectionStringGlobal = "jdbc:sqlite:" + pathGlobal+ "global.db";
    public final static String getConnectionStringGlobal() {
        return connectionStringGlobal;
    }

    // Create Global database if not already existing
    private static CurrentTSTable currentTSTable = new CurrentTSTable();
    private static TestEnvTable testEnvTable = new TestEnvTable();


    public void setUp() {

        logger.info("Creating all folders needed in our framework (if not existing already)...");
        makeDirectories(pathGlobal);
        makeDirectories(TS_ROOT);

        logger.info("Initialising all global Tables for the Full Test Run!");
        currentTSTable.setUp();
        testEnvTable.setUp();
    }

    public final static void makeDirectories(String pathFolder) {

        logger.info("Creating directory " + pathFolder + " ...");

        File file = new File(pathFolder);
        file.mkdirs();
    }
}
