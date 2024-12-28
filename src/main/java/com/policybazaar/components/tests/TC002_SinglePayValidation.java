package com.policybazaar.components.tests;

import com.policybazaar.components.businesslogic.BL_ProductPage;
import com.policybazaar.components.setup.DriverSetup;
import com.policybazaar.components.utilities.Log;
import org.testng.annotations.Test;

public class TC002_SinglePayValidation extends DriverSetup {

    Log log = new Log(this.getClass().getName());
    @Test
    public void TC002_singlePayValidation() throws Throwable {
        log.startTestCase(TC_ID);

        BL_ProductPage pp = new BL_ProductPage(driver, TC_ID);
        pp.viewPlans();
        pp.singlePayValidation();
        log.endTestCase(TC_ID);
    }
}
