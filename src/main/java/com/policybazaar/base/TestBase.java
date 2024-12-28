package com.policybazaar.base;

import com.policybazaar.components.setup.DriverSetup;

import org.openqa.selenium.Dimension;
import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.List;

public class TestBase extends DriverSetup {
    public TestBase() {

    }

    @DataProvider(
            name = "devices"
    )
    public Object[][] devices() {
        Object[][] obj = (Object[][]) null;
        System.out.println(device[0]);
        if (device[0].equalsIgnoreCase("Perfecto Mobile")) {
            obj = new Object[][]{{new TestBase.TestDevice("PerfectoMobile", new Dimension(Integer.parseInt(device[5]), Integer.parseInt(device[6])), Arrays.asList("mobile"))}};
        }
        if (device[0].equalsIgnoreCase("Perfecto Web")) {
            obj = new Object[][]{{new TestBase.TestDevice("Perfecto Web", new Dimension(Integer.parseInt(device[1]), Integer.parseInt(device[2])), Arrays.asList("desktop"))}};
        }
        if (!device[0].contains("Perfecto")) {
            obj = new Object[][]{{new TestBase.TestDevice("mobile", new Dimension(Integer.parseInt(device[5]), Integer.parseInt(device[6])), Arrays.asList("mobile"))}, {new TestBase.TestDevice("tablet", new Dimension(Integer.parseInt(device[3]), Integer.parseInt(device[4])), Arrays.asList("tablet"))}, {new TestBase.TestDevice("desktop", new Dimension(Integer.parseInt(device[1]), Integer.parseInt(device[2])), Arrays.asList("desktop"))}};
        }


        return obj;
    }

    public static class TestDevice {
        private final String name;
        private final Dimension screenSize;
        private final List<String> tags;

        public TestDevice(String name, Dimension screenSize, List<String> tags) {
            this.name = name;
            this.screenSize = screenSize;
            this.tags = tags;
        }

        public String getName() {
            return this.name;
        }

        public Dimension getScreenSize() {
            return this.screenSize;
        }

        public List<String> getTags() {
            return this.tags;
        }

        public String toString() {
            return String.format("%s %dx%d", this.name, this.screenSize.width, this.screenSize.height);
        }
    }
}
