package com.policybazaar.components.utilities;

import com.policybazaar.components.setup.WindowsProcess;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Create_TestNGXML {

    String Mode;
    public String UrlType;
    static XmlTest test = null;
    static XmlClass testClass = null;
    static TestNG testNG = new TestNG();
    static XmlSuite suite = new XmlSuite();
    Map<String, HashMap> ConfigData = null;
    public static String[][] mobileValue = (String[][]) null;
    public static String[][] webValue = (String[][]) null;
    public static String[][] desktopValue = (String[][]) null;
    public static String[][] headLess = (String[][]) null;
    String[] desktopKeys = null;
    String[] headlessKeys = null;
    String[] rwdKeys = null;
    public static String[][]    configDataV = (String[][]) null;
    public static int len = 0;
    Log log = null;
    int in = 0;

    public Create_TestNGXML() {
        this.readConfigData();
        this.log = new Log(this.getClass().getName());
    }

    @Test

    public void createXMLfile() throws Exception {
        System.out.println("1");
//        System.out.println(getConfigData("sheetCount"));

        int sheetCount = Integer.parseInt(getConfigData("sheetCount"));
        System.out.println("SheetCount---->" + sheetCount);
        String[] sheetName = (new Common_Functions()).GetMultipleTagXMLValue(getConfigData("sheetName"));
        System.out.println("SheetName Length--->" + sheetName.length);


        int i;
        for (i = 0; i < sheetCount; ++i) {
            System.out.println(sheetName[i]);
        }
        if (sheetCount == 1) {
            this.createTestNG(sheetName[0]);
        } else {
            for (i = 0; i < sheetCount; ++i) {
                this.createTestNG(sheetName[i]);
            }
        }
    }


    public void readConfigData() {
        Excel_Handling config = new Excel_Handling();
        config.ExcelReader(Constants.configPath, Constants.configSheet1Name, Constants.configPath, Constants.configSheet1Name);

        try {
            this.ConfigData = config.getExcelDataAll(Constants.configSheet1Name, "Select", "Y", "Config_Property");
            System.out.println(this.ConfigData);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        len = this.ConfigData.size();
        configDataV = new String[2][this.ConfigData.size()];
        this.in = 0;
        String[] Keys = new String[this.ConfigData.size()];


        for (Iterator var3 = this.ConfigData.keySet().iterator(); var3.hasNext(); ++this.in) {
            String key = (String) var3.next();
            Keys[this.in] = key;
            //System.out.println(Keys[this.in]);
        }

        for (int cIndex = 0; cIndex < this.ConfigData.size(); ++cIndex) {
            configDataV[0][cIndex] = Excel_Handling.Get_Data(Keys[cIndex], "Config_Property");
            configDataV[1][cIndex] = Excel_Handling.Get_Data(Keys[cIndex], "Property_Value");
        }
        Excel_Handling.close();
    }

    public void createTestNG(String sheetName) throws Exception {
        WindowsProcess w = new WindowsProcess();
        w.kill("chromedriver.exe");
        w.kill("IEHeadLess.exe");
        w.kill("msedgedriver.exe");
//        DOMConfigurator.configure("log4j.xml");
        Map<String, HashMap> deskTopDeviceConfigData = null;
        Map<String, HashMap> headlessDeviceConfigData = null;
        Excel_Handling deviceConfig = new Excel_Handling();
        deviceConfig.ExcelReader(Constants.configPath, "DeviceConfig", Constants.configPath, "DeviceConfig");
        String DeviceCapability_Value = "DeviceCapability_Value";

        Iterator var11;
        String key;
        int k;
        int l;

        try {
            deskTopDeviceConfigData = deviceConfig.getExcelDataAll(Constants.configSheet2Name, "DeviceType", "Desktop Web", "Variable_Name");
            desktopValue = new String[5][deskTopDeviceConfigData.size()];
            this.desktopKeys = new String[deskTopDeviceConfigData.size()];
            this.in = 0;

            for (var11 = deskTopDeviceConfigData.keySet().iterator(); var11.hasNext(); ++this.in) {
                key = (String) var11.next();
                this.desktopKeys[this.in] = key;
            }
            for (k = 0; k < 5; ++k) {
                for (l = 0; l < 1; ++l) {
                    desktopValue[k][l] = Excel_Handling.Get_Data(this.desktopKeys[l], DeviceCapability_Value + (k + 1));
                }
            }

        } catch (Exception var30) {
            var30.printStackTrace();
        }

        try {
            headlessDeviceConfigData = deviceConfig.getExcelDataAll(Constants.configSheet2Name, "DeviceType", "HeadLess", "Variable_Name");
            headLess = new String[5][headlessDeviceConfigData.size()];
            this.headlessKeys = new String[headlessDeviceConfigData.size()];
            this.in = 0;

            for (var11 = headlessDeviceConfigData.keySet().iterator(); var11.hasNext(); ++this.in)
            {
                key = (String) var11.next();
                this.headlessKeys[this.in] = key;
            }

            for (k = 0; k < 5; ++k) {
                for (l = 0; l < 1; ++l) {
                    headLess[k][l] = Excel_Handling.Get_Data(this.headlessKeys[l], DeviceCapability_Value + (k + 1));
                }
            }
        } catch (Exception var29) {
            var29.printStackTrace();
        }
        Excel_Handling.close();

        try {
            this.UrlType = getConfigData("URLType");
        } catch (Exception var27) {
            this.UrlType = "Single";
        }
        if (!this.UrlType.contains("Single") && !this.UrlType.contains("Multiple") && !this.UrlType.contains("BambooURL")) {
            this.UrlType = "Single";
        }

        try {

            this.Mode = getConfigData("Execution_Type");
        } catch (Exception var26) {
            this.Mode = "Single";
        }
        if (!this.Mode.contains("Single") && !this.Mode.contains("Multiple")) {
            this.Mode = "Single";
        }
        Excel_Handling excel = new Excel_Handling();
        excel.ExcelReader(Constants.datasheetPath, sheetName, Constants.datasheetPath, sheetName);

        try {
            this.ConfigData = excel.getExcelDataAll(sheetName, "Execute", "Y", "TC_ID");
        } catch (Exception var25) {
            var25.printStackTrace();
        }

        if (!this.ConfigData.isEmpty() && !this.Mode.contains("PDF")) {
            Map<String, HashMap> map = Excel_Handling.TestData;
            System.out.println("TestData--->" + Excel_Handling.TestData);
            System.out.println("Mode---->" + this.Mode);
            System.out.println("Test Start");
            int browserIns = Integer.parseInt(getConfigData("Browser_Instance"));
            Iterator var36;
//            String key;

            int i;
            if (!this.Mode.contains("Single")) {
                if (this.Mode.contains("Multiple")) {
                    String tcount = getConfigData("TreadCount");
                    int threadcount = Integer.parseInt(tcount);
                    if (tcount.equals(String.valueOf(threadcount))) {
                        System.out.println("Same");
                    }

                    int keycount = map.keySet().size();
                    System.out.println(keycount);
                    if (threadcount > keycount) {
                        threadcount = keycount;
                    }

                    String[] KeysSets = new String[keycount + 1];
                    i = 1;
                    int TestCaseId = 0;

                    for (Iterator var21 = map.keySet().iterator(); var21.hasNext(); ++i) {
                        key = (String) var21.next();
                        KeysSets[i] = key;
                    }
                    for (int j = 1; j <= keycount; j += threadcount) {
                        suite.setName(getConfigData("Regression_Suite_Name"));
                        suite.setParallel("tests");
                        System.out.println("here u go");
                        if (keycount - TestCaseId < threadcount) {
                            System.out.println("here u went");
                            i = keycount - TestCaseId;
                        } else {
                            System.out.println("here u left");
                            i = threadcount;
                        }
                        System.out.println("outerloop" + j + "TestCaseId:" + TestCaseId + "keycount:" + keycount + "loopcount:" + i);
                        suite.setThreadCount(i);

                        for (k = 1; k <= i; ++k) {
                            ++TestCaseId;
                            System.out.println("inner loop" + k + " " + KeysSets[TestCaseId]);
                            key = KeysSets[TestCaseId];
                            test = new XmlTest(suite);
                            test.setName(key);
                            test.setPreserveOrder(true);
                            test.addParameter("deviceType", Excel_Handling.Get_Data(key, "DeviceType"));
                            test.addParameter("deviceValue", Excel_Handling.Get_Data(key, "DeviceValue"));
                            test.addParameter("tcID", key);
                            test.addParameter("reportID", key);
                            if (this.UrlType.equalsIgnoreCase("Single")) {
                                test.addParameter("appURL", getConfigData("AppUrl"));
                            } else if (this.UrlType.equalsIgnoreCase("Multiple")) {
                                test.addParameter("appURL", Excel_Handling.Get_Data(key, "URL"));
                            } else if (this.UrlType.equalsIgnoreCase("BambooURL")) {
                                test.addParameter("appURL", "${bamboo_URL}");
                            }
                            test.addParameter("temp", "temp");
                            testClass = new XmlClass();
                            testClass.setName(Constants.testClassPath + Excel_Handling.Get_Data(key, "Class_Name"));
                            test.setXmlClasses(Arrays.asList(testClass));
                        }
                    }
                } else if (!this.Mode.contains("Hybrid")) {
                    if (this.Mode.contains("RWD")) {
                        var36 = map.keySet().iterator();

                        while (var36.hasNext()) {
                            key = (String) var36.next();
                            suite.setName(getConfigData("Regression_Suite_Name"));
                            test = new XmlTest(suite);
                            test.setName(key);
                            test.addParameter("deviceType", Excel_Handling.Get_Data(key, "DeviceType"));
                            test.addParameter("deviceValue", Excel_Handling.Get_Data(key, "DeviceValue"));
                            test.addParameter("tcID", key);
                            test.addParameter("reportID", key);

                            if (this.UrlType.equalsIgnoreCase("Single")) {
                                test.addParameter("appURL", getConfigData("AppUrl"));
                            } else if (this.UrlType.equalsIgnoreCase("Multiple")) {
                                test.addParameter("appURL", Excel_Handling.Get_Data(key, "URL"));
                            } else if (this.UrlType.equalsIgnoreCase("BambooURL")) {
                                test.addParameter("appURL", "${bamboo_URL}");
                            }
                            test.addParameter("temp", "temp");
                            testClass = new XmlClass();
                            testClass.setName(Constants.testClassPath + Excel_Handling.Get_Data(key, "Class_Name"));
                            test.setXmlClasses(Arrays.asList(testClass));
                        }
                    }

                } else {
                    var36 = map.keySet().iterator();

                    label284:
                    while (true) {
                        while (true) {
                            while (true) {
                                if (!var36.hasNext()) {
                                    break label284;
                                }

                                key = (String) var36.next();
                                suite.setName(getConfigData("Regression_Suite_Name"));
                                if (this.Mode.contains("Hybrid")) {
                                    if (Integer.parseInt(getConfigData("Browser_Instance")) > 1) {
                                        suite.setParallel("tests");
                                        suite.setThreadCount(browserIns);

                                        for (i = 1; i <= browserIns; ++i) {
                                            test = new XmlTest(suite);
                                            test.setName(key + "_Instance_" + i);
                                            test.addParameter("deviceType", Excel_Handling.Get_Data(key, "DeviceType"));
                                            test.addParameter("deviceValue", Excel_Handling.Get_Data(key, "DeviceValue"));
                                            test.addParameter("tcID", key);
                                            test.addParameter("reportID", key + "_Instance_" + i);
                                            if (this.UrlType.equalsIgnoreCase("Single")) {
                                                test.addParameter("appURL", getConfigData("AppUrl"));
                                            } else if (this.UrlType.equalsIgnoreCase("Multiple")) {
                                                test.addParameter("appURL", Excel_Handling.Get_Data(key, "URL"));
                                            } else if (this.UrlType.equalsIgnoreCase("BambooURL")) {
                                                test.addParameter("appURL", "${bamboo_URL}");

                                            }
                                            test.addParameter("temp", "temp" + i);
                                            testClass = new XmlClass();
                                            testClass.setName(Constants.testClassPath + Excel_Handling.Get_Data(key, "Class_Name"));
                                            test.setXmlClasses(Arrays.asList(testClass));
                                        }
                                    } else {
                                        test = new XmlTest(suite);
                                        test.setName(key);
                                        test.addParameter("deviceType", Excel_Handling.Get_Data(key, "DeviceType"));
                                        test.addParameter("deviceValue", Excel_Handling.Get_Data(key, "DeviceValue"));
                                        test.addParameter("tcID", key);
                                        test.addParameter("reportID", key);

                                        if (this.UrlType.equalsIgnoreCase("Single")) {
                                            test.addParameter("appURL", getConfigData("AppUrl"));
                                        } else if (this.UrlType.equalsIgnoreCase("Multiple")) {
                                            test.addParameter("appURL", Excel_Handling.Get_Data(key, "URL"));
                                        } else if (this.UrlType.equalsIgnoreCase("BambooURL")) {
                                            test.addParameter("appURL", "${bamboo_URL}");
                                        }
                                        test.addParameter("temp", "temp");
                                        testClass = new XmlClass();
                                        testClass.setName(Constants.testClassPath + Excel_Handling.Get_Data(key, "Class_Name"));
                                        test.setXmlClasses(Arrays.asList(testClass));

                                    }
                                } else if (this.Mode.contains("CBT")) {
                                }
                            }
                        }
                    }
                }
            } else {
                var36 = map.keySet().iterator();

                label305:
                while (true) {
                    while (true) {
                        while (true) {
                            if (!var36.hasNext()) {
                                break label305;
                            }


                            key = (String) var36.next();
                            suite.setName(getConfigData("Regression_Suite_Name"));
                            if (this.Mode.contains("Single")) {
                                if (Integer.parseInt(getConfigData("Browser_Instance")) > 1) {
                                    String var1 = "Test";
                                    suite.setParallel(XmlSuite.ParallelMode.getValidParallel(var1));
                                    suite.setThreadCount(browserIns);

                                    for (i = 1; i <= browserIns; ++i) {
                                        test = new XmlTest(suite);
                                        test.setName(key + "_Instance_" + i);
                                        test.addParameter("deviceType", Excel_Handling.Get_Data(key, "DeviceType"));
                                        test.addParameter("deviceValue", Excel_Handling.Get_Data(key, "DeviceValue"));
                                        test.addParameter("tcID", key);
                                        test.addParameter("reportID", key + "_Instance_" + i);
                                        if (this.UrlType.equalsIgnoreCase("Single")) {
                                            test.addParameter("aapURL", getConfigData("AppUrl"));
                                        } else if (this.UrlType.equalsIgnoreCase("Multiple")) {
                                            test.addParameter("aapURL", Excel_Handling.Get_Data(key, "URL"));
                                        } else if (this.UrlType.equalsIgnoreCase("BambooURL")) {
                                            test.addParameter("appURL", "${bamboo_URL}");
                                        }
                                        test.addParameter("temp", "temp" +i);
                                        testClass = new XmlClass();
                                        testClass.setName(Constants.testClassPath + Excel_Handling.Get_Data(key, "Class_Name"));
                                        test.setXmlClasses(Arrays.asList(testClass));
                                    }

                                } else {
                                    test = new XmlTest(suite);
                                    test.setName(key);
                                    test.addParameter("deviceType", Excel_Handling.Get_Data(key, "DeviceType"));
                                    test.addParameter("deviceValue", Excel_Handling.Get_Data(key, "DeviceValue"));
                                    test.addParameter("tcID", key);
                                    test.addParameter("reportID", key);
                                    if (this.UrlType.equalsIgnoreCase("Single")) {
                                        test.addParameter("appURL", getConfigData("AppUrl"));
                                    } else if (this.UrlType.equalsIgnoreCase("Multiple")) {
                                        test.addParameter("appURL", Excel_Handling.Get_Data(key, "URL"));

                                    } else if (this.UrlType.equalsIgnoreCase("BambooURL")) {
                                        test.addParameter("appURL", "${bamboo_URL}");

                                    }
                                    test.addParameter("temp", "temp");
                                    testClass = new XmlClass();
                                    testClass.setName(Constants.testClassPath + Excel_Handling.Get_Data(key, "Class_Name"));
                                    test.setXmlClasses(Arrays.asList(testClass));
                                }
                            } else if (this.Mode.contains("CBT")) {

                            }
                        }
                    }
                }
            }
            List<String> suites = new ArrayList();
            key = "testNG_" + sheetName + ".xml";
            File f = new File(key);
            f.createNewFile();
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(suite.toXml());
            bw.close();
            suites.add(f.getPath());
            testNG.setTestSuites(suites);
            testNG.run();
            f.delete();
            test = null;
            testClass = null;
            testNG = new TestNG();
            suite = new XmlSuite();


            try {
                if (getConfigData("ExecuteFailCase").equalsIgnoreCase("Yes")) {
                    TestNG failRunner = new TestNG();
                    List<String> suitesFail = new ArrayList();
                    key = System.getProperty("user.dir") + "\\test-output\\testng-failed.xml";
                    f = new File(key);
                    System.out.println(f.getPath());
                    suitesFail.add(f.getPath());
                    failRunner.setTestSuites(suitesFail);
                    failRunner.run();
                }
            } catch (Exception var24) {
            }
            Excel_Handling.close();
        }
    }

    public static String getConfigData(String Config_Property) {
        String value = null;

        try {
            for (int i = 0; i < len; ++i) {
                if (configDataV[0][i].equalsIgnoreCase(Config_Property)) {
                    value = configDataV[1][i];
                    break;
                }
                   }
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return value;
    }


    public static void main(String[] args) throws Exception {
        Create_TestNGXML c = new Create_TestNGXML();
        c.createXMLfile();
    }

}


