package systemname.cmd;

import org.junit.jupiter.api.Test;

public class SetUp {

    RunCommands runCommands = new RunCommands();

    @Test
    public void initialSetUP(){
        cloneTestMasters();
        runDocker();
        publishInstallAllApplications();
    }
    @Test
    public void runSetUP(){
        gitPullTestMasters();
        runDocker();
        startAllApplications();
    }
    @Test
    public void cloneTestMasters(){
        runCommands.runBatchFile("C:/IntelliJ/Scripts/CloneTestMasters.bat");
    }
    @Test
    public void runDocker(){
        runCommands.runBatchFile("C:/IntelliJ/Scripts/RunDocker.bat");
    }
    @Test
    public void publishInstallAllApplications(){
        runCommands.runBatchFile( "C:/IntelliJ/Scripts/PublishInstallApplicationLocal.bat");
    }
    @Test
    public void startAllApplications(){
        runCommands.runBatchFile( "C:/IntelliJ/Scripts/StartServiceAllApplicationsLocal.bat");
    }
    @Test
    public void stopAllApplications(){
        runCommands.runBatchFile( "C:/IntelliJ/Scripts/StopServiceAllApplicationsLocal.bat");
    }
    @Test
    public void gitPullTestMasters(){
        runCommands.runBatchFile("C:/IntelliJ/Scripts/GitPullTestMasters.bat");
    }
}

