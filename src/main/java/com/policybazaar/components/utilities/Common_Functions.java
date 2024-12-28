package com.policybazaar.components.utilities;

import com.policybazaar.components.reporting.Extent_Reporting;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.*;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Common_Functions {

    static String snapPath = null;
    static String driverName = null;
    static String autoITExecutable = null;
    public static final String workingDir = System.getProperty("user.dir");
    public static final String homeDir = System.getProperty("user.home");
    public static final String downloadPath = homeDir + File.separator + Constants.DOWNLOADPATH;

    public Common_Functions() {
    }

    public String[] GetMultipleTagXMLValue(String tagname) {
        String[] sheetName = null;
        if (tagname.contains(",")) {
            sheetName = new String[tagname.split(",").length];
            sheetName = tagname.split(",");
        } else {
            sheetName = new String[]{tagname};
        }
        return sheetName;
    }

    public String captureScreenshotalert(String StepName) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date date = new Date();
            dateFormat.format(date);
            Robot robot = new Robot();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_a");
            Calendar now = Calendar.getInstance();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            System.out.println("taking screenshot");
            String name1 = "Summary" + formatter.format(now.getTime()) + StepName + formatter.format(now.getTime());
            ImageIO.write(screenShot, "PNG", new File(".\\src\\main\\resources\\com\\policybazaar\\results\\snapshots" + name1 + ".png"));
            snapPath = "../snapshots/" + name1 + ".png";
        } catch (Exception var10) {
            System.out.println("Issue with snapshot capture");
        }
        return snapPath;
    }

    public String GetXMLTagValue(String xmlpath, String tagname) {
        String val = null;
        try {
            File f = new File(xmlpath);
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(f);
            val = doc.getElementsByTagName(tagname).item(0).getTextContent();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return val;
    }

    public By locatorParser(String locator) {
        By loc = By.id(locator);
        if (locator.contains("id")) {
            loc = By.id(locator.substring(locator.indexOf("\"") + 1, locator.length() - 2));
        } else if (locator.contains("name")) {
            loc = By.name(locator.substring(locator.indexOf("\"") + 1, locator.length() - 2));
        } else if (locator.contains("cssSelector")) {
            loc = By.cssSelector(locator.substring(locator.indexOf("\"") + 1, locator.length() - 2));
        }
        if (locator.contains("xpath")) {
            loc = By.xpath(locator.substring(locator.indexOf("\"") + 1, locator.length() - 2));
        }
        return loc;
    }

    public String captureScreenshot(WebDriver driver) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date date = new Date();
            String d = dateFormat.format(date);
            File srcFile = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String name = d + srcFile.getName();
            FileUtils.copyFile(srcFile, new File(".\\src\\main\\resources\\com\\policybazaar\\results\\snapshots\\" + name));
            snapPath = ".\\src\\main\\resources\\com\\policybazaar\\results\\snapshots\\" + name;
            snapPath = "../snapshots/" + name + ".png";
        } catch (Exception var7) {
            System.out.println("Issue with snapshot capture");
        }
        return snapPath;
    }

    public String captureScreenshot(WebDriver driver, String StepName) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date date = new Date();
            String d = dateFormat.format(date);
            System.out.println("Capture screen shot -->" + driver);
            File srcFile = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            StepName = StepName.replace("", "_");
            String name = Constants.Resultfilename + StepName + d + ".png";
            name = name.replace(".html", "_");
            File destFile = new File(".\\src\\main\\resources\\com\\policybazaar\\results\\snapshots\\" + name);
            FileUtils.copyFile(srcFile, destFile);
            String str = destFile.getAbsolutePath();
            str = str.replace("\\.\\", "\\");
            snapPath = str;
            snapPath = "../snapshots/" + name;
        } catch (Exception var10) {
            System.out.println("Issue with snapshot capture -->" + var10.getMessage());
        }
        return snapPath;
    }

    /***=========================================================================================================================================
     /*Auto IT Methods Start/*
     ========================================================================================================================================
     * @Author Ankit Kumar
     * @Following method is to Upload file (present in the working directory) using AutoIT
     */
    public static void uploadFilesFromDownloads(WebDriver driver, String fileName, String autoITExe) throws Exception {
        driverName = String.valueOf(driver);
        String fileToBeUploaded = downloadPath + File.separator + fileName; // Passed as command line parameter to AutoIT executable below
        System.out.println(fileToBeUploaded);
        if (driverName.contains("Chrome")) {
            autoITExecutable = workingDir + Constants.AUTOITSCRIPTPATH + autoITExe + " " + fileToBeUploaded;
            Runtime.getRuntime().exec(autoITExecutable);
            Extent_Reporting.Log_Pass_Screenshot("AutoIT Script Run Pass", "Uploaded" + fileName + "successfully", driver);
        } else if (driverName.contains("Edge")) {
            autoITExecutable = workingDir + Constants.AUTOITSCRIPTPATH + autoITExe;
            Thread.sleep(3000);
            Runtime.getRuntime().exec(autoITExecutable);
            Extent_Reporting.Log_Pass_Screenshot("AutoIT Script Ron Pass", "Uploaded" + fileName + "successfully", driver);
        } else
            Extent_Reporting.Log_Fail("Driver is neither Edge or Chrome", "failed");
    }

    /**
     * @Author Ankit Kumar
     * @Following method is to upload a file (present in working directory) using AutoIT
     */

    public static void uploadFile(WebDriver driver, String fileName, String autoITExe) throws Exception {
        Thread.sleep(4000);
        String fileToBeUploaded = workingDir + Constants.AUTOITSCRIPTPATH + fileName; // passed as a command line parameter to AutoIT executable below
        driverName = String.valueOf(driver);
        if (driverName.contains("Chrome")) {
            autoITExecutable = workingDir + Constants.AUTOITSCRIPTPATH + autoITExe + " " + fileToBeUploaded;
            Runtime.getRuntime().exec(autoITExecutable);
            Extent_Reporting.Log_Pass_Screenshot("Upload File AutoIT Script Run Pass", "Uploaded" + fileName + "successfully", driver);
        } else if (driverName.contains("Edge")) {
            autoITExecutable = workingDir + Constants.AUTOITSCRIPTPATH + autoITExe + " " + fileToBeUploaded;
            Runtime.getRuntime().exec(autoITExecutable);
            Extent_Reporting.Log_Pass_Screenshot("Upload File AutoIT Script Run Pass", "Uploaded" + fileName + "successfully", driver);
        } else
            Extent_Reporting.Log_Fail("Upload File AutoIT Script Run Pass", "Failed to upload" + fileName);
    }


    /**
     * @Following is generic method used to verify that attachment/file is download successfully and deleted from downloads folder of Home Directory
     */

    public  static boolean verifyFileDownloadAndDelete(WebDriver driver, String fileName, String downloadAutoItExe)throws Exception{
        Thread.sleep(10000);
        driverName = String.valueOf(driver);
        System.out.println("driveName===========>" + driverName);
        if (driverName.contains("Chrome")){
            File dir = new File(downloadPath);
            File[] dirContents = dir.listFiles();
            for (int i = 0; i<dirContents.length; i++) {
                if (dirContents[i].getName().equals(fileName)){
                    dirContents[i].delete();
                    Extent_Reporting.Log_Pass_Screenshot("verifyFileDownloadAndDelete", "verified" + fileName + "downloaded Successfully", driver);
                    return true;
                }
            }
            Extent_Reporting.Log_Fail("verifyFileDownloadAndDelete", "Failed to Download" + fileName);
            return false;
        } else if (driverName.contains("Edge")){
            String autoITExecutable = workingDir + Constants.AUTOITSCRIPTPATH + downloadAutoItExe;
            Thread.sleep(3000);
            Runtime.getRuntime().exec(autoITExecutable);
            Thread.sleep(3000);
            Extent_Reporting.Log_Pass_Screenshot("AutoIT Script to Download File", "Downloaded and Saved the" + fileName + "successfully", driver);
            Thread.sleep(5000);
            File dir = new File(downloadPath);
            File[] dirContents = dir.listFiles();
            for (int i = 0; i < dirContents.length; i++){
                if (dirContents[i].getName().equals(fileName)){
                    Thread.sleep(2000);
                    dirContents[i].delete();
                    Thread.sleep(2000);
                    Extent_Reporting.Log_Pass_Screenshot("verifyFileDownloadAndDelete", "Verified" + fileName + "downloaded successfully", driver);
                    return true;
                }
            }
            Extent_Reporting.Log_Fail("verifyFileDownloadAndDelete", "Failed to download" + fileName);
            return false;
        } else
            Extent_Reporting.Log_Fail("verifyFileDownload", "failed");
        return false;

    }

    /**
     * @Following is generic used to verify that attachment/file is downloaded successfully
     */

    public static boolean verifyFileDownload(WebDriver driver, String fileName, String downloadAutoItExe) throws Exception {
        Thread.sleep(6000);
        driverName = String.valueOf(driver);
        System.out.println("driverName===========>" + driverName);
        if (driverName.contains("Chrome")){
            File dir = new File(downloadPath);
            File[] dirContents = dir.listFiles();
            for (int i = 0; i < dirContents.length; i++){
                if (dirContents[i].getName().equals(fileName)){
                    Extent_Reporting.Log_Pass_Screenshot("verifyFileDownload", "Verified" + fileName + "downloaded successfully", driver);
                    return true;
                }
            }
            Extent_Reporting.Log_Fail("verifyFileDownload", "Failed to download" + fileName);
            return false;
        } else if (driverName.contains("Edge")) {
            String autoITExecutable = workingDir + Constants.AUTOITSCRIPTPATH + downloadAutoItExe;
            Runtime.getRuntime().exec(autoITExecutable);
            Thread.sleep(5000);
            Extent_Reporting.Log_Pass_Screenshot("AutoIT Script to Download File", "Downloaded and saved the" + fileName + "successfully", driver);
            File dir = new File(downloadPath);
            File[] dirContents = dir.listFiles();
            for (int i = 0; i<dirContents.length; i++){
                if (dirContents[i].getName().equals(fileName)){
                    Extent_Reporting.Log_Pass_Screenshot("verifyFileDownload", "Verified" + fileName + "downloaded successfully",driver);
                    return true;
                }
            }
            Extent_Reporting.Log_Fail("verifyFileDownload", "Failed to download" + fileName);
            return false;
        } else Extent_Reporting.Log_Fail("verifyFileDownload", "Failed");
        return false;
    }

    /***
     * @Following Method is used to verify the download file
     */

    public static boolean isFileDownloaded(WebDriver driver, String fileName) throws Exception{
        String downloadPath = homeDir + File.separator + Constants.DOWNLOADPATH;
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        for (int i = 0; i < dirContents.length; i++){
            if (dirContents[i].getName().equals(fileName)){
                Extent_Reporting.Log_Pass_Screenshot("isFileDownloaded", "Verified" + fileName + "downloaded Successfully", driver);
                return true;
            }
        } Extent_Reporting.Log_Fail("isFileDownloaded", "Failed to download" + fileName);
        return false;
    }

    /***
     * @Following Method is used to verify the downloaded file and delete the file after verification
     */

    public static boolean isFileDownloadedAndDelete(WebDriver driver, String fileName) throws Exception{
        String downloadPath = homeDir + File.separator + Constants.DOWNLOADPATH;
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        for (int i = 0; i<dirContents.length; i++){
            if (dirContents[i].getName().equals(fileName)){
                /* file has been found, it can be now deleted:*/
                Extent_Reporting.Log_Pass_Screenshot("isFileDownloaded", "Verified" + fileName + "download Successfully", driver);
                return  true;
            }
        }
        Extent_Reporting.Log_Fail("isFileDownloaded", "Failed to download" + fileName);
        return false;
    }

    /**
     * @Author Ankit Kumar
     * @Following Method is open to a file after download using AutoIT
     */

    public static void openFile(WebDriver driver, String fileName, String openFileAutoITExe) throws Exception{
        Thread.sleep(4000);
        driverName = String.valueOf(driver);
        if (driverName.contains("Chrome")){
            autoITExecutable = homeDir + File.separator + Constants.DOWNLOADPATH;
//            autoITExecutable = homeDir + Constants.DOWNLOADPATH + openFileAutoITExe;
            Runtime.getRuntime().exec(autoITExecutable);
            Extent_Reporting.Log_Pass_Screenshot("Open File AutoIT Script Run Pass", "Opened" + fileName + "successfully", driver);
        } else {
            Extent_Reporting.Log_Fail("Open File AutoIT Script Run Pass", "Failed to Open" +fileName);
        }
    }
    /*===================================================================================================================================================================================
                                                  /* Auto IT Methods END/*
    ====================================================================================================================================================================================== */


    /*****===============================================================================================================================================================================================
     /* Random String Number Methods START/*
     ==================================================================================================================================================================================
     * @param n is the length fo the number
     * @return
     * @Following method is used to generate a random number with given length
     */


    public static int generateRandomDigits(int n){
        int number = (int) Math.pow(10, n - 1);
        int newNumber = number + new Random().nextInt(9 * number);
        System.out.println("newNumber = " +newNumber);
        return newNumber;
    }
    public static String generateRandomString(int n){
        String randomString = RandomStringUtils.randomAlphabetic(n);
        System.out.println("randomString = " +randomString);
        return randomString;
    }

     /*===================================================================================================================================================================================
                                                  /* Random String Number Methods END/*
     ====================================================================================================================================================================================== */


    /*===============================================================================================================================================================================================
                                                  /* Process Kill Methods START/*
     =================================================================================================================*/


    public static void killIEDriverServer() throws Exception{
        Thread.sleep(5000);
        Runtime.getRuntime().exec("taskkill/T /F/IM IEDriverServer.exe");
        Thread.sleep(5000);
        System.out.println("============Killed the IEDriverServer.exe Successfully===========");
    }

    public static void deleteTempSubFolder(String filePath){
        File file = new File(filePath);
        //call deleteDirectory function to delete
        // subdirectory and files
        deleteDirectory(file);
        //delete main GFG folder
        // file.delete();
    }

    public static void deleteDirectory(File file){
        // store all the paths of files and folders present
        //inside directory
        for (File subfile : file.listFiles()){
            // if it is a subfolder e.g Demo and Test
            // recursively call function empty subfolder
            if (subfile.isDirectory()){
                deleteDirectory(subfile);
            }
            // delete files and subfolders
            subfile.delete();
        }
    }

    /*===================================================================================================================================================================================
                                                  /* Process Kill Methods END/*
     ====================================================================================================================================================================================== */


    /*===============================================================================================================================================================================================
                                                  /* Dates Methods START/*
     =================================================================================================================*/

    public static String getFutureDate(int days){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(5, days);
        String futureDate = sdf.format(cal.getTime());
        return futureDate;
    }

    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String currentDate = sdf.format(date);
        return currentDate;
    }

    public static String getPastDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        return sdf.format(yesterday).toString();
    }

    /* The resulting number ranges from 1 (Sunday) to 7 (Saturday).

     */

    public static int getDayNumberOld(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    /*===================================================================================================================================================================================
                                                  /* Dates Methods END/*
     ====================================================================================================================================================================================== */


}
