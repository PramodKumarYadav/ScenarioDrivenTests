package systemname.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunCommands {

    public void runPowerShell(String filePathPowerShellScript) {

        //String command = "powershell.exe  your command";
        String command = "powershell.exe -ExecutionPolicy ByPass -File " + filePathPowerShellScript;

        // Executing the command
        Process powerShellProcess = null;
        try {
            powerShellProcess = Runtime.getRuntime().exec(command);

            // Getting the results
            powerShellProcess.getOutputStream().close();

            String line;
            System.out.println("Standard Output:");
            BufferedReader stdout = new BufferedReader(new InputStreamReader(
                    powerShellProcess.getInputStream()));
            while ((line = stdout.readLine()) != null) {
                System.out.println(line);
            }
            stdout.close();

            System.out.println("Standard Error:");
            BufferedReader stderr = new BufferedReader(new InputStreamReader(
                    powerShellProcess.getErrorStream()));
            while ((line = stderr.readLine()) != null) {
                System.out.println(line);
            }
            stderr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }

    public void runBatchFile(String batchFileLocation) {
        try{
            // "file location here, don't forget using / instead of \\ to make it interoperable"
            Process p = Runtime.getRuntime().exec(batchFileLocation);
            p.waitFor();

        }catch( IOException ex ){
            //Validate the case the file can't be accesed (not enough permissions)
            System.out.println("File not accessible :" + ex);

        }catch( InterruptedException ex ){
            //Validate the case the process is being stopped by some external situation
            System.out.println("process interrupted" + ex);

        }
    }
}
