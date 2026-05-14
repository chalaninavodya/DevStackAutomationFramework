package com.devstack.automation.tests.dashboard;

import com.devstack.automation.functions.commons.LIB_Common;
import com.devstack.automation.functions.commons.LIB_Dashboard;
import com.devstack.automation.reporter.ExtentReportManager;
import com.devstack.automation.testbase.SeleniumTestBase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DashboardTest extends SeleniumTestBase {

    private static final String EXCEL_FILE_PATH = "src/test/resources/testdata/DashboardTest.xlsx"; // Adjust path as needed
    private static final String SHEET_NAME = "loginTest";

    // ===========================================
    // ✅ REPORT SETUP
    // ===========================================

    @BeforeSuite
    public void startReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUpTest(Method method) {
        ExtentReportManager.createTest(method.getName());
    }

    @AfterMethod
    public void tearDownTest() throws InterruptedException {
        super.tearDown(); // 🔥 close browser
    }

    @AfterSuite
    public void endReport() {
        ExtentReportManager.flushReport();
    }

    // ===========================================
    // ✅ BASIC TEST (SMOKE)
    // ===========================================

    @Test
    public void verifyDashboard() {
        LIB_Common common = new LIB_Common(driver);
        common.tc_login("super@admin.com", "password");

        LIB_Dashboard dashboard = new LIB_Dashboard(driver);
        dashboard.verifyDashboardLoaded();
    }

    // ===========================================
    // 🔥 DATA-DRIVEN TEST (FROM EXCEL)
    // ===========================================

    @Test(dataProvider = "dashboardData")
    public void dashboardDataDrivenTest(String testCaseName,
                                        String action,
                                        String input,
                                        String expectedResult,
                                        String validationType) throws InterruptedException {

        ExtentReportManager.logInfo("Executing Test Case: " + testCaseName);
        ExtentReportManager.logInfo("Action: " + action + " | Input: " + input + " | Expected: " + expectedResult);

        LIB_Common common = new LIB_Common(driver);
        common.tc_login("super@admin.com", "password");

        LIB_Dashboard dashboard = new LIB_Dashboard(driver);

        try {
            switch (action) {

                case "loadDashboard":
                    dashboard.verifyDashboardLoaded();
                    ExtentReportManager.logPass("Dashboard loaded successfully");
                    break;

                case "getActiveReservations":
                    dashboard.verifyActiveReservations(); // You may need to implement this
                    ExtentReportManager.logPass("Active reservations verified");
                    break;

                case "getBorrowings":
                    dashboard.verifyCurrentBorrowings(); // You may need to implement this
                    ExtentReportManager.logPass("Current borrowings verified");
                    break;

                case "getOverdue":
                    dashboard.verifyOverdueBooks(); // You may need to implement this
                    ExtentReportManager.logPass("Overdue books verified");
                    break;

                case "getMemberStatus":
                    dashboard.verifyMemberStatus(); // You may need to implement this
                    ExtentReportManager.logPass("Member status verified");
                    break;

                case "getProfile":
                    dashboard.verifyProfileSection();
                    ExtentReportManager.logPass("Profile section verified");
                    break;

                case "loadBooks":
                    dashboard.verifyBooksSection();
                    ExtentReportManager.logPass("Books section loaded");
                    break;

                case "searchBook":
                    dashboard.searchBook(input);
                    ExtentReportManager.logPass("Search completed for: " + input);
                    break;

                case "filterGenre":
                    dashboard.filterByGenre(input);
                    ExtentReportManager.logPass("Filtered by genre: " + input);
                    break;

                case "filterAuthor":
                    dashboard.filterByAuthor(input);
                    ExtentReportManager.logPass("Filtered by author: " + input);
                    break;

                case "viewBook":
                    dashboard.viewBookDetails();
                    ExtentReportManager.logPass("Book details view opened");
                    break;

                case "borrowBook":
                    dashboard.borrowBook();
                    ExtentReportManager.logPass("Book borrowed successfully");
                    break;

                case "checkEmptyReservations":
                    dashboard.verifyEmptyReservations(); // You may need to implement this
                    ExtentReportManager.logPass("Empty reservations verified");
                    break;

                case "loadHistory":
                    dashboard.verifyReservationHistory(); // You may need to implement this
                    ExtentReportManager.logPass("Reservation history loaded");
                    break;

                case "pagination":
                    dashboard.goToNextPage();
                    ExtentReportManager.logPass("Pagination verified");
                    break;

                default:
                    ExtentReportManager.logWarning("No matching action for: " + action);
            }
        } catch (AssertionError e) {
            ExtentReportManager.logFail("Test failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            ExtentReportManager.logFail("Exception occurred: " + e.getMessage());
            throw e;
        }
    }

    // ===========================================
    // 🔥 DATA PROVIDER - READS FROM EXCEL FILE
    // ===========================================

    @DataProvider(name = "dashboardData")
    public Object[][] getDataFromExcel() throws IOException {
        return readExcelData(EXCEL_FILE_PATH, SHEET_NAME);
    }

    /**
     * Reads test data from Excel file
     * @param filePath Path to Excel file
     * @param sheetName Sheet name to read from
     * @return 2D Object array containing test data
     */
    private Object[][] readExcelData(String filePath, String sheetName) throws IOException {
        List<Object[]> testData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in " + filePath);
            }

            // Skip header row (row 0)
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;

                // Read all 5 columns: testCaseName, action, input, expectedResult, validationType
                String testCaseName = getCellValueAsString(row.getCell(0));
                String action = getCellValueAsString(row.getCell(1));
                String input = getCellValueAsString(row.getCell(2));
                String expectedResult = getCellValueAsString(row.getCell(3));
                String validationType = getCellValueAsString(row.getCell(4));

                // Skip empty rows
                if (testCaseName == null || testCaseName.trim().isEmpty()) continue;

                testData.add(new Object[]{testCaseName, action, input, expectedResult, validationType});
            }
        }

        if (testData.isEmpty()) {
            throw new RuntimeException("No test data found in Excel file: " + filePath);
        }

        System.out.println("Loaded " + testData.size() + " test cases from Excel");
        return testData.toArray(new Object[0][]);
    }

    /**
     * Converts Excel cell to String value
     * @param cell Excel cell
     * @return String value of the cell
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Handle numeric values (including scientific notation)
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}