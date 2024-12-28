package com.policybazaar.components.utilities;

import com.policybazaar.components.reporting.Extent_Reporting;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.python.antlr.ast.Str;

import java.awt.*;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ElementAction {

    public static Logger Log = null;
    Extent_Reporting r = null;
    static String rgbaColor;
    static String actualColor;
    Long timeOut = 60L;
    Long pollingTime = 5L;

    public ElementAction() {
        Log = Logger.getLogger(this.getClass().getSimpleName());
        this.r = new Extent_Reporting();
    }

    /*
     * Use the locator key to return the web element
     *
     * @param locatorKey - string name set in the java file for a web element
     * @param type       - string to tell which type of locator is being passed in supported
     *                   types: (id, name, classname, link text, xpath, css selector)
     */

    public WebElement getElement(WebDriver driver, String type, String locatorKey) {
        WebElement element = null;
        try {
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            if (type.equalsIgnoreCase(Constants.ID)) {
                element = wait.until(x -> x.findElement(By.id(locatorKey)));
            } else if (type.equalsIgnoreCase(Constants.NAME)) {
                element = wait.until(x -> x.findElement(By.name(locatorKey)));
            } else if (type.equalsIgnoreCase(Constants.CLASSNAME)) {
                element = wait.until(x -> x.findElement(By.className(locatorKey)));
            } else if (type.equalsIgnoreCase(Constants.LINKTEXT)) {
                element = wait.until(x -> x.findElement(By.linkText(locatorKey)));
            } else if (type.equalsIgnoreCase(Constants.XPATH)) {
                element = wait.until(x -> x.findElement(By.xpath(locatorKey)));
            } else if (type.equalsIgnoreCase(Constants.CSSSELECTOR)) {
                element = wait.until(x -> x.findElement(By.cssSelector(locatorKey)));
            } else {
                Extent_Reporting.Log_Fail("getElementFailed", "The provided Locator Type" + type + "is not Correct", driver);
            }
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("GetElement Method Failed", "GetElement Method Failed due to locator'" + locatorKey + "' not found", driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            new Exception("GetElement Method Failed" + e.getMessage());
        }
        return element;
    }

    /*=================================================================================================================================================================================
                                               /* Click Methods Start/*
     =====================================================================================================================================================================================*/
    /* Clicks the web element provided
     *
     * Use the locator key to return the web element
     *
     * @param locatorKey - string name set in the java file for a web element
     * @param type       - string to tell which type of locator is being passed in supported
     *                   types: (id, name, classname, link text, xpath, css selector)
     */
    public void click(WebDriver driver, String type, String locatorKey, String elementName) {
        try {
            getElement(driver, type, locatorKey).click();
            Extent_Reporting.Log_report_img_alrt("Click", "Clicked on" + elementName + "Successfully");
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("Click", "Click FAILED on" + elementName, driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            new Exception(e.getMessage());
        }
    }

    public void clickUsingActions(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            Actions actions = new Actions(driver);
            WebElement webButton = driver.findElement(By.xpath(locatorKey));
            actions.click(webButton);
            Extent_Reporting.Log_Pass_Screenshot("Click", "Clicked SUCCESSFULLY on" + elementName, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("Click", "Clicked FAILED on" + elementName, driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            new Exception(e.getMessage());
        }
    }
 /*===================================================================================================================================
                                                 /* Click Methods END/*
  ==============================================================================================================*/

 /*==============================================================================================================================
                                                 /* Select Methods START/*
  ============================================================================================================================= */

    public void selectClass(WebDriver driver, WebElement parent, String elementToSelect, String elementName) throws Exception {
        try {
            Select dropdown = new Select(parent);
            dropdown.selectByVisibleText(elementToSelect);
            Extent_Reporting.Log_Pass_Screenshot("selectClass", "Selected" + elementToSelect + "in" + parent, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectClass", "Failed to Select" + elementName, driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void selectCheckBox(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement checkBox = getElement(driver, type, locatorKey);
            checkBox.click();
            Extent_Reporting.Log_Pass_Screenshot("selectCheckBox", elementName + "checkbox clicked", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectCheckBox", "Failed to Check" + elementName + "due to" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void selectRadio(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement radioButton = getElement(driver, type, locatorKey);
            radioButton.click();
            Extent_Reporting.Log_Pass_Screenshot("selectRadio", elementName + "radiobutton clicked", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectRadio", "Failed to select" + elementName + "due to" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
/*===================================================================================================================================
                                                 /* Select Methods END/*
  ==============================================================================================================*/

 /*==============================================================================================================================
                                                 /* Boolean Methods START/*
  ============================================================================================================================= */

    public boolean isElementPresent(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            getElement(driver, type, locatorKey);
            Extent_Reporting.Log_Pass_Screenshot("isElementPresent", elementName + "is Present", driver);
            Log.info(elementName + "Exist");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(e.getMessage());
            return false;
        }
    }

    public boolean isTextPresent(WebDriver driver, String type, String text) {
        boolean flag = false;

        try {
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            flag = wait.until(x -> x.findElement(By.xpath("//*[contains(.,'" + text + "')]")).isDisplayed());
            Extent_Reporting.Log_Pass_Screenshot("isTextPresent", text + "is present", driver);
            return flag;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return flag;
        }
    }

    public boolean isElementEnabled(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        boolean flag = false;

        try {
            WebElement element = getElement(driver, type, locatorKey);
            if (element.isEnabled()) {
                Extent_Reporting.Log_Pass_Screenshot("isElementEnabled", elementName + "is Enabled", driver);
                flag = true;
                System.out.println(flag);
            }
        } catch (Exception e) {
            flag = false;
            System.out.println(flag);
        }
        return flag;
    }


    public void isElementDisplayed(WebDriver driver, String type, String locatorKey, String elementName) throws InterruptedException {

        try {
            WebElement element = getElement(driver, type, locatorKey);
            if (element.isDisplayed()) {
                Extent_Reporting.Log_Pass_Screenshot("isElementDisplayed", elementName + "is Displayed", driver);
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
            Extent_Reporting.Log_Fail("isElementDisplayed", "Element not displayed", driver);
        }
    }

    public boolean isElementDisplay(WebDriver driver, String type, String locatorKey, String elementNAme) throws InterruptedException {
        boolean flag = false;
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(2L)).ignoring(Exception.class);
            WebElement element = wait.until(x -> x.findElement(By.xpath(locatorKey)));
            if (element.isDisplayed()) {
                flag = true;
                Extent_Reporting.Log_Pass_Screenshot("isElementDisplay", elementNAme + "is Displayed", driver);
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public void checkElementClickable(WebDriver driver, String type, String locatorKey, String Web_Element) throws Exception {
        try {
            WebElement ele = getElement(driver, type, locatorKey);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            Extent_Reporting.Log_Pass_Screenshot("checkElementClickable", Web_Element + "Element is clickable", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("checkElementClickable", "Element is not clickable due to " + e.getMessage());
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public boolean checkElementClickable(WebDriver driver, String type, String locatorKey, String Web_Element, Long timeInSec) throws Exception {
        try {
            WebElement ele = getElement(driver, type, locatorKey);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            Extent_Reporting.Log_Pass_Screenshot("checkElementClickable", Web_Element + "Element is clickable", driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementSelected(WebDriver driver, String type, String locatorKey) throws Exception {
        boolean flag = false;
        try {
            WebElement ele = getElement(driver, type, locatorKey);
            flag = ele.isSelected();
            return flag;
        } catch (Throwable t) {
            t.getMessage();
            return flag;
        }
    }

    /*===================================================================================================================================
                                                 /* Boolean Methods END/*
  ==============================================================================================================*/

 /*==============================================================================================================================
                                                 /* Alert and PopUp Methods START/*
  ============================================================================================================================= */


    public void acceptAlert(WebDriver driver) throws Exception {
        try {
            Alert alert = this.waitForAlertPresent(driver);
            if (!alert.equals(null)) {
                alert.accept();
                Extent_Reporting.Log_Pass_Screenshot("acceptAlert", "Alert Accepted", driver);
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
            Extent_Reporting.Log_Fail("acceptAlert", "Alert NOT Accepted", driver);
            throw new Exception(e.getMessage());
        }
    }

    public void selectPopUp(WebDriver driver, String arg) {
        boolean flag = false;

        try {
            for (int i = 0; i < 2 && !flag; ++i) {
                Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
                Set<String> pops = wait.until(x -> x.getWindowHandles());
                Iterator<String> it = pops.iterator();
                if (pops.size() > 1) {
                    System.out.println("No of Windows" + pops.size());
                    for (int j = 0; j < pops.size() && !flag; ++j) {
                        String popupHandle = it.next();
                        if (driver.switchTo().window(popupHandle).getTitle().contains(arg)) {
                            driver.switchTo().window(popupHandle);
                            flag = true;
                        }
                    }
                    pops.clear();
                }
            }
            Extent_Reporting.Log_Pass_Screenshot("selectPopUp", "Selected Pop Up Successfully", driver);
        } catch (Exception e) {
            Log.error(e.getMessage());
            System.out.println("Not able to Navigate to Window" + arg);
            Extent_Reporting.Log_Fail("selectPopUp", "Failed to Select Pop Up", driver);
        }
    }

/*===================================================================================================================================
        @return boolean true or false doesn't throw
        Except on failure but return false
  ==============================================================================================================*/

    public Boolean isAlertPresent(WebDriver driver) throws Exception {
        try {
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            Alert alert = wait.until(x -> x.switchTo().alert());
            Extent_Reporting.Log_Pass_Screenshot("waitForAlertPresent", "Successfully wait for Alert", driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    /*===================================================================================================================================
                                                 /* Alert and PopUp Methods END/*
  ==============================================================================================================*/

 /*==============================================================================================================================
                                                 /* Get Methods START/*
  ============================================================================================================================= */


    public String getInputTextValue(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement inputText = getElement(driver, type, locatorKey);
            Extent_Reporting.Log_Pass_Screenshot("getInputTextValue", elementName + "has" + inputText.getText(), driver);
            return inputText.getText();
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getInputTextValue", "Failed to get input value of" + elementName + "due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public String getAttributeValue(WebDriver driver, String type, String locatorKey, String attributeName, String elementName) throws Exception {
        try {
            WebElement element = getElement(driver, type, locatorKey);
            Extent_Reporting.Log_Pass_Screenshot("getAttributeValue", elementName + "has" + element.getAttribute(attributeName), driver);
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getAttributeValue", elementName + "does not Exist. ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public int getElementsSize(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            int size = wait.until(x -> x.findElements(By.xpath(locatorKey)).size());
            Extent_Reporting.Log_Pass_Screenshot("getElementsSize", elementName + "has size count of" + size, driver);
            return size;
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getElementsSize", "Failed to get size of" + elementName + "due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());

        }
    }


    public void getTableData(WebDriver driver, String locator, String type) throws Exception {
        try {
            WebElement table = getElement(driver, locator, type);
            List<WebElement> allRows = table.findElements(By.tagName("tr"));
            Iterator e = allRows.iterator();
            while (e.hasNext()) {
                WebElement row = (WebElement) e.next();
                List<WebElement> cells = row.findElements(By.tagName("td"));
                Iterator var8 = cells.iterator();

                while (var8.hasNext()) {
                    WebElement cell = (WebElement) var8.next();
                    System.out.println(cell.getText());
                }
            }
            Extent_Reporting.Log_Pass_Screenshot("getTableData", "Retrieved Table Data Successfully", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getTableData", "Failed to Retrieved due to" + e.getMessage(), driver);
            throw new Exception(e.getMessage());
        }
    }

    public String getObjectValue(WebDriver driver, WebElement webElement) throws Exception {
        try {
            JavascriptExecutor e = (JavascriptExecutor) driver;
            String value = (String) e.executeScript(String.format("return $('#%s').val();", webElement.getAttribute("id")), new Object[0]);
            Extent_Reporting.Log_Pass_Screenshot("getObjectValue", "Retrieved Object value Successfully", driver);
            return value;
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getObjectValue", "Failed to get Object value due to" + e.getMessage(), driver);
            throw new Exception(e.getMessage());
        }
    }

    public int getElementCountXpath(WebDriver driver, String type, String ObjectPath, String elementName) throws Exception {
        try {
            int count = 0;
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            count = wait.until(x -> x.findElements(By.xpath(ObjectPath)).size());
            Extent_Reporting.Log_Pass_Screenshot("getElementCountXpath", "Retrieved count as" + count + "Successfully", driver);
            return count;
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getElementCountXpath", "Failed to get element count due to" + e.getMessage(), driver);
            throw new Exception(e);
        }
    }

    public String getAlertText(WebDriver driver) throws InterruptedException {
        try {
            Alert alert = this.waitForAlertPresent(driver);
            if (!alert.equals(null)) {
                String text = alert.getText();
                Extent_Reporting.Log_Pass_Screenshot("getAlertText", "Retrieved Alert text as" + text + "Successfully", driver);
                return text;
            }
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getAlertText", "Not able to retrieve Alert text", driver);
            Log.error(e.getMessage());
        }
        return null;
    }


    public int getTableRowCount(WebDriver driver, String type, String locatorKey) {
        try {
            WebElement htmlTable = getElement(driver, type, locatorKey);
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            List<WebElement> rows = wait.until(x -> htmlTable.findElements(By.tagName("tr")));
            int size = rows.size();
            System.out.println(rows.size());
            Extent_Reporting.Log_Pass_Screenshot("getTableRowCount", "Retrieved Table Row count as" + size + "Successfully", driver);
            return rows.size();
        } catch (Exception e) {
            e.printStackTrace();
            Extent_Reporting.Log_Fail("getTableRowCount", "Not able to Retrieve Table Row count", driver);
            Log.error(e.getMessage());
            return 0;
        }
    }


/*===================================================================================================================================
                                                 /* Get Methods END/*
  ==============================================================================================================*/

 /*==============================================================================================================================
                                                 /* Text Methods START/*
  ============================================================================================================================= */

    public void enterText(WebDriver driver, String type, String locatorKey, String sValue, String elementName) throws Exception {
        Actions actions = new Actions(driver);

        try {
            WebElement inputText = getElement(driver, type, locatorKey);
            actions.moveToElement(inputText);
            actions.click();
            actions.sendKeys(sValue);
            actions.build().perform();
            Extent_Reporting.Log_Pass_Screenshot("enterText", sValue + "entered in" + elementName, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("enterText", "Text not entered in" + elementName + "due to ==> Exception ==> " + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void inputText(WebDriver driver, String type, String locatorKey, String sValue, String elementName) throws Exception {
        try {
            WebElement inputText = getElement(driver, type, locatorKey);
            inputText.sendKeys(sValue);
            Extent_Reporting.Log_Pass_Screenshot("inputText", sValue + "entered in" + elementName, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("inputText", elementName + "does not Exist. ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public void clearAndInputText(WebDriver driver, String type, String locatorKey, String sValue, String elementNAme) throws Exception {
        try {
            WebElement inputText = getElement(driver, type, locatorKey);
            inputText.clear();
            inputText.sendKeys(sValue);
            Extent_Reporting.Log_Pass_Screenshot("clearAndInputText", sValue + "entered in" + elementNAme, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("clearAndInputText", "Text not entered in" + elementNAme + "due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void clearTextValue(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement inputText = getElement(driver, type, locatorKey);
            inputText.clear();
            Extent_Reporting.Log_Pass_Screenshot("clearTextValue", "Text value cleared successfully", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("clearTextValue", "Failed to clear the text value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void clearAndInputTextValue(WebDriver driver, String type, String locatorKey, String value, String elementName) throws Exception {
        try {
            WebElement inputText = getElement(driver, type, locatorKey);
            Thread.sleep(1000L);
            inputText.clear();
            Thread.sleep(1000L);
            inputText.sendKeys(value);
            Extent_Reporting.Log_Pass_Screenshot("clearAndInputTextValue", elementName + "Cleared and Entered with" + value, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("clearAndInputTextValue", "Failed to clear and enter the text due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public void inputAndDeleteTextValue(WebDriver driver, String type, String locatorKey, String value, String elementName) throws Exception {
        try {
            WebElement inputText = getElement(driver, type, locatorKey);
            Thread.sleep(1000L);
            String s = Keys.chord(Keys.CONTROL, "a");
            inputText.sendKeys(s);
            inputText.sendKeys(Keys.DELETE);
            Thread.sleep(1000L);
            inputText.sendKeys(value);
            Extent_Reporting.Log_Pass_Screenshot("inputAndDeleteTextValue", elementName + "Cleared and Entered with" + value, driver);
        } catch (Throwable var6) {
            Extent_Reporting.Log_Fail("inputAndDeleteTextValue", "Failed to clear and enter the text value due to ==>Exception==>" + var6.getMessage(), driver);
            var6.printStackTrace();
            Log.error(var6.getMessage());
            throw new Exception(var6.getMessage());
        }

    }


    public void inputTextWithClick(WebDriver driver, String type, String locatorKey, String sValue, String elementName) throws Exception {
        try {
            WebElement inputText = getElement(driver, type, locatorKey);
            inputText.click();
            inputText.clear();
            System.out.println(sValue);
            inputText.sendKeys(sValue);
            inputText.sendKeys(Keys.ENTER);
            Extent_Reporting.Log_Pass_Screenshot("inputTextWithClick", sValue + "entered in" + elementName, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("inputTextWithClick", "Input Text with click failed due to Exception" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public boolean checkIfTextExistAndReport(WebDriver driver, String type, String element, String elementName, long timeInSec) throws InterruptedException {
        try {
            String strElement = "//*[contains(text(),'" + element + "')]";
            System.out.println("=============" + strElement);
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            Boolean result = wait.until(x -> x.findElement(By.xpath(strElement)).isDisplayed());
            Extent_Reporting.Log_Pass_Screenshot("checkIfTextExistAndReport", elementName + " is Exist", driver);
            System.out.println("Element Exist");
            this.waitForPageToLoad(driver, timeInSec);
            return result;
        } catch (Exception e) {
            System.out.println("Not Exist");
            e.printStackTrace();
            Extent_Reporting.Log_Fail("checkIfTextExistAndReport", "checkIfTextExistAndReport failed due to Exception" + e.getMessage(), driver);
            Log.error(e.getMessage());
            return false;
        }
    }


    public void typeNonEditable(WebDriver driver, String type, String locatorKey, String sValue, String elementName) throws Exception {
        this.removeAttribute(driver);
        this.clearAndInputText(driver, type, locatorKey, sValue, elementName);
    }

    /*===================================================================================================================================
                                                 /* Text Methods END/*
  ==============================================================================================================*/

 /*==============================================================================================================================
                                                 /* Wait Methods START/*
  ============================================================================================================================= */

    public void waitForElementTillSelected(WebDriver driver, String element, long timeInSec) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSec));
            wait.until(ExpectedConditions.elementToBeSelected(By.xpath(element)));
            Extent_Reporting.Log_Pass_Screenshot("waitForElementTillSelected", "Successfully wait done till element is selected", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("waitForElementTillSelected", "wait failed due to Exception" + e.getMessage(), driver);
            Log.error(e.getMessage());
        }
    }


    public void waitForTextPresent(WebDriver driver, String type, String text) throws InterruptedException {
        try {
            Thread.sleep(2000L);
            for (long timer = 0L; !this.isTextPresent(driver, type, text) && timer < Long.valueOf("30000"); timer += 50000L) {
                Thread.sleep(500L);
                Extent_Reporting.Log_Pass_Screenshot("waitForTextPresent", "Successfully wait done till Text is present", driver);
            }
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("waitForTextPresent", "wait failed due to Exception" + e.getMessage(), driver);
            Log.error(e.getMessage());
        }
    }


    /***** Wait for the element to be clickable or visible
     * @param locator   - String name set in the properties file for a web element
     * @param timeOutInSeconds  - desired wait time
     * @param condition   - This is either visibilityOfElementLocated or
     *                      ElementToBeClickable
     * @return
     */


    public WebElement explicitWait(WebDriver driver, String locator, long timeOutInSeconds, String condition) {
        WebElement element = null;
        try {
            if (condition.equalsIgnoreCase("visibilityOfElementLocated")) {
                element = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                return element;
            }
            if (condition.equalsIgnoreCase("ElementToBeClickable")) {
                element = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                return element;

            }
            if (condition.equalsIgnoreCase("presenceOfElementLocated")) {
                element = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
                return element;
            }
            if (condition.equalsIgnoreCase("visibilityOfElementLocated")) {
                element = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                return element;
            }
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("Explicit Wait Failed", "Element Not found due to error - " + e.getMessage() + "even after using Explicit wait", driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw e;
        }
        return element;
    }


    public void waitUntilExist(WebDriver driver, String locatorKey, long timeInSec) throws Exception {
        int i = 1;
        do {
            i++;
            Thread.sleep(10000L);
            try {
                Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
                WebElement webButton = wait.until(x -> x.findElement(By.xpath(locatorKey)));
                System.out.println("x" + i);
                webButton.getText();
            } catch (Exception e) {
                break;
            }
        } while (i < 100);
        this.waitForPageToLoad(driver, timeInSec);
    }


    public Alert waitForAlertPresent(WebDriver driver) throws Exception {
        try {
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            Alert alert = wait.until(x -> x.switchTo().alert());
            Extent_Reporting.Log_Pass_Screenshot("waitForAlertPresent", "Successfully wait for Alert", driver);
            return alert;
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("waitForAlertPresent", "Failed to wait for Alert", driver);
            throw new Exception(e.getMessage());
        }
    }


    public void waitForPopUp(WebDriver driver, String b) throws InterruptedException {
        Thread.sleep(3000L);
        try {
            this.selectPopUp(driver, b);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30L));
            Extent_Reporting.Log_Pass_Screenshot("waitForPopUp", "Successfully wait for Pop Up", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("waitForPopUp", "Failed to wait for Pop Up", driver);
            Log.error(e.getMessage());
        }
    }


 /*===================================================================================================================================
                                                 /* Wait Methods END/*
  ==============================================================================================================*/

 /*==============================================================================================================================
                                                 /* Mouse Methods START/*
  ============================================================================================================================= */


    public void mouseClick(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            Robot bot = new Robot();
            WebElement e = getElement(driver, type, locatorKey);
            int x = e.getLocation().getX();
            int y = e.getLocation().getY();
            System.out.println(x + "" + y);
            bot.mouseMove(x, y);
            bot.mousePress(16);
            bot.mouseRelease(16);
            Extent_Reporting.Log_Pass_Screenshot("mouseClick", "Successfully Clicked" + elementName + "by Robot class", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("mouseClick", "Failed to click" + elementName, driver);
            throw new Exception(e.getMessage());
        }
    }



    /*===================================================================================================================================
                                                 /* Mouse Methods END/*
  ==============================================================================================================*/

 /*==============================================================================================================================
                                                 /* DropBox Methods START/*
  ============================================================================================================================= */

    public void selectDropBoxValue(WebDriver driver, String type, String locatorKey, String value, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            select.selectByValue(value);
            Extent_Reporting.Log_Pass_Screenshot("selectDropBoxValue", elementName + "selected with" + value, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectDropBoxValue", "Failed to select drop box value due to ==>exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }

    public void selectDropBoxValueByVisibleTextWithClick(WebDriver driver, String type, String locatorKey, String value, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            selectDropBox.click();
            Select select = new Select(selectDropBox);
            select.selectByVisibleText(value);
            Extent_Reporting.Log_Pass_Screenshot("selectDropBoxValueByVisibleTextWithClick", elementName + "selected with" + value, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectDropBoxValueByVisibleTextWithClick", "Failed to select drop box value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            throw new Exception("Error - " + e.getMessage());
        }
    }

    public void selectDropBoxByVisibleText(WebDriver driver, String type, String locatorKey, String value, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            select.selectByVisibleText(value);
//            Thread.sleep(5000);
            Extent_Reporting.Log_Pass_Screenshot("selectDropBoxByVisibleText" + value, elementName + "selected with" + value, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectDropBoxByVisibleText", "Failed to select drop box value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }

    public void selectDropBoxByValue(WebDriver driver, String type, String locatorKey, String value, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            select.selectByValue(value);
            Extent_Reporting.Log_Pass_Screenshot("selectDropBoxByValue", elementName + "selected with" + value, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectDropBoxByValue", "Failed to select drop box value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }


    public void selectDropBoxByIndex(WebDriver driver, String type, String locatorKey, int index, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            select.selectByIndex(index);
            Extent_Reporting.Log_Pass_Screenshot("selectDropBoxByIndex", elementName + "selected with" + index, driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectDropBoxByIndex", "Failed to select drop box value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }


    public void selectDropBoxDefaultValue(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            select.selectByIndex(0);
            Extent_Reporting.Log_Pass_Screenshot("selectDropBoxDefaultValue", elementName + "selected with default value", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("selectDropBoxDefaultValue", "Failed to select drop box value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }

    public String getDropBoxDefaultValue(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            Extent_Reporting.Log_Pass_Screenshot("getDropBoxDefaultValue" + "Selected the dropbox value" + select.getFirstSelectedOption().getText(), elementName + "selected value is" + select.getFirstSelectedOption().getText(), driver);
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getDropBoxDefaultValue", "Failed to select the default dropbox value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }

    public String getDropBoxSelectedValue(WebDriver driver, String type, String locatorKey, int index, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            Extent_Reporting.Log_Pass_Screenshot("getDropBoxSelectedValue" + "Selected the dropbox value" + select.getFirstSelectedOption().getText(), elementName + "selected value is" + select.getFirstSelectedOption().getText(), driver);
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getDropBoxSelectedValue", "Failed to select the first default dropbox value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }

    public int getDropBoxSize(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            Extent_Reporting.Log_Pass_Screenshot("getDropBoxSize", elementName + "dropbox size is" + select.getOptions().size(), driver);
            return select.getOptions().size();
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getDropBoxSize", "Failed to get dropbox size due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }


    public String[] getDropBoxValue(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            List<WebElement> optionValue = select.getOptions();
            String[] value = new String[optionValue.size()];
            for (int i = 0; i < optionValue.size(); i++) {
                value[i] = optionValue.get(i).getText();
            }
            Extent_Reporting.Log_Pass_Screenshot("getDropBoxValue", "Successfully retrieved the dropbox value", driver);
            return value;
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getDropBoxValue", "Failed to get dropbox value due to ==>Exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());
        }
    }


    public List<WebElement> getDropBoxAllOptions(WebDriver driver, String type, String locatorKey, String elementName) throws Exception {
        try {
            WebElement selectDropBox = getElement(driver, type, locatorKey);
            Select select = new Select(selectDropBox);
            List<WebElement> optionValue = select.getOptions();
            Extent_Reporting.Log_Pass_Screenshot("getDropBoxAllOptions", "Successfully retrieved the dropbox all options", driver);
            return optionValue;
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("getDropBoxAllOptions", "Failed to get dropbox option due to ==>exception==>" + e.getMessage(), driver);
            e.printStackTrace();
            Log.error(e.getMessage());
            throw new Exception("Error - " + e.getMessage());

        }
    }

    /*=======================================================================================================================================================

                                                          /* Dropbox Methods End /*
     ==============================================================================================================================================================*/


    public void javascriptExecutorClick(WebDriver driver, String type, String locatorKey, String elementName) throws
            Exception {
        try {
            WebElement element = getElement(driver, type, locatorKey);
            JavascriptExecutor js = ((JavascriptExecutor) driver);
            js.executeScript("arguments[0].click();", element);
            Extent_Reporting.Log_Pass_Screenshot("Click", "Clicked on" + elementName + "Successfully", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("javascriptExecutorClick", "Failed to Click" + elementName, driver);
            throw new Exception(e.getMessage());
        }
    }


    public boolean switchToWindowUsingTitle(WebDriver driver, String title) throws Exception {
        try {
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            Set<String> availableWindows = wait.until(x -> x.getWindowHandles());
            if (!availableWindows.isEmpty()) {
                for (String windowId : availableWindows) {
                    if (driver.switchTo().window(windowId).getTitle().equals(title)) {
                        System.out.println("Active Window ==>" + driver.getTitle());
                        Extent_Reporting.Log_Pass_Screenshot("switchToWindowUsingTitle", "Switched successfully using Title -" + driver.getTitle(), driver);
                        return true;
                    }
                }

            }
            return false;
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("switchToWindowUsingTitle", "Failed to switch the window using Title -" + driver.getTitle(), driver);
            throw new Exception(e.getMessage());
        }
    }

    public void getTopWindow(WebDriver driver) throws Exception {
        try {
            Wait<WebDriver> wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(Exception.class);
            Set<String> ids = wait.until(x -> x.getWindowHandles());
            Iterator<String> iterator = ids.iterator();
            String nextID = null;
            while (iterator.hasNext()) {
                nextID = iterator.next();
            }
            driver.switchTo().window(nextID);
            Log.info("Switched To Top Window");
            Extent_Reporting.Log_Pass_Screenshot("Switch To Top Most Window", "Switched To Top Most Window Successfully", driver);
        } catch (Exception e) {
            Log.info("Not Switched To Top Window");
            Log.warn(e.getMessage());
            Log.fatal("Not Switched To Top Window" + e.getMessage());
            Extent_Reporting.Log_Fail("Switch To Top Most Window", "Failed to Switch To Top Most Window -", driver);
    e.printStackTrace();
    Log.error(e.getMessage());
    throw e;
        }
    }


    public void removeAttribute(WebDriver driver) throws Exception {
        try {
            List<WebElement> inputs = driver.findElements(By.tagName("input"));
            Iterator e = inputs.iterator();

            while (e.hasNext()) {
                WebElement input = (WebElement) e.next();
                ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('read only','read only')", new Object[]{input});
            }
            Extent_Reporting.Log_Pass_Screenshot("removeAttribute", "Attribute removed Successfully", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("removeAttribute", "Failed to remove the Attribute", driver);
            throw new Exception(e.getMessage());
        }
    }


    public void waitForPageToLoad(WebDriver driver, Long pageLoadTimeoutInSec) throws Exception {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(pageLoadTimeoutInSec)).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            Extent_Reporting.Log_Pass_Screenshot("waitForPageToLoad", "waitForPageToLoad passed", driver);
        } catch (Exception e) {
            Extent_Reporting.Log_Fail("waitForPageToLoad", "waitForPageToLoad Failed due to" + e.getMessage(), driver);
            throw new Exception(e.getMessage());

        }
    }
/*====================================================================================================================================================================================================================

                                                         /* Window Methods Start/*

 =============================================================================================================================================================================*/



    /* Scrolls within a window to put an element into view */

    public void scrollWindow(WebDriver driver, String type, String locator) throws Exception {
        try {
            WebElement element = getElement(driver, type,locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(2000);
            Extent_Reporting.Log_Pass_Screenshot("scrollWindow", "Scrolled the window successfully", driver);
        } catch (Exception e){
            Extent_Reporting.Log_Fail("scrollWindow", "Failed to scroll the window", driver);
            throw new Exception(e.getMessage());
        }
    }


}
