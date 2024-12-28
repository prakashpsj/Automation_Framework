package com.policybazaar.components.utilities;

import com.policybazaar.components.reporting.Extent_Reporting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.*;

public class Excel_Handling {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFCell cell;
    private static XSSFRow row;
    private static FileInputStream fis = null;
    public static FileOutputStream fileOut = null;
    public static HashMap<String, HashMap> TestData;
    public static HashMap<String, HashMap> deviceDetails;
    public static String fileFullPath;
    public static String srcSheetName;
    public static String resultPath = "";
    public static String resultSheetName = "";

    public Excel_Handling() {

    }

    public void ExcelReader(String fileName, String sheetname, String ResultPath, String ResultName) {
        try {
            fis = new FileInputStream(new File(fileName));
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetname);
            srcSheetName = sheetname;
            fileFullPath = fileName;
            resultPath = ResultPath;
            resultSheetName = ResultName;
        } catch (FileNotFoundException var6) {
            System.out.println(fileName + " is not Found.please check the file name.");
            System.exit(0);
        } catch (IOException var7) {
            System.out.println(fis + " is not Found.please check the path.");
        } catch (Exception var8) {
            System.out.println("There is error reading/loading xls file due to" + var8);
        }
    }

    public static int searchField(String sheetname, int colNum, String value) throws Exception {
        try {
            int rowCount = sheet.getLastRowNum();
            System.out.println("rowCount" + rowCount);

            for (int i = 0; i <= rowCount; ++i) {
                if (getCellData(i, colNum).equalsIgnoreCase(value)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception var5) {
            throw var5;
        }
    }

    public String[] getRowData(int rowNum) throws Exception {
        String[] temp = new String[sheet.getRow(rowNum).getLastCellNum()];

        for (int i = 0; i < temp.length; ++i) {
            temp[i] = getCellData(rowNum, i);
        }
        return temp;
    }

    public static String getCellData(int rowNum, int colNum) throws Exception {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            String cellData = cell.getStringCellValue();
            return cellData;
        } catch (Exception var3) {
            return "";
        }
    }

    public static String getCellIntData(int rowNum, int colNum) throws Exception {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            String CellData = String.valueOf(cell.getNumericCellValue());
            CellData = CellData.substring(0, CellData.indexOf("."));
            return CellData;
        } catch (Exception var3) {
            return "";
        }
    }

    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            if (rowNum <= 0) {
                return "";
            } else {
                int index = workbook.getSheetIndex(sheetName);
                int col_Num = -1;
                if (index == -1) {
                    return "";
                } else {
                    sheet = workbook.getSheetAt(index);
                    row = sheet.getRow(0);

                    for (int i = 0; i < row.getLastCellNum(); ++i) {
                        if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                            col_Num = i;
                        }
                    }
                    if (col_Num == -1) {
                        return "";
                    } else {
                        sheet = workbook.getSheetAt(index);
                        row = sheet.getRow(rowNum - 1);
                        if (row == null) {
                            return "";
                        } else {
                            cell = row.getCell(col_Num);
                            if (cell == null){
                                return " ";
                        } else if (cell.getCellType() == 1) {
                            return cell.getStringCellValue();
                        } else if (cell.getCellType() != 0 && cell.getCellType() != 2) {
                            if (cell.getCellType() == 3) {
                                return "";
                            } else {
                                return String.valueOf(cell.getBooleanCellValue());
                            }
                        } else {
                            String cellText = String.valueOf(cell.getNumericCellValue());
                            return cellText;
                        }
                    }
                    }
                }
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            return "row" + rowNum + " or column " + colName + "does not exist in xls";
        }
    }

    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            if (rowNum <= 0) {
                return "";
            } else {
                int index = workbook.getSheetIndex(sheetName);
                if (index == -1) {
                    return "";
                } else {
                    sheet = workbook.getSheetAt(index);
                    row = sheet.getRow(rowNum - 1);
                    if (row == null) {
                        return "";
                    } else {
                        cell = row.getCell(colNum);
                        if (cell == null) {
                            return "";
                        } else if (cell.getCellType() == 1) {
                            return cell.getStringCellValue();
                        } else if (cell.getCellType() != 0 && cell.getCellType() != 2) {
                            return cell.getCellType() == 3 ? "" : String.valueOf(cell.getBooleanCellValue());
                        } else {
                            String cellText = String.valueOf(cell.getNumericCellValue());
                            return cellText;
                        }
                    }
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return "row" + rowNum + "or column" + colNum + "does not exist in xls";
        }
    }

    public List<HashMap<String, String>> getExcelData() {
        int lastRow = sheet.getLastRowNum();
        System.out.println(lastRow);
        List<HashMap<String, String>> result = new ArrayList(lastRow);

        for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
            HashMap<String, String> testdata = new HashMap();

            for (int j = 0; j < sheet.getRow(i).getLastCellNum(); ++j) {
                try {
                    System.out.println("i:" + i + "" + "j:" + j);
                    testdata.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(i).getCell(j).getStringCellValue());
                } catch (Throwable var7) {

                }
            }
            result.add(testdata);
        }
        return result;
    }


    public HashMap<String, String> getExcelRowData(int rowNum) {
        HashMap<String, String> map = new HashMap();

        for (int j = 0; j < sheet.getRow(rowNum).getLastCellNum(); ++j) {
            map.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(rowNum).getCell(j).getStringCellValue());

        }
        return map;
    }

    public static String cellToString(HSSFCell cell) {
        int type = cell.getCellType();
        Object result;
        switch (type) {
            case 0:
                result = cell.getNumericCellValue();
                break;
            case 1:
                result = cell.getStringCellValue();
                break;
            case 2:
                throw new RuntimeException("We can't evaluate formulas in java");
            case 3:
                result = "-";
                break;
            case 4:
                result = cell.getBooleanCellValue();
                break;
            case 5:
                throw new RuntimeException("This cell has an error");
            default:
                throw new RuntimeException("We don't support this cell type:" + type);
        }
        return result.toString();
    }

    public int getRowCount(String sheetName) {
        return workbook.getSheet(sheetName).getLastRowNum() + 1;
    }

    public static int getFirstRowNum() {
        return sheet.getFirstRowNum();
    }

    public static int getLastRowNum() {
        return sheet.getLastRowNum();
    }

    public static boolean setCellData(String colName, int rowNum, String data) {
        try {
            if (rowNum <= 0) {
                return false;
            } else {
                int index = workbook.getSheetIndex(srcSheetName);
                int colNum = -1;
                if (index == -1) {
                    return false;
                } else {
                    sheet = workbook.getSheetAt(index);
                    row = sheet.getRow(0);

                    for (int i = 0; i < row.getLastCellNum(); ++i) {
                        if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
                            colNum = i;
                        }
                    }
                    if (colNum == -1) {
                        return false;
                    } else {
                        sheet.autoSizeColumn(colNum);
                        row = sheet.getRow(rowNum - 1);
                        if (row == null) {
                            row = sheet.createRow(rowNum - 1);
                        }
                        cell = row.getCell(colNum);
                        if (cell == null) {
                            cell = row.createCell(colNum);
                        }
                        cell.setCellValue(data);
                        fileOut = new FileOutputStream(fileFullPath);
                        workbook.write(fileOut);
                        fileOut.close();
                        return true;
                    }
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }


    public boolean addSheet(String filePath, String sheetName) {
        try {
            workbook.createSheet(sheetName);
            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public boolean removeSheet(String filePath, String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            return false;
        } else {
            try {
                workbook.removeSheetAt(index);
                fileOut = new FileOutputStream(filePath);
                workbook.write(fileOut);
                fileOut.close();
                return true;
            } catch (Exception var5) {
                var5.printStackTrace();
                return false;
            }
        }
    }


    public boolean addColumn(String filePath, String sheetName, String colName) {
        try {
            fis = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return false;
            } else {
                XSSFCellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor((short) 55);
                style.setFillPattern((short) 1);
                XSSFSheet sheet = workbook.getSheetAt(index);
                XSSFRow row = sheet.getRow(0);
                XSSFCell cell = null;
                if (row == null) {
                    row = sheet.createRow(0);
                }
                if (row.getLastCellNum() == -1) {
                    cell = row.createCell(0);
                } else {
                    cell = row.createCell(row.getLastCellNum());
                }
                cell.setCellValue(colName);
                cell.setCellStyle(style);
                fileOut = new FileOutputStream(filePath);
                workbook.write(fileOut);
                fileOut.close();
                return true;
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            return false;
        }
    }


    public boolean removeColumn(String filePath, String sheetName, int colNum) {
        try {
            if (!this.isSheetExist(sheetName)) {
                return false;
            } else {
                sheet = workbook.getSheet(sheetName);
                XSSFCellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor((short) 55);
                style.setFillPattern((short) 0);


                for (int i = 0; i < this.getRowCount(sheetName); ++i) {
                    row = sheet.getRow(i);
                    if (row != null) {
                        cell = row.getCell(colNum);
                        if (cell != null) {
                            cell.setCellStyle(style);
                            row.removeCell(cell);
                        }
                    }
                }

                fileOut = new FileOutputStream(filePath);
                workbook.write(fileOut);
                fileOut.close();
                return true;
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }


    public boolean isSheetExist(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workbook.getSheetIndex(sheetName.toUpperCase());
            return index != -1;
        } else {
            return true;
        }
    }

    public int getColumnCount(String sheetName) {
        if (!this.isSheetExist(sheetName)) {
            return -1;
        } else {
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(0);
            return row == null ? -1 : row.getLastCellNum();
        }
    }

    public static String HSSFCellToString(HSSFCell cell) {
        String cellValue = null;
        if (cell != null) {
            cellValue = cell.toString();
            cellValue = cellValue.trim();
        } else {
            cellValue = "";
        }
        return cellValue;
    }


    public HashMap<String, HashMap> getExcelDataAll() {
        int lastRow = sheet.getLastRowNum();
        HashMap<String, HashMap> result = new HashMap(lastRow);

        for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
            HashMap<String, String> testData = new HashMap();

            for (int j = 0; j <= sheet.getRow(i).getLastCellNum(); ++j) {
                try {
                    testData.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(i).getCell(j).getStringCellValue());
                } catch (Throwable var7) {
                }
            }
            result.put(sheet.getRow(i).getCell(0).getStringCellValue(), testData);
        }
        deviceDetails = result;
        return result;
    }

    public HashMap<String, HashMap> getExcelDataAll(String sheetName, String Flag, String FlagValue, String Key) throws Exception {
        sheet = workbook.getSheet(sheetName);
        int flagIndex = this.findColumnIndex(Flag);
        int keyIndex = this.findColumnIndex(Key);
        int lastRow = sheet.getLastRowNum();
        LinkedHashMap<String, HashMap> result = new LinkedHashMap(lastRow);

        for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
            if (getCellData(i, flagIndex).equalsIgnoreCase(FlagValue)) {
                LinkedHashMap<String, String> testdata = new LinkedHashMap();

                for (int j = 0; j < sheet.getRow(i).getLastCellNum(); ++j) {
                    try {
                        sheet.getRow(0).getCell(j).setCellType(1);
                        sheet.getRow(i).getCell(j).setCellType(1);
                        testdata.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(i).getCell(j).getStringCellValue());
                    } catch (Throwable var13) {
                    }
                }
                try {
                    result.put(sheet.getRow(i).getCell(keyIndex).getStringCellValue(), testdata);
                } catch (Throwable var14) {
                }
            }
        }
        TestData = result;
        return result;
    }

    public int findColumnIndex(String ColumnHeader) {
        int CurrentColumn = -1;
        XSSFRow fr = sheet.getRow(0);
        int ColumnCount = fr.getLastCellNum() - fr.getFirstCellNum();


        for (int i = 0; i <= ColumnCount -1 ; ++i){
            if (fr.getCell(i).getStringCellValue().contains(ColumnHeader)) {
                CurrentColumn = i;
                break;
            }
        }
        return CurrentColumn;
    }


    public static String Get_Data(String TestCase, String ColumnHeader) {
        LinkedHashMap TC = (LinkedHashMap) TestData.get(TestCase);

        try {
            return (String) TC.get(ColumnHeader);
        } catch (Throwable var4) {
            return "null";
        }
    }

    public static String Put_Data(String TestCase, String ColumnHeader, String Value) {
        try {
            String data = "";
            LinkedHashMap<String, String> TC = (LinkedHashMap) TestData.get(TestCase);
            if (TC == null) {
                return "Fail";
            } else {
                if (TC.containsKey(ColumnHeader)) {
                    data = (String) TC.get(ColumnHeader);
                    data = data + ";" + Value;
                } else {
                    data = Value;
                }
                TC.put(ColumnHeader, data);
                return "success";
            }
        } catch (Throwable var5) {
            var5.printStackTrace();
            return "fail";
        }
    }


    public static String Put_Data_Replace(String TestCase, String ColumnHeader, String Value) {
        LinkedHashMap<String, String> TC = (LinkedHashMap) TestData.get(TestCase);
        String data = (String) TC.get(ColumnHeader);
        TC.put(ColumnHeader, Value);
        return "success";
    }

    public static boolean setCellDataWithCondition(String colName, String rowName, String rowValue, String data) {
        try {
            int colNum = -1;
            int rowNameNum = -1;
            row = sheet.getRow(0);

            int rowNum;
            String s;
            for (rowNum = 0; rowNum<row.getLastCellNum(); ++rowNum) {
                s ="";

                try {
                    s = row.getCell(rowNum).getStringCellValue().trim();
                } catch (Throwable var10){
                }

                if (s.equals(colName)) {
                    colNum = rowNum;
                    break;
                }
            }
            if (colNum == -1) {
                colNum = row.getLastCellNum();
                row.createCell(colNum);
                cell = row.getCell(colNum);
                cell.setCellValue(colName);
            }

            for (rowNum = 0; rowNum < row.getLastCellNum() -1; ++rowNum) {
                System.out.println(rowNum + rowName + row.getCell(rowNum).getNumericCellValue());
                s = "";

                try {
                    s = row.getCell(rowNum).getStringCellValue().trim();
                } catch (Throwable var9) {
                }
                if (s.equals(rowName.trim())) {
                    rowNameNum = rowNum;
                    break;
                }
            }
            if (rowNameNum == -1) {
                return false;
            } else {
                rowNum = searchField(srcSheetName, rowNameNum, rowValue);
                if (rowNum <= 0) {
                    return false;
                } else {
                    sheet.autoSizeColumn(colNum);
                    row = sheet.getRow(rowNum);
                    if (row == null) {
                        row =sheet.createRow(rowNum);
                    }
                    cell = row.getCell(colNum);
                    if (cell == null) {
                        cell = row.createCell(colNum);
                    }
                    cell.setCellValue(data);
                    return true;
                }
            }
        } catch (Exception var11) {
            var11.printStackTrace();
            return false;
        }

    }


    public static boolean Write_Data(String rowName, String ColumnHeader) {
        String s = "";
        try {
            Map<String, HashMap> map = TestData;
            new HashMap();
            Iterator var5 = map.keySet().iterator();

            while (var5.hasNext()) {
                String key = (String) var5.next();
                HashMap<String, String>  m = (HashMap) map.get(key);
                s = Get_Data(key, ColumnHeader);
                if (s != null); {
                    setCellDataWithCondition(ColumnHeader, rowName, key, s);
                }
            }
        }  catch (Exception var7) {
            var7.printStackTrace();
        }
        return true;
    }


    public static void close() {
        try {
            System.out.println(fileFullPath + srcSheetName);
            fileOut = new FileOutputStream(fileFullPath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Throwable var1) {
            var1.printStackTrace();
        }
    }
    public static boolean Write_Data_All(String rowName, String ColumnHeaders) {
        String[] ch = ColumnHeaders.split(";");
        String[] var3 = ch;
        int var4 = ch.length;

        for (int var5 = 0; var5 < var4;  ++var5){
            String s = var3[var5];
            Write_Data(rowName, s);
        }
        close();
        return true;
    }


    /**
     * @author Ankit Kumar
     * @Following method is to write in existing Excel file
     */

    public void writeInExistingExcel(String filePath, String fileName, String sheetName, String[] dataToWrite) throws IOException {
        //Create an object of file class to open xlsx file
        File file = new File(filePath);
        // Create an object of fileInputStream class to read Excel file
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook1 = null;
        // Find the file extension by splitting file name in substring and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        // Check condition if the file is xlsx file
        if (fileExtensionName.equals(".xlsx")) {
            // If it is xlsx file then create object of XSSFWorkbook class
            workbook1 = new XSSFWorkbook(inputStream);
        }
        // Check condition if the file is xls file
        else if (fileExtensionName.equals(".xls")) {
            // If it is xls file then create object of XSSFWorkbook class
            workbook1 = new HSSFWorkbook(inputStream);
        }
        // Read Excel sheet by sheet name
        Sheet sheet = workbook1.getSheet(sheetName);
        // Get the current count of rows in Excel file
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        // Get the first row from the sheet
        Row row = sheet.getRow(0);
        // Create a new row and opened it at last of sheet
        Row newRow = sheet.createRow(rowCount + 1);
        // Create a loop over the cell of newly created Row
        for (int j = 0; j < row.getLastCellNum(); ++j){
            // Fill data in row
            Cell cell = newRow.createCell(j);
            cell.setCellValue(dataToWrite[j]);
        }
        // Close input stream
        inputStream.close();
        // Create an object of FileOutputStream class to create write data in Excel file
        FileOutputStream outputStream= new FileOutputStream(file);
        // Write data in the Excel file
        workbook1.write(outputStream);
        outputStream.close();
    }

    /**
     * @author Ankit Kumar
     * @Following method is to write a csv file
     */

    public void writeCSV(WebDriver driver, String filePath, String companyName, String companyID, String startDate, String endDate, String employeeNumber) throws IOException {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(companyName + "," + companyID + "," + startDate + "," + endDate + "," + employeeNumber);
            pw.flush();
            pw.close();
        } catch (Exception E) {
            Extent_Reporting.Log_Fail("CSV Write", "Failed to write CSV ", driver);
        }
    }

}
