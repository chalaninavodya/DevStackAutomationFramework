package com.devstack.automation.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHandler {
    private static final String EXCEL_PATH = "src/test/resources/excelData/";

    @DataProvider(name="commonDataProvider")
    public static Object[][] commonDataProvider(Method method) {
        String testClassName = method.getDeclaringClass().getSimpleName();
        String testMethodName = method.getName();
        String excelFileName = testClassName+".xlsx";

        Class<?> modelClass = getModelClass(testClassName);


        return readExcelData(EXCEL_PATH+excelFileName,testMethodName,modelClass);
    }

    private static Object[][] readExcelData(String filePath, String sheetName, Class<?> modelClass) {
        List<Object> dataList = new ArrayList<>();
        try(FileInputStream file = new FileInputStream(new File(filePath))) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
            XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
            if(sheet == null) {
                throw new RuntimeException("Sheet "+sheetName+" is not found in "+filePath+".");
            }
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            List<String> headers = new ArrayList<>();

            for(Cell cell : headerRow) {
                headers.add(cell.getStringCellValue().trim());
            }

            while(rowIterator.hasNext()) {
               Row row =  rowIterator.next();
                Object modelInstance = modelClass.getDeclaredConstructor().newInstance();

                for(int i=0; i<headers.size(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = (cell.getCellType() == CellType.STRING) ?
                            cell.getStringCellValue() : String.valueOf((int)cell.getNumericCellValue());

                    Field field = modelClass.getDeclaredField(headers.get(i));
                    field.setAccessible(true);
                    field.set(modelInstance,cellValue);
                }
                dataList.add(modelInstance);
            }
        }catch (Exception e) {
            throw new RuntimeException("Error in reading the excel file "+e);
        }

        Object[][] dataArray = new Object[dataList.size()][1];
        for(int i=0; i<dataList.size(); i++) {
            dataArray[i][0] = dataList.get(i);
        }
        return dataArray;
    }

    private static Class<?> getModelClass(String testClassName) {
        String modelClassName = testClassName+"Data";
        try {
            return Class.forName("com.devstack.automation.model."+modelClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Model class not found for "+modelClassName,e);
        }

    }
}
