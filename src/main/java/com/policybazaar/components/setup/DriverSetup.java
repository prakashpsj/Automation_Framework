package com.policybazaar.components.setup;

import com.policybazaar.components.reporting.DocTestManager;
import com.policybazaar.components.reporting.ExtentManager;
import com.policybazaar.components.reporting.ExtentTestManager;
import com.policybazaar.components.utilities.Create_TestNGXML;
import com.policybazaar.components.utilities.Log;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Capabilities;


import static com.policybazaar.components.utilities.Create_TestNGXML.getConfigData;
import static org.openqa.selenium.remote.DesiredCapabilities.*;


public class DriverSetup {
    public static WebDriver driverAppTwo = null;
    public WebDriver driver = null;
    public String TC_ID = null;
    public String appURL = null;
    WindowsProcess process = new WindowsProcess();
    public String url = null;
    public static Log log = null;
    public static String[] device = null;
    public static String deviceType = null;
    public static String deviceValue = null;
    public static int Timeout = 50;

    public DriverSetup() {

        log = new Log(this.getClass().getName());
    }


    private void setDriver(int i, String[] deviceDetail) throws InterruptedException {
        if (i == 1) {
            if (this.appURL.split(",").length == 2) {
                if (deviceDetail[0].equalsIgnoreCase("Chrome")) {
                    this.driver = this.initChromeDriver(this.appURL.split(",")[0], 2);
                    driverAppTwo = this.initChromeDriver(this.appURL.split(",")[1], 2);
                } else if (deviceDetail[0].equalsIgnoreCase("IECompatibilityMode")) {
                    this.driver = this.initIEDriver(this.appURL.split(",")[0]);
                    driverAppTwo = this.initIEDriver(this.appURL.split(",")[1]);
                } else if (deviceDetail[0].equalsIgnoreCase("firefox")) {
                    this.driver = this.initFirefoxDriver(this.appURL.split(",")[0]);
                    driverAppTwo = this.initFirefoxDriver(this.appURL.split(",")[1]);
                } else if (deviceDetail[0].equalsIgnoreCase("Edge")) {
                    this.driver = this.initEdgeDriver(this.appURL.split(",")[0]);
                    driverAppTwo = this.initEdgeDriver(this.appURL.split(",")[1]);
                }
            } else if (deviceDetail[0].equalsIgnoreCase("Chrome")) {
                this.driver = this.initChromeDriver(this.appURL, 1);
            } else if (deviceDetail[0].equalsIgnoreCase("IECompatibilityMode")) {
                this.driver = this.initIEDriver(this.appURL);
            } else if (deviceDetail[0].equalsIgnoreCase("firefox")) {
                this.driver = this.initFirefoxDriver(this.appURL);
            } else if (deviceDetail[0].equalsIgnoreCase("Edge")) {
                this.driver = this.initEdgeDriver(this.appURL);
            } else if (deviceDetail[0].equalsIgnoreCase("EdgeBeta")) {
                this.driver = this.initEdgeBetaDriver(this.appURL);
            } else if (deviceDetail[0].equalsIgnoreCase("IEBetaCompatibilityMode")) {
                this.driver = this.initIEBetaDriver(this.appURL);
            }
        } else if (i == 2) {
            if (deviceDetail[0].equalsIgnoreCase("HeadLessChrome")) {
                this.driver = this.initHeadLessChromeDriver(this.appURL);
            } else if (deviceDetail[0].equalsIgnoreCase("HeadLessIE")) {
                this.driver = this.initIEHeadLessDriver(this.appURL);
            } else if (deviceDetail[0].equalsIgnoreCase("HeadLessFireFox")) {
                this.driver = this.initHeadLessFirefoxDriver(this.appURL);
            }
        }

    }
    private WebDriver initEdgeDriver(String appURL) throws InterruptedException {
        System.out.println("Launching Edge Browser..");
//       System.setProperty("webdriver.edge.driver","driver\\msedgedriver.exe");
//        List<String> lst = WebDriverManager.edgedriver().getDriverVersions();
//       for (String list: lst) {
//           System.out.println(list);
//       }

        WebDriverManager manager = WebDriverManager.edgedriver();
        manager.arch64().setup();


//        WebDriverManager.edgedriver().arch64().setup();
        Thread.sleep(10000);
        String version = manager.getDownloadedDriverVersion();
        System.out.println("Edge DRIVER DOWNLOADED VERSION FROM WebDriver Manager=========>" + version);
        this.driver = new EdgeDriver();
        this.driver.manage().window().maximize();
        this.driver.manage().deleteAllCookies();
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
        this.driver.get(appURL);
        return this.driver;
    }


//    private WebDriver initEdgeDriver(String appURL) {
//        System.out.println("Launching Edge Browser..");
//
//        WebDriverManager manager = WebDriverManager.edgedriver();
//        manager.arch64().setup();
//
//        EdgeOptions options = new EdgeOptions();
//        options.addArguments("--start-maximized");
//        options.addArguments("--disable-extensions");
//
//        try {
//            WebDriver driver = new EdgeDriver(options);
//            String version = manager.getDownloadedDriverVersion();
//            System.out.println("Edge DRIVER DOWNLOADED VERSION FROM WebDriver Manager=========>" + version);
//
//            driver.manage().deleteAllCookies();
//            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
//
//            driver.get(appURL);
//
//        } catch (Exception e) {
//            System.out.println("Error initializing EdgeDriver: " + e.getMessage());
//        }
//        return null;
//    }




//    private WebDriver initEdgeDriver(String appURL) throws InterruptedException {
//        System.out.println("Launching Edge Browser...");
//
////         Set the path to the EdgeDriver executable
////        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ankitkumar5\\edgedriver_win64\\msedgedriver.exe"); // Adjust the path as needed
//        WebDriverManager.edgedriver().setup();
//
//
//
//        // Initialize EdgeDriver
//        this.driver = new EdgeDriver();
//        this.driver.manage().window().maximize();
//        this.driver.manage().deleteAllCookies();
//        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
//        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
//
//        this.driver.get(appURL);
//        return this.driver;
//    }



//    private WebDriver initChromeDriver(String appURL, int index) {
//        WebDriver driver = null;
//
//        try {
//            System.out.println("Launching Google Chrome with a new profile...");
//
//            // Automatically manage ChromeDriver version
//            WebDriverManager.chromedriver().setup();
//
//            ChromeOptions options = new ChromeOptions();
//            options.addArguments("window-size=1366x4000");
//            options.addArguments("--disable-dev-shm-usage");
//
//            if (index == 1) {
//                driver = new ChromeDriver(options);
//            } else if (index == 2) {
//                driver = new ChromeDriver(options);
//            } else {
//                throw new IllegalArgumentException("Invalid index: " + index);
//            }
//
//            driver.manage().window().maximize();
//            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
//            driver.get(appURL);
//
//        } catch (Exception e) {
//            System.err.println("Error initializing ChromeDriver: " + e.getMessage());
//            return null; // Handle error as needed
//        }
//
//        return driver;
//    }



