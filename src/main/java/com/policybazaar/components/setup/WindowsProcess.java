package com.policybazaar.components.setup;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WindowsProcess {
    private String processName;

    public WindowsProcess() {
    }

    public void kill(String processName) throws Exception {
        this.processName = processName;
        this.isRunning();
    }

    private boolean isRunning() throws Exception {
        boolean bFlag = false;
        Process p = null;
        Process listTasksProcess = this.getRunTime().exec("tasklist");
        BufferedReader tasksListReader = new BufferedReader(new InputStreamReader(listTasksProcess.getInputStream()));

        String tasksLine;
        while ((tasksLine = tasksListReader.readLine()) != null) {
            if (tasksLine.contains(this.processName)) {
                System.out.println(tasksLine + "Killed by code for service -->" + this.processName);
                try {
                    p = this.getRunTime().exec("taskkill/F/IM" + this.processName);
                    System.out.println(p.exitValue());
                } catch (Exception var7) {
                    System.out.println(var7.getMessage());
                }
                bFlag = true;
            }
        }
        return bFlag;
    }

    private Runtime getRunTime() {
        return Runtime.getRuntime();
    }

    public static void main(String[] args) throws Exception {
        WindowsProcess w = new WindowsProcess();
        w.kill("chromedriver.exe");
        w.kill("IE");
    }
}
