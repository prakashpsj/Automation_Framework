package com.policybazaar.components.tests;

import com.policybazaar.components.businesslogic.BL_ProductPage;
import com.policybazaar.components.setup.DriverSetup;
import com.policybazaar.components.utilities.Log;
import org.testng.annotations.Test;

public class TC004_LimitedPayValidationMonthly extends DriverSetup {
    Log log = new Log(this.getClass().getName());
    @Test
    public void TC004_limitedPayValidationMonthly() throws Throwable {
        log.startTestCase(TC_ID);

        BL_ProductPage pp = new BL_ProductPage(driver, TC_ID);
        pp.viewPlans();
        pp.limitedPayValidationMonthly();
        log.endTestCase(TC_ID);
    }
}