    private WebDriver initChromeDriver(String appURL, int index) {
        try {
            new Dimension(1366, 4000);
            System.out.println("Launching google chrome with new profile..");
//            System.setProperty("webdriver.chrome.driver","C:\\Users\\ankitkumar5\\chrome-win64\\chrome.exe");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments(new String[]{"window-size=1366x4000"});
            options.addArguments(new String[]{"--disable-dev-shm-usage"});
            if (index == 1) {
                this.driver = new ChromeDriver(options);
                this.driver.manage().window().maximize();
                this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
                this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
                this.driver.get(appURL);
                return this.driver;
            }

            if (index == 2) {
                driverAppTwo = new ChromeDriver(options);
                this.driver.manage().window().maximize();
                this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
                this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
                driverAppTwo.get(appURL);
                return driverAppTwo;
            }
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }
        return this.driver;
    }




    private WebDriver initEdgeBetaDriver(String appURL) throws InterruptedException {
        System.out.println("Launching Edge browser");
        WebDriverManager manager = WebDriverManager.edgedriver().driverVersion(getConfigData("EdgeBetaVersion"));
        manager.setup();
        Thread.sleep(10000);
        String version = manager.getDownloadedDriverVersion();
        EdgeOptions option = new EdgeOptions();
        option.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge Beta\\Application\\msedge.exe");
        System.out.println("Edge Driver Downloaded Version from WebDriver Manager=======> " + version);
        this.driver = new EdgeDriver(option);
        System.out.println("Beta Version ===> " + ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion());
        this.driver.manage().window().maximize();
        this.driver.manage().deleteAllCookies();
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
        this.driver.get(appURL);
        return this.driver;
    }

