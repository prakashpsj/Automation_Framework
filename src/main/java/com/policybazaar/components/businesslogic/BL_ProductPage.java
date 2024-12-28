package com.policybazaar.components.businesslogic;

import com.policybazaar.components.pageobjects.PO_ViewPlans;
import com.policybazaar.components.reporting.Extent_Reporting;
import com.policybazaar.components.utilities.ElementAction;
import com.policybazaar.components.utilities.Excel_Handling;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.python.modules._threading._threading;

import java.time.Duration;

public class BL_ProductPage extends PO_ViewPlans {
    public WebDriver driver;
    public String TC_ID;
    ElementAction action = new ElementAction();

    public BL_ProductPage(WebDriver d, String tcId) {
        this.driver = d;
        this.TC_ID = tcId;
    }

    public void viewPlans() throws Exception {
        try {
            driver.manage().window().maximize();
            Extent_Reporting.Log_Pass_Screenshot("Pre-Quote page", "Pre-Quote page", driver);
            action.inputText(driver, "xpath", txtfieldYourName, Excel_Handling.Get_Data(TC_ID, "YourName"), "YourName");
            action.inputText(driver, "xpath", txtfieldMobileNumber, Excel_Handling.Get_Data(TC_ID, "MobileNumber"), "MobileNumber");
            action.inputText(driver, "xpath", txtfieldYourEmail, Excel_Handling.Get_Data(TC_ID, "Email"), "Email");
            action.isElementDisplayed(driver, "xpath", btnViewPlans, "View Plans Button");
            action.click(driver, "xpath", btnViewPlans, "View Plans Button");
        } catch (Exception t) {
            t.printStackTrace();
            Extent_Reporting.Log_Fail(TC_ID, "Function viewPlans is FAILED due to error" + t.getMessage());
            throw new Exception(t.getMessage());
        }
    }

