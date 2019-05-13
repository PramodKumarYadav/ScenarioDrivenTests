package systemname.dir;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.File;

public class InputRoot {

    private final static Logger logger = Logger.getLogger(InputRoot.class);
    private final static String INPUT_ROOT = "C:\\SystemName\\InputRoot";

    public String getInputRootPath(){
        return INPUT_ROOT;
    }

    @Test
    public void makeInputDirectoryIfNotAlreadyExisting() {
        logger.info("Creating directory C:\\SystemName\\InputRoot\\ ...");

        File file = new File("C:\\SystemName\\InputRoot\\balls");
        file.mkdirs();
        file = new File("C:\\SystemName\\InputRoot\\toys");
        file.mkdirs();
        file = new File("C:\\SystemName\\InputRoot\\tshirts");
        file.mkdirs();
        file = new File("C:\\SystemName\\InputRoot\\shoes");
        file.mkdirs();

    }
    @Test
    public void removeFilesFromInputRoot() {

        logger.info("Removing files from subdirectories of INPUT_ROOT [balls,toys,tshirts,shoes]");
        purgeFilesInADirectory(INPUT_ROOT +"\\"+"balls");
        purgeFilesInADirectory(INPUT_ROOT +"\\"+"toys");
        purgeFilesInADirectory(INPUT_ROOT +"\\"+"tshirts");
        purgeFilesInADirectory(INPUT_ROOT +"\\"+"shoes");
        logger.info("Files removed from subdirectories of INPUT_ROOT [balls,toys,tshirts,shoes]");
    }
    private void purgeFilesInADirectory(String pathFolder) {

        File directory = new File(pathFolder);
        for(File file: directory.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

}