    private WebDriver initIEDriver(String appURL) {
        try {
            System.out.println("Delete temp sub directory");
            System.out.println("Launching Internet Explorer with new profile");
            WebDriverManager.iedriver().arch32().setup();
            InternetExplorerOptions option = new InternetExplorerOptions();
            option.attachToEdgeChrome();
            option.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
            option.ignoreZoomSettings();
            option.setCapability("ie.ensureCleanSession", true);
            option.setCapability("ignoreProtectedModeSetting", true);
            this.driver = new InternetExplorerDriver(option);
            System.out.println("t1");
            this.driver.manage().window().maximize();
            this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30L));
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30L));
            this.driver.get(appURL);
        } catch (Exception var4) {
            System.out.println("Error in InternetExplorer---->" + var4.getMessage());
        }
        return this.driver;
    }


    private WebDriver initIEBetaDriver(String appURL) {
        try {
            System.out.println("Delete temp sub directory");
            System.out.println("Launching Internet Explorer with new profile");
            WebDriverManager.iedriver().arch32().setup();
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.attachToEdgeChrome();
            options.attachToEdgeChrome();
            options.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge Beta\\Application\\msedge.exe");
            options.ignoreZoomSettings();
            options.setCapability("ie.ensureCleanSession", true);
            options.setCapability("ignoreProtectedModeSetting", true);
            this.driver = new InternetExplorerDriver(options);
            System.out.println("t1");
            this.driver.manage().window().maximize();
            this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30L));
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30L));
            this.driver.get(appURL);

        } catch (Exception var4) {
            System.out.println("Error in InternetExplorer---->" + var4.getMessage());
        }
        return this.driver;
    }

