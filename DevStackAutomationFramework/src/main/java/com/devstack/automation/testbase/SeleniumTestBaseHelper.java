package com.devstack.automation.testbase;

import com.devstack.automation.reporter.ExtentReportManager;
import com.devstack.automation.utils.PropertyHandler;
import com.devstack.automation.utils.ThreadLocalWebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class SeleniumTestBaseHelper {
    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(){
        ExtentReportManager.initReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method){
        this.driver = ThreadLocalWebDriverManager.createDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(PropertyHandler.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result){
        if(result.getStatus()==ITestResult.FAILURE){
            ExtentReportManager.logFail(result.getName()+"\n"+result.getThrowable().getMessage());
        }else if(result.getStatus()==ITestResult.SKIP){
            ExtentReportManager.logSkip(result.getName()+"\n"+result.getThrowable().getMessage());
        }else {
            ExtentReportManager.logPass(result.getName()+" passed.");
        }
        ThreadLocalWebDriverManager.removeDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(){
        ExtentReportManager.flushReport();
    }


}
