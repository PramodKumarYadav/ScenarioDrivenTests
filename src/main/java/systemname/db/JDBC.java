package systemname.db;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import java.sql.*;

public class JDBC {
    private final static Logger logger = Logger.getLogger(JDBC.class);
    private static SQLToJSON sqlToJSON = new SQLToJSON();

    private Connection connection;
    private final String JDBCURL;

    // SQLLITE ConnectionString: "jdbc:sqlite:sqlite_database_file_path";
    // SQLLITE ConnectionString: "jdbc:sqlite:C:/global/sqlite/db/global.db";

    // MSSQL ConnectionString: "jdbc:sqlserver://<server>:<port>;databaseName=AdventureWorks;user=<user>;password=<password>"
    // MSSQL ConnectionString: "jdbc:sqlserver://localhost;databaseName=dummyKidsToys;user=dummyUser;password=dummyPwd"

    public JDBC(String jdbcUrl){
        logger.info("Making connection for: " + jdbcUrl);
        JDBCURL = jdbcUrl;
    }
    private void connect() {

        try {
            connection = DriverManager.getConnection(JDBCURL);
            if (connection.isClosed()) {
                throw new RuntimeException(String.format("Failed to connect to the database(%s) ", JDBCURL));
            }else{
                logger.info("Connected Successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void disconnect() {
        try {
            if(connection.isClosed()){
                logger.info("Connection was already closed");
            }
            else{
                connection.close();
                if(connection.isClosed()) {
                    logger.info("Connection Closed!");
                }else{
                    throw new RuntimeException(String.format("Failed to disconnect from the database(%s) ", JDBCURL));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public JSONArray runQuery(String query){
        logger.info("In Method runQuery");
        logger.info("Query = " + query);
        JSONArray jsonArray = new JSONArray();
        try{
            connect();
            PreparedStatement statement = connection.prepareStatement(query);
            logger.info("statement: " + statement);
            jsonArray = sqlToJSON.convertSQLResultSetToJsonArray(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
            logger.info("------------------------------------------------------");
        }
        return jsonArray;
    }
    public void runUpdate(String query){
        logger.info("In Method runUpdate");
        logger.info("Query = " + query);
        try{
            connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
}

