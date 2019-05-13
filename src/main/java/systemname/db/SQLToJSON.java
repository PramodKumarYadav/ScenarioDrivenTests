package systemname.db;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class SQLToJSON {
    private final static Logger logger = Logger.getLogger(SQLToJSON.class);

    public static JSONArray convertSQLResultSetToJsonArray(ResultSet resultSet){

        JSONArray jsonArray = new JSONArray();

        int numberOfRows = 0;
        if (resultSet != null) {
            logger.info("Converting ResultSet to JSONArray...");

            try {
                ResultSetMetaData metaData = null;
                metaData = resultSet.getMetaData();

                int numOfColumns = metaData.getColumnCount();
                logger.info("Number of columns in this table : " + numOfColumns);

                while (resultSet.next()) {
                    numberOfRows = numberOfRows +1;

                    JSONObject jsonObject = new JSONObject();

                    // Build the json record for each record. Add once all columns are iterated and added in JsonObject.
                    for (int i = 1; i < numOfColumns + 1; i++) {

                        String column_name = metaData.getColumnName(i);
                        int column_type = metaData.getColumnType(i);

                        if (column_type == Types.ARRAY) {
                            jsonObject.put(column_name,resultSet.getArray(column_name));

                        } else if(column_type == Types.BIGINT){
                            jsonObject.put(column_name,resultSet.getInt(column_name));
                        }else if(column_type == Types.BOOLEAN){
                            jsonObject.put(column_name,resultSet.getBoolean(column_name));
                        }else if(column_type == Types.BLOB){
                            jsonObject.put(column_name,resultSet.getBlob(column_name));
                        }else if(column_type == Types.DOUBLE){
                            jsonObject.put(column_name,resultSet.getDouble(column_name));
                        }else if(column_type == Types.FLOAT){
                            jsonObject.put(column_name,resultSet.getFloat(column_name));
                        }else if(column_type == Types.INTEGER){
                            jsonObject.put(column_name,resultSet.getInt(column_name));
                        }else if(column_type == Types.NVARCHAR){
                            jsonObject.put(column_name,resultSet.getNString(column_name));
                        }else if(column_type == Types.VARCHAR){
                            jsonObject.put(column_name,resultSet.getString(column_name));
                        }else if(column_type == Types.VARBINARY){
                            jsonObject.put(column_name,resultSet.getString(column_name));
                        }else if(column_type == Types.TINYINT){
                            jsonObject.put(column_name,resultSet.getInt(column_name));
                        }else if(column_type == Types.SMALLINT){
                            jsonObject.put(column_name,resultSet.getInt(column_name));
                        }else if(column_type == Types.DATE){
                            jsonObject.put(column_name,resultSet.getDate(column_name));
                        }else if(column_type == Types.TIMESTAMP){
                            jsonObject.put(column_name,resultSet.getTimestamp(column_name));
                        }else {
                            jsonObject.put(column_name,resultSet.getObject(column_name));
                        }
                    }

                    jsonArray.put(jsonObject);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            logger.info("No Results in the resultSet");
        }

        logger.info("number Of Rows : " + numberOfRows );
        logger.info("jsonArray : ");
        for (Object result : jsonArray) {
            logger.info(result);
        }
        return jsonArray;
    }
}

