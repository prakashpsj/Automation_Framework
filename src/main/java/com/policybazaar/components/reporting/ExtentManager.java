package com.policybazaar.components.reporting;

import com.relevantcodes.extentreports.ExtentReports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    static ExtentReports extent;
    static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    static Date date = new Date();
    static  String d;
    static String filename;
    static final String filePath;
    public ExtentManager(){
    }
    public static synchronized ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath,true);
        }
        return extent;
    }
    static {
        d = dateFormat.format(date);
        filename = "Summary" + d + ".html";
        filePath = ".\\src\\main\\resources\\com\\policybazaar\\results\\html\\" + filename;
    }

}
