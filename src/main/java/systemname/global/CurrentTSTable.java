package systemname.global;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import systemname.db.JDBC;

import java.io.File;

public class CurrentTSTable {

    private final static Logger logger = org.apache.log4j.Logger.getLogger(CurrentTSTable.class);

    private static final String pathGlobal = "C:\\global\\sqlite\\db\\";

    private static final String jdbcUrlSqlite = "jdbc:sqlite:" + pathGlobal+ "global.db";
    private static JDBC jdbcSQLITE = new JDBC(jdbcUrlSqlite);

    public void setUp(){
        logger.info("Initialising CurrentTSTable for the Full Test Run!");

        makeGlobalDirectoryToHoldSqliteDb();
        dropCurrentTSTable();
        createCurrentTSTable();
        initialiseCurrentTSTable();

        logger.info("Initialisation of CurrentTSTable Complete! \n");
    }
    @Test
    public void makeGlobalDirectoryToHoldSqliteDb() {
        logger.info("Creating directory C:\\global\\sqlite\\db\\ ...");

        File file = new File("C:\\global\\sqlite\\db\\");
        file.mkdirs();
    }
    @Test
    public void dropCurrentTSTable(){
        logger.info("Dropping CurrentTSTable...");

        String query = "DROP TABLE CurrentTS";
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void createCurrentTSTable(){
        logger.info("Creating CurrentTSTable...");

        String query = "CREATE TABLE IF NOT EXISTS CurrentTS (\n"
                + "	Name text PRIMARY KEY NOT NULL,\n"
                + "	Path text NOT NULL,\n"
                + "	LastModified text NOT NULL\n"
                + ");";
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void initialiseCurrentTSTable(){
        logger.info("Initializing CurrentTSTable...");

        deleteAllRowsFromCurrentTSTable();
        String query = "INSERT INTO CurrentTS (Name, Path, LastModified) \n"
                + "	VALUES ('TS000','C:\\TestScenarios\\TestScenariosToRun',datetime('now', 'localtime')) \n"
                + ";";
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public void updateCurrentTSRecord(String nameTS, String path){
        logger.info("Updating CurrentTS Path and Name variables with latest runs TS folder details...");

        String query = String.format("UPDATE CurrentTS SET name = '%s' , \n" +
                "                 Path = '%s' ,\n" +
                "                 LastModified = datetime('now', 'localtime') \n" +
                "                 ;" ,nameTS ,path);
        jdbcSQLITE.runUpdate(query);
    }
    @Test
    public JSONArray selectCurrentTSPath(){
        logger.info("Selecting CurrentTS Path...");

        String query = String.format("SELECT * FROM CurrentTS \n;" );
        return jdbcSQLITE.runQuery(query);
    }
    @Test
    public void deleteAllRowsFromCurrentTSTable(){
        logger.info("Deleting all records from CurrentTS Table...");

        String query = "DELETE FROM CurrentTS  \n;";
        jdbcSQLITE.runUpdate(query);
    }
}

