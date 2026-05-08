package com.devstack.automation.functions;

import org.openqa.selenium.WebDriver;

public class FunctionBase {

    protected WebDriver driver;

    public FunctionBase(WebDriver driver) {
        this.driver = driver;
    }
}
