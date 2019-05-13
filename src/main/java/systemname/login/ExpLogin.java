package systemname.login;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import systemname.dir.TSRoot;

import java.util.List;

public class ExpLogin {

    private static Logger logger = Logger.getLogger(ExpLogin.class);

    private static TSRoot tsRoot = new TSRoot();

    @Test
    public JSONArray buildJsonUsingDataFromCurrentTSFolder(String currentTSPath) {

        JSONArray jsonArray = new JSONArray();

        List<String> filePaths = tsRoot.getAllFilePathsInCurrentTSRoot(currentTSPath);
        logger.info("No: of files in the FTP folder : " + filePaths.size());

        for (String filePath:filePaths){

            // Initialise jsonObject for each record.
            JSONObject jsonObject = new JSONObject();

            //Build element fileName
            String fileName = filePath.substring(currentTSPath.length()+1).replace("\\","/");
            jsonObject.put("fileName", fileName);

            //Build element ResourceSourceType
            jsonObject.put("FileType", "xml");

            //Build element ResourceSource from resourceName calculated above.
            String[] fileSource = fileName.split("/");
            jsonObject.put("fileSource", fileSource[0]);

            //Build element SentMessageCount
            Integer fileRecords = Math.toIntExact(tsRoot.getCountOfRecordsFromACSVFile(filePath));
            jsonObject.put("fileRecords", fileRecords);

            jsonArray.put(jsonObject);
        }

        logger.info("Expected JsonJRMRome : ");
        for (Object result : jsonArray) {
            logger.info(result);
        }
        return jsonArray;
    }
}

