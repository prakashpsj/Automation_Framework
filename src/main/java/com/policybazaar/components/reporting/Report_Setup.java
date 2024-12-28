package com.policybazaar.components.reporting;

import com.policybazaar.components.utilities.Constants;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report_Setup {

    public static ExtentTest test;
    static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    static Date date= new Date();
    static String d;
    static String filename;
    public static ExtentReports extent;
    public Report_Setup(){
    }
    public static void InitializeReport(String testCaseName){
        Constants.Resultfilename = filename;
        System.out.println(filename);
        test = extent.startTest(testCaseName, "Execution started for :"+testCaseName);
    }
    static {
        d = dateFormat.format(date);
        filename = "Summary" + d + ".html";
        extent = new ExtentReports(".\\src\\main\\resources\\com\\policybazaar\\results\\html\\" + filename,  true);
    }

}