    public void ageValidation() throws Exception {
        try {
            action.isElementDisplayed(driver, "xpath", btnGetDetail, "Button - Get Detail");
            action.click(driver, "xpath", btnGetDetailsSmartSIP, "Button - Smart SIP Wealth Secure");
            action.click(driver, "xpath", btnProceed, "Button - Proceed");
            action.selectRadio(driver, "xpath", radioGenderMale, "Radio - Gender male");
            action.scrollWindow(driver, "xpath", txtfieldDOB);
            action.inputText(driver, "xpath", txtfieldDOB, Excel_Handling.Get_Data(TC_ID, "DOB"), "Textfield - DOB");
            action.inputText(driver, "xpath", txtfieldAnnualIncome, Excel_Handling.Get_Data(TC_ID, "AnnualIncome"), "Textfield - Annual Income");
            action.clearAndInputText(driver, "xpath", txtfieldDOB, Excel_Handling.Get_Data(TC_ID, "DOB1"), "Textfield - DOB");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(txtUserTotalAge)));
            String actualText = element.getText();
            String expectedText = "undefined Years";
            if (actualText.equals(expectedText)) {
                System.out.println("Text matches!");
            } else {
                System.out.println("Text does not match.");
                System.out.println("Expected: " + expectedText);
                System.out.println("Actual: " + actualText);
                throw new AssertionError("Expected text: " + expectedText + " but found: " + actualText);
            }
            action.clearAndInputText(driver, "xpath", txtfieldDOB, Excel_Handling.Get_Data(TC_ID, "DOB2"), "Textfield - DOB");
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(txtUserTotalAge)));
            String actualText1 = element1.getText();
            String expectedText1 = "undefined Years";
            if (actualText.equals(expectedText1)) {
                System.out.println("Text matches!");
            } else {
                System.out.println("Text does not match.");
                System.out.println("Expected: " + expectedText1);
                System.out.println("Actual: " + actualText1);
                throw new AssertionError("Expected text: " + expectedText + " but found: " + actualText);
            }

        } catch (Exception t) {
            t.printStackTrace();
            Extent_Reporting.Log_Fail(TC_ID, "Function verifyDropdownValues is FAILED due to error" + t.getMessage());
            throw new Exception(t.getMessage());
        }
    }


    public void singlePayValidation() throws Exception {
        try {
            action.isElementDisplayed(driver, "xpath", btnGetDetail, "Button - Get Detail");
            action.click(driver, "xpath", btnGetDetailsSmartSIP, "Button - Smart SIP Wealth Secure");
            action.click(driver, "xpath", btnProceed, "Button - Proceed");
            action.selectRadio(driver, "xpath", radioGenderMale, "Radio - Gender male");
            action.inputText(driver, "xpath", txtfieldDOB, Excel_Handling.Get_Data(TC_ID, "DOB"), "Textfield - DOB");
            action.inputText(driver, "xpath", txtfieldAnnualIncome, Excel_Handling.Get_Data(TC_ID, "AnnualIncome"), "Textfield - Annual Income");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownPayFor, "One Time", "Dropdown - Pay For");
            action.clearAndInputText(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmount"), "Textfield - Investment Amount");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownWithdrawAfter, "15 Years", "Dropdown - Withdraw After");
//            Thread.sleep(5000);
            action.clearAndInputText(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmount1"), "Textfield - Investment Amount");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownWithdrawAfter, "25 Years", "Dropdown - Withdraw After");
        } catch (Exception t) {
            t.printStackTrace();
            Extent_Reporting.Log_Fail(TC_ID, "Function singlePayValidation is FAILED due to error" + t.getMessage());
            throw new Exception(t.getMessage());
        }
    }

    public void limitedPayValidationAnnually() throws Exception {
        try {
            action.isElementDisplayed(driver, "xpath", btnGetDetail, "Button - Get Detail");
            action.click(driver, "xpath", btnGetDetailsSmartSIP, "Button - Smart SIP Wealth Secure");
            action.click(driver, "xpath", btnProceed, "Button - Proceed");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownModeOfPremium, "Annually", "Dropdown - Mode Of Premium");

            action.selectRadio(driver, "xpath", radioGenderMale, "Radio - Gender male");
            action.selectDropBoxByValue(driver, "xpath", dropDownPayFor, "5", "Dropdown - Pay For");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownWithdrawAfter, "20 Years", "Dropdown - Withdraw After");
            action.scrollWindow(driver, "xpath", txtfieldDOB);
            action.inputText(driver, "xpath", txtfieldDOB, Excel_Handling.Get_Data(TC_ID, "DOB"), "Textfield - DOB");
            action.inputText(driver, "xpath", txtfieldAnnualIncome, Excel_Handling.Get_Data(TC_ID, "AnnualIncome"), "Textfield - Annual Income");
            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmountAnnually"), "Textfield - Investment Amount Annually");

            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmountAnnually1"), "Textfield - Investment Amount Annually");
        } catch (Exception t) {
            t.printStackTrace();
            Extent_Reporting.Log_Fail(TC_ID, "Function limitedPayValidation is FAILED due to error" + t.getMessage());
            throw new Exception(t.getMessage());
        }
    }

    public void limitedPayValidationMonthly() throws Exception {
        try {
            action.isElementDisplayed(driver, "xpath", btnGetDetail, "Button - Get Detail");
            action.click(driver, "xpath", btnGetDetailsSmartSIP, "Button - Smart SIP Wealth Secure");
            action.click(driver, "xpath", btnProceed, "Button - Proceed");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownModeOfPremium, "Monthly", "Dropdown - Mode Of Premium");
            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmountAnnually"), "Textfield - Investment Amount Annually");
            action.selectRadio(driver, "xpath", radioGenderMale, "Radio - Gender male");
            action.selectDropBoxByValue(driver, "xpath", dropDownPayFor, "5", "Dropdown - Pay For");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownWithdrawAfter, "20 Years", "Dropdown - Withdraw After");
            action.scrollWindow(driver, "xpath", txtfieldDOB);
            action.inputText(driver, "xpath", txtfieldDOB, Excel_Handling.Get_Data(TC_ID, "DOB"), "Textfield - DOB");
            action.inputText(driver, "xpath", txtfieldAnnualIncome, Excel_Handling.Get_Data(TC_ID, "AnnualIncome"), "Textfield - Annual Income");
            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmountAnnually1"), "Textfield - Investment Amount Annually");
            action.click(driver, "xpath", btnProceed, "Button - Proceed");
        } catch (Exception t) {
            t.printStackTrace();
            Extent_Reporting.Log_Fail(TC_ID, "Function limitedPayValidation is FAILED due to error" + t.getMessage());
            throw new Exception(t.getMessage());
        }
    }


    public void regularPayValidation() throws Exception {
        try {
            action.isElementDisplayed(driver, "xpath", btnGetDetail, "Button - Get Detail");
            action.click(driver, "xpath", btnGetDetailsSmartSIP, "Button - Smart SIP Wealth Secure");
            action.click(driver, "xpath", btnProceed, "Button - Proceed");

            action.selectRadio(driver, "xpath", radioGenderMale, "Radio - Gender male");
            action.inputText(driver, "xpath", txtfieldDOB, Excel_Handling.Get_Data(TC_ID, "DOB"), "Textfield - DOB");
            action.inputText(driver, "xpath", txtfieldAnnualIncome, Excel_Handling.Get_Data(TC_ID, "AnnualIncome"), "Textfield - Annual Income");

            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmountAnnually1"), "Textfield - Investment Amount Annually");
            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmountAnnually"), "Textfield - Investment Amount Annually");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownModeOfPremium, "Quarterly", "Dropdown - Mode Of Premium");

            action.selectDropBoxByVisibleText(driver, "xpath", dropDownPayFor, "15 Years", "Dropdown - Pay For");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownWithdrawAfter, "40 Years", "Dropdown - Withdraw After");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownPayFor, "25 Years", "Dropdown - Pay For");

        } catch (Exception t) {
            t.printStackTrace();
            Extent_Reporting.Log_Fail(TC_ID, "Function limitedPayValidation is FAILED due to error" + t.getMessage());
            throw new Exception(t.getMessage());
        }
    }
    public void premiumPaymentModeValidation() throws Exception {
        try {
            action.isElementDisplayed(driver, "xpath", btnGetDetail, "Button - Get Detail");
            action.click(driver, "xpath", btnGetDetailsSmartSIP, "Button - Smart SIP Wealth Secure");
            action.click(driver, "xpath", btnProceed, "Button - Proceed");
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownPayFor, "One Time", "Dropdown - Pay For");

            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmount"), "Textfield - Investment Amount Annually");
            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmount1"), "Textfield - Investment Amount Annually");
            Thread.sleep(9000);
            action.selectDropBoxByVisibleText(driver, "xpath", dropDownPayFor, "17 Years", "Dropdown - Pay For");

//            action.selectDropBoxByVisibleText(driver, "xpath", dropDownModeOfPremium, "Annually", "Dropdown - Mode Of Premium");
            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmountAnnually"), "Textfield - Investment Amount Annually");
            action.clearAndInputTextValue(driver, "xpath", txtfieldInvestmentAmount, Excel_Handling.Get_Data(TC_ID, "InvestmentAmountAnnually1"), "Textfield - Investment Amount Annually");



        }catch (Exception t) {
            t.printStackTrace();
            Extent_Reporting.Log_Fail(TC_ID, "Function limitedPayValidation is FAILED due to error" + t.getMessage());
            throw new Exception(t.getMessage());
        }
    }
    }