//    private WebDriver initChromeDriver(String appURL, int index) {
//        try {
//            new Dimension(1366, 4000);
//            System.out.println("Launching google chrome with new profile..");
////            System.setProperty("webdriver.chrome.driver","dependencies\\chromedriver-126.0.6478.127.exe");
//            WebDriverManager.chromedriver().setup();
//            ChromeOptions options = new ChromeOptions();
//            options.addArguments(new String[]{"window-size=1366x4000"});
//            options.addArguments(new String[]{"--disable-dev-shm-usage"});
//            if (index == 1) {
//                this.driver = new ChromeDriver(options);
//                this.driver.manage().window().maximize();
//                this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
//                this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
//                this.driver.get(appURL);
//                return this.driver;
//            }
//
//            if (index == 2) {
//                driverAppTwo = new ChromeDriver(options);
//                this.driver.manage().window().maximize();
//                this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
//                this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
//                driverAppTwo.get(appURL);
//                return driverAppTwo;
//            }
//        } catch (Exception var5) {
//            System.out.println(var5.getMessage());
//        }
//        return this.driver;
//    }


    private WebDriver initFirefoxDriver(String appURL) {
        boolean flag = false;

        try {
            System.out.println("Launching Firefox browser..");
            String path = getConfigData("FireFoxBinaryPath");
            System.out.println("path-->" + path);

            try {
                if (path.contains("exe")) {
                    flag = true;
                }
            } catch (Exception var10) {
                flag = false;
            }
            if (flag) {
                File pathToBinary = new File(path);
                FirefoxBinary binary = new FirefoxBinary(pathToBinary);
//                System.setProperty("webdriver.gecko.driver",System.getProperty("java.io.tmpdir") + "/Drivers/geckodriver.exe");
                System.setProperty("webdriver.gecko.driver", "dependencies\\geckodriver-1.0.exe");
                FirefoxProfile fp = new FirefoxProfile();
                ProfilesIni profilesIni = new ProfilesIni();
                fp.setPreference("network.proxy.type", 0);
                fp = profilesIni.getProfile("developer");
                FirefoxOptions opt = new FirefoxOptions();
                opt.setAcceptInsecureCerts(true);
                opt.setBinary(binary);
                opt.setProfile(fp);
                opt.setCapability("acceptSslCerts", true);
                this.driver = new FirefoxDriver(opt);
                System.out.println(this.driver);
                this.driver.manage().window().maximize();
                this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
                this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
                this.driver.get(appURL);
            } else {
                System.setProperty("webdriver.gecko.driver", System.getProperty("java.io.tmpdir") + "/Drivers/geckodriver.exe");
                System.setProperty("webdriver.gecko.driver", "dependencies\\geckodriver-1.0.exe");
                FirefoxProfile fp = new FirefoxProfile();
                ProfilesIni profilesIni = new ProfilesIni();
                fp.setPreference("network.proxy.type", 0);
                fp = profilesIni.getProfile("developer");
                FirefoxOptions opt = new FirefoxOptions();
                opt.setAcceptInsecureCerts(true);
                opt.setProfile(fp);
                opt.setCapability("acceptSslCerts", true);
                this.driver = new FirefoxDriver(opt);
                this.driver.manage().window().maximize();
                this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
                this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
                this.driver.navigate().to(appURL);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
            log.error(var11.getMessage());
        }
        return this.driver;
    }


    private WebDriver initHeadLessChromeDriver(String appURL) {
        try {
            this.process.kill("chromedriver.exe");
            System.out.println("Launching google chrome with new profile..");
//            System.setProperty("webdriver.chrome.driver", "Driver\\chromedriver.exe");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments(new String[]{"headless"});
            options.addArguments(new String[]{"window-size=1366x3500"});
            options.addArguments(new String[]{"disable-dev-shm-usage"});
            this.driver = new ChromeDriver(options);
            this.driver.manage().window().maximize();
            this.driver.navigate().to(appURL);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return this.driver;
    }


    private WebDriver initIEHeadLessDriver(String appURL) {
        try {
            System.out.println("Launching google chrome with new profile..");
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.attachToEdgeChrome();
            options.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge Beta\\Application\\msedge.exe");
            options.introduceFlakinessByIgnoringSecurityDomains();
            options.ignoreZoomSettings();
            options.enablePersistentHovering();
            options.takeFullPageScreenshot();
            options.setCapability("nativeEvents", false);
            options.requireWindowFocus();
            options.destructivelyEnsureCleanSession();
            options.setCapability("ignoreProtectModeSettings", true);
            options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
            this.driver = new InternetExplorerDriver(options);
            this.driver.manage().window().maximize();
            this.driver.navigate().to(appURL);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return this.driver;
    }


    private WebDriver initHeadLessFirefoxDriver(String appURL) {
        boolean flag = false;
        try {
            System.out.println("Launching Firefox browser..");
            String path = getConfigData("FireFoxBinaryPath");
            System.out.println("path-->" + path);
            try {
                if (path.contains("exe")) {
                    flag = true;
                }
            } catch (Exception var10) {
                flag = false;
            }
            if (flag) {
                File pathToBinary = new File(path);
                FirefoxBinary binary = new FirefoxBinary(pathToBinary);
                binary.addCommandLineOptions(new String[]{"--headless"});
//                System.setProperty("webdriver.gecko.driver",System.getProperty("java.io.tmpdir") + "/Drivers/geckodriver.exe");
                System.setProperty("webdriver.gecko.driver", "dependencies\\geckodriver-1.0.exe");
                new FirefoxProfile();
                FirefoxOptions opt = new FirefoxOptions();
                opt.setCapability("moz:firefoxOptions", opt.setBinary(binary));
                this.driver = new FirefoxDriver(opt);
                this.driver.manage().window().maximize();
                this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
                this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
                this.driver.navigate().to(appURL);
            } else {
                FirefoxBinary binary = new FirefoxBinary();
                binary.addCommandLineOptions(new String[]{"--headless"});
                WebDriverManager.firefoxdriver().setup();
                new FirefoxProfile();
                FirefoxOptions opt = new FirefoxOptions();
                this.driver = new FirefoxDriver(opt);
                this.driver.manage().window().maximize();
                this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60L));
                this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
                this.driver.navigate().to(appURL);
            }
        } catch (Exception var10) {
            log.error(var10.getMessage());
        }
        return this.driver;
    }

    @Parameters({"deviceType", "reportID", "deviceValue", "appURL", "tcID"})
    @BeforeClass
    public void initializeTestBaseSetup(String deviceType, String reportID, String deviceValue, String appURL, String tcID) throws Exception {
        System.out.println("initializeTestBaseSetup()");
        ExtentTestManager.startTest(tcID);
        DocTestManager.startTest(tcID);
        System.out.println("Test-->" + deviceType);
        this.TC_ID = tcID;
        this.appURL = appURL;
        String temp = null;
        DriverSetup.deviceType = deviceType;
        DriverSetup.deviceValue = deviceValue;
        try {
            if (deviceType.equalsIgnoreCase("Desktop web")) {
                temp = "Desktop";
                System.out.println("temp-->" + temp);
                device = this.getDeviceParameter(deviceType, deviceValue);
                this.setDriver(1, device);
            }
            if (deviceType.equalsIgnoreCase("HeadLess")) {
                temp = "HeadLess";
                device = this.getDeviceParameter(deviceType, deviceValue);
                this.setDriver(2, device);
            }
        } catch (Exception var8) {
            log.error(temp);
            log.error("Error in initialize Test Base Setup function...." + var8.getMessage());
            System.out.println(var8.getMessage());
        }
    }

    public WebDriver getDriver() {
        return this.driver;
    }


    @AfterClass
    public void classTearDown() throws Exception {
        try {
            if (this.driver != null) {
                this.driver.quit();
            }
            if (driverAppTwo != null) {
                driverAppTwo.quit();
            }
        } catch (Exception var2) {
            log.error(var2.getMessage());
        }
        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
        ExtentManager.getReporter().flush();
        this.process.kill("WerFault.exe");
        DocTestManager.endTest(DocTestManager.getTest());
        System.out.println("test case status->" + ExtentTestManager.getTest().getRunStatus().toString());
    }


    public String[] getDeviceParameter(String deviceType, String deviceValue) {
        String[] value = null;
        int index = 0;
        if (deviceValue.equalsIgnoreCase("DeviceCapability_Value1")) {
            index = 0;
        }
        if (deviceValue.equalsIgnoreCase("DeviceCapability_Value2")) {
            index = 1;
        }
        if (deviceValue.equalsIgnoreCase("DeviceCapability_Value3")) {
            index = 2;
        }
        if (deviceValue.equalsIgnoreCase("DeviceCapability_Value4")) {
            index = 3;
        }
        if (deviceValue.equalsIgnoreCase("DeviceCapability_Value5")) {
            index = 4;
        }
        if (deviceType.equalsIgnoreCase("Desktop Web")) {
            value = new String[]{Create_TestNGXML.desktopValue[index][0]};
        }
        if (deviceType.equalsIgnoreCase("HeadLess")) {
            value = new String[]{Create_TestNGXML.headLess[index][0]};
        }
        return value;
    }
}
