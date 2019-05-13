package login;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import org.junit.jupiter.api.BeforeAll;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import org.junit.jupiter.api.Test;
import systemname.db.JDBC;
import systemname.dir.TSRoot;
import systemname.global.CurrentTSTable;
import systemname.login.ExpLogin;

public class LoginAdminUserTest {

    final static Logger logger = Logger.getLogger(LoginAdminUserTest.class);

    private static CurrentTSTable currentTSTable = new CurrentTSTable();
    private static ExpLogin expLogin = new ExpLogin();

    // MSSQL ConnectionString: "jdbc:sqlserver://localhost;databaseName=KidsToys;user=salesman01;password=salesmanPwd01"
    private static String jdbcUrlMSSQL = "jdbc:sqlserver://localhost;databaseName=KidsToys;user=salesman01;password=salesmanPwd01" ;
    //ToDo: Fetch the above URL from global variables database. Reason is, if you want to run this in multi-env,
    //ToDo: You don't want to hardcode it here.

    private static JDBC jdbcMSSQL = new JDBC(jdbcUrlMSSQL);
    private static TSRoot tsRoot = new TSRoot();

    private static JSONArray actJsonLoginFromMSSQL;
    private static JSONArray expJsonLoginFromFile;
    @BeforeAll
    public static void fetchSourceAndTarget(){
        logger.info("Prerequisite: Application should already be up and running for this test to work!");

        String currentTSPath = currentTSTable.selectCurrentTSPath().getJSONObject(0).getString("Path");
        logger.info("currentTSPath: " + currentTSPath);

        logger.info("Step 01: Build expected Json using content from currentTS folder.");
        expJsonLoginFromFile = expLogin.buildJsonUsingDataFromCurrentTSFolder(currentTSPath);

        logger.info("Step 02: Build actual Json by reading records FROM Login Table where, ResourceName in [these file paths]");
        String query = "SELECT * \n" +
                "  FROM [Login].[user]\n" +
                "  WHERE UserName in (\n" +
                tsRoot.getUserNameWhereClauseString(currentTSPath) +
                "      ) order by UserName";
        actJsonLoginFromMSSQL = jdbcMSSQL.runQuery(query);
    }
    @Test
    public void validateFTPFolderMetaDataVsMSSQLTable() {
        JSONAssert.assertEquals(expJsonLoginFromFile, actJsonLoginFromMSSQL, JSONCompareMode.LENIENT);
    }
}

