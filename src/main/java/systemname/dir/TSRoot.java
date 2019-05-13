package systemname.dir;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class TSRoot {

    private final static Logger logger = org.apache.log4j.Logger.getLogger(TSRoot.class);

    private final static String TS_ROOT = "C:\\TestScenarios\\TestScenariosToRun";

    private static final InputRoot inputRoot = new InputRoot();

    public String getTSRootPath(){
        return TS_ROOT;
    }
    public List<String> getAllTSNamesFromTSFolder(){
        logger.info("Getting all TS names from TSFolder...");
        List<String> folderNames = new ArrayList<>();
        File fileFolder = new File(TS_ROOT);

        File[] folderList = fileFolder.listFiles();
        for(File file : folderList){
            if(file.isDirectory()){
                folderNames.add(file.getName());;
            }else if(file.isFile()){
                logger.info("Not a folder : " + file.getName());
            }
        }

        logger.info("List of All TS Names : " + folderNames);
        return folderNames;
    }
    public List<String> getAllFileNamesFromTSFolder(String folderPath){

        List<String> fileNames = new ArrayList<>();
        File fileFolder = new File(folderPath);

        ListAllFileNamesInAFolder(fileFolder,fileNames);

        logger.info("List of All file Names : " +fileNames);
        return fileNames;
    }
    private void ListAllFileNamesInAFolder(File folder, List<String> fileNames){

        File[] fileList = folder.listFiles();
        for(File file : fileList){
            // if directory, then call the same method again
            if(file.isDirectory()){
//                logger.info("Directory : " + file.getName());
                ListAllFileNamesInAFolder(file, fileNames);
            }else if(file.isFile()){
//                logger.info("File : " + file.getName());
                fileNames.add(file.getName());
            }
        }
    }
    public String getUserNameWhereClauseString(String currentTSPath){

        List<String> resourceNameListFromTSFolder = getResourceNameListFromPaths(currentTSPath);

        // Get the where clause String for [Rome].[dbo].[JobResourceMetadata]
        String resourceNameWhereClauseString = convertResourceNameListToWhereClauseQueryString(resourceNameListFromTSFolder);
        return resourceNameWhereClauseString;
    }
    public List<String> getResourceNameListFromPaths(String folderPath) {

        List<String> resourceNameList = new ArrayList<>();
        List<String> filePaths = getAllFilePathsInCurrentTSRoot(folderPath);
        for (int i=0; i < filePaths.size(); i++) {
            resourceNameList.add(filePaths.get(i).substring(folderPath.length()+1).replace("\\","/"));
        }
        // logger.info(resourceNameList);
        return resourceNameList;
    }
    public List<String> getAllFilePathsInCurrentTSRoot(String folderPath){

        List<String> filePaths = new ArrayList<>();
        File fileFolder = new File(folderPath);

        ListAllFilePathsInAFolder(fileFolder,filePaths);

        // logger.info("List of All file paths : " +filePaths);
        return filePaths;
    }
    private void ListAllFilePathsInAFolder(File folder, List<String> filePaths) {

        File[] fileList = folder.listFiles();
        for(File file : fileList){
            // if directory, then call the same method again
            if(file.isDirectory()){
//                logger.info("Directory : " + file.getPath());
                ListAllFilePathsInAFolder(file, filePaths);
            }else if(file.isFile()){
//                logger.info("File : " + file.getPath());
                filePaths.add(file.getPath());
            }
        }
    }
    public String convertResourceNameListToWhereClauseQueryString(List<String> resourceNameList)
    {
        String ResourceNameWhereClauseString = "";
        for (int i=0; i < resourceNameList.size(); i++) {
            // If not the last element, add a comma. Else, skip comma.
            if (i != (resourceNameList.size() - 1) ) {
                ResourceNameWhereClauseString = ResourceNameWhereClauseString + "\t\t" + "'" + resourceNameList.get(i) + "',\n";
            }else{
                ResourceNameWhereClauseString = ResourceNameWhereClauseString + "\t\t" + "'" + resourceNameList.get(i) + "'\n";
            }
        }
        // logger.info("ResourceNameWhereClauseString: " +ResourceNameWhereClauseString);
        return ResourceNameWhereClauseString;
    }
    public Long getCountOfRecordsFromACSVFile(String fullFilePath) {

        // ToDo: PayPlaza doesn't have a header. So we need to put some logic in the FTP folder to handle this.
        File file = new File(fullFilePath);
        Long countOfRecords = Long.valueOf(0);

        try{
//            logger.info("Reading file: " + file.getCanonicalPath() );
            BufferedReader br  = new BufferedReader(new FileReader(file));
            countOfRecords = br.lines().count();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.info("countOfRecords: " + countOfRecords);
            return countOfRecords;
        }
    }
    public List<String> getListOfRecordsFromACSVFile(String path) {

        File file = new File(path);
        List<String> listOfRecords = new ArrayList<>();

        try{
            logger.info("Reading file: " + file.getCanonicalPath() );
            BufferedReader br  = new BufferedReader(new FileReader(file));
            String strLine;
            while((strLine = br.readLine()) != null){
//                logger.info("Line is : " + strLine);
                listOfRecords.add(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.info("List size: " +listOfRecords.size());
            logger.info("list contents: " +listOfRecords);
            return listOfRecords;
        }
    }
    private void readContent(File file, Integer countOfRecords) throws IOException{
        logger.info("read file " + file.getCanonicalPath() );
        try(BufferedReader br  = new BufferedReader(new FileReader(file))){
            String strLine;
            // Read lines from the file, returns null when end of stream
            // is reached
            logger.info("Count of lines using br.lines().count() : " + br.lines().count());
            while((strLine = br.readLine()) != null){
                logger.info("Line is : " + strLine);
                countOfRecords++;
            }
        }
        finally {
            logger.info("countOfRecords :" + countOfRecords);
        }
    }
    // Uses listFiles method
    private void listAllFiles(File folder){
        logger.info("In listAllfiles(File) method");
        Integer countOfRecords = 0;

        File[] fileNames = folder.listFiles();
        for(File file : fileNames){
            // if directory call the same method again
            if(file.isDirectory()){
                logger.info("In folder : " + file.getName());
                listAllFiles(file);
            }else{
                try {
                    readContent(file, countOfRecords);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
    // Uses Files.walk method
    private void listAllFiles(String path){
        logger.info("In listAllfiles(String path) method");
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        readContent(filePath);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getNumberOfRecordsInACSVFile_GivesErrorWithOtherEncodings(String path){
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {

            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        readContent(filePath);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void readContent(Path filePath) throws IOException{
        logger.info("Reading file " + filePath);
        List<String> fileList = Files.readAllLines(filePath);

        logger.info("No: of records in file :" + fileList.size());
        logger.info("Actual Reocords : " + fileList + "\n");
    }
    @Test
    public void testCopy() throws InterruptedException {
        TSRoot tsRoot = new TSRoot();
        List<String> TSNames = tsRoot.getAllTSNamesFromTSFolder();
        logger.info(TSNames);

        // Clear the existing FTP root Test Scenario from INPUT_DIRECTORY folder
        inputRoot.removeFilesFromInputRoot();
        TimeUnit.SECONDS.sleep(2);

        // For each test scenario,
        for (int i = 0; i < TSNames.size(); i++) {

            Integer runNr = i + 1;
            logger.info("This is run nr # : " + runNr + " for TS: " + TS_ROOT + "\\"+ TSNames.get(i));

            // Copy the current Test Scenario to INPUT_DIRECTORY folder
            tsRoot.copyFilesFromTSFolderToInputRoot(TSNames.get(i));

            // Wait for sometime for the folders to be processed.
            // ToDo: Later I want to change this fixed wait to a real time wait, listening to Rabbit.
            TimeUnit.SECONDS.sleep(2);

        }
    }
    public void copyFilesFromTSFolderToInputRoot(String nameTSFolder) {
        logger.info("Copying files from Test Scenarios directory to ftp root subfolders...");
        copyOneFolderToAnother(TS_ROOT + "\\"+ nameTSFolder+"\\"+"inputRoot\\toys", inputRoot +"\\"+"toys");
        copyOneFolderToAnother(TS_ROOT + "\\"+ nameTSFolder+"\\"+"inputRoot\\shoes", inputRoot +"\\"+"shoes");
        copyOneFolderToAnother(TS_ROOT + "\\"+ nameTSFolder+"\\"+"inputRoot\\balls", inputRoot +"\\"+"balls");
        copyOneFolderToAnother(TS_ROOT + "\\"+ nameTSFolder+"\\"+"inputRoot\\tshirt", inputRoot +"\\"+"tshirt");
    }
    private void copyOneFolderToAnother(String pathSource,String pathTarget) {
        File source = new File(pathSource);
        File dest = new File(pathTarget);

        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void testgetCountOfRecordsInACSVFile(String path){

        File folder = new File(path);
        File[] fileNames = folder.listFiles();
        Integer countOfRecords = 0;
        for(File file : fileNames){
            // if directory call the same method again
            if(file.isDirectory()){
                logger.info("In folder : " + file.getName());
                listAllFiles(file);
            }else{
                try {
                    readContent(file,countOfRecords);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
}
