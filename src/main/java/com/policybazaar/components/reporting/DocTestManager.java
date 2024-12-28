package com.policybazaar.components.reporting;

import java.util.HashMap;
import java.util.Map;

public class DocTestManager {
    static Map DocTest = new HashMap();

    public DocTestManager() {
    }

    public static synchronized String getTest() {
        return (String) DocTest.get((int) Thread.currentThread().getId());
    }

    public static synchronized void endTest(String fileID) throws Exception {
        WordGenerator.endTest(fileID);
    }

    public static synchronized void startTest(String reportID) throws Exception {
        String test = WordGenerator.GenerateDoc(reportID);
        DocTest.put((int) Thread.currentThread().getId(), test);
    }
}
