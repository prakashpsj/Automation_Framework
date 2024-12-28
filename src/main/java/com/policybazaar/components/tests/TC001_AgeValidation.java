package com.policybazaar.components.tests;

import com.policybazaar.components.businesslogic.BL_ProductPage;
import com.policybazaar.components.setup.DriverSetup;
import com.policybazaar.components.utilities.Log;
import org.testng.annotations.Test;

public class TC001_AgeValidation extends DriverSetup {

    Log log = new Log(this.getClass().getName());
    @Test
    public void TC001_ageValidation() throws Throwable {
        log.startTestCase(TC_ID);

        BL_ProductPage pp = new BL_ProductPage(driver, TC_ID);
        pp.viewPlans();
        pp.ageValidation();
        log.endTestCase(TC_ID);
    }
}
