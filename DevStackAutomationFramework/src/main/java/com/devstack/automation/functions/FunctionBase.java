package com.devstack.automation.functions;

import com.devstack.automation.testbase.SeleniumTestBase;
import org.openqa.selenium.WebDriver;

public class FunctionBase extends SeleniumTestBase {

    // ✅ Default constructor
    public FunctionBase() {

    }

    // ✅ Driver constructor
    public FunctionBase(WebDriver driver) {

        this.driver = driver;
    }
}
