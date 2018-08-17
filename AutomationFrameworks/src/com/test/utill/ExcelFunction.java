package com.test.utill;
import jxl.WorkbookSettings;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author d
 * @date 2018/4/13 0013 15:17
 * @method: writeExcelCells,         将已经准备好的数据，写入Excel中
 * <p>
 * getWorkBook,             根据扩展名获取workbook
 * <p>
 * creatExcelAndDefineHeader,          创建Excel表，并自定义表头
 * <p>
 * getLastCellNum,          获取已知excel表中，最后一行的行数
 * <p>
 * getExcelCells，          读取Excel表中的数据
 * <p>
 * getStringCellValue,      读取Excel表中的数据转换成String类型
 */

@SuppressWarnings("ALL")
public class ExcelFunction {
    //    private static String filePath = "e://abcd.xlsx";
    private String HSSF_XLS = "xls";
    private String XSSF_XLSX = "xlsx";
    private File file;
    private Workbook wk = null;
    private Sheet sheet = null;
    private Row row = null;
    private Cell cell = null;
    private static int a;
    private Map<Integer, Map<Integer, String>> map = new HashMap<Integer, Map<Integer, String>>();
//    private String[] title={"张三","李四","王二麻子"};

    public ExcelFunction(String filePath) {
        WorkbookSettings workbookSettings = new WorkbookSettings();
        workbookSettings.setEncoding("utf-8");
        file = new File(filePath);
        wk = this.getWorkBook(file);

    }


    private void writeExcelCells(String filePath) {
        File file = new File(filePath);
        try {
            FileOutputStream outputStream = FileUtils.openOutputStream(file);
            wk.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Workbook getWorkBook(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            if (file.getName().endsWith(HSSF_XLS)) {
                wk = new HSSFWorkbook(in);
            } else if (file.getName().endsWith(XSSF_XLSX)) {
                wk = new XSSFWorkbook(in);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wk;
    }

    public void creatExcelAndDefineHeader(String filePath, String[] title1) {
        if (filePath.endsWith(HSSF_XLS)) {
            wk = new HSSFWorkbook();
        } else if (filePath.endsWith(XSSF_XLSX)) {
            wk = new XSSFWorkbook();
        }
        sheet = wk.createSheet();

        row = sheet.createRow(0);

        String[] title = title1;

        if (sheet.getRow(0) != null) {

            for (int i = 0; i < title.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
            }
        }
//        int lastRowNum = sheet.getLastRowNum() + 1;

//        System.out.println(lastRowNum);

        //写入固定数据
        /*for (int i = lastRowNum; i <= 10; i++) {
            Row newRow = sheet.createRow(i);
            cell = newRow.createCell(0);
            cell.setCellValue("i");
            Cell cell1 = newRow.createCell(1);
            cell1.setCellValue("user" + i);
            Cell cell2 = newRow.createCell(2);
            cell2.setCellValue(24);
        }*/

/*        int lastRowNum1 = sheet.getLastRowNum() + 1;
        System.out.println(lastRowNum1);*/


        writeExcelCells(filePath);

/*        OpenFile openFile = new OpenFile();
        openFile.open(filePath);*/
    }

    /*public static void writeCells(String filePath,int x,String []a){
        if (filePath.endsWith(HSSF_XLS)) {
            wk = new HSSFWorkbook();
        } else if (filePath.endsWith(XSSF_XLSX)) {
            wk = new XSSFWorkbook();
        }
        sheet = wk.getSheetAt(0);

//      获取最后一行行数
        int lastRowNum=sheet.getLastRowNum();
        row = sheet.getRow(0);
//      获取列总数
        int columnNum = row.getPhysicalNumberOfCells();

         String [][] values=a[columnNum][];


        for (int i = lastRowNum; i <= x; i++) {
            for (int k=0;k<values.length;k++){
                String aa=Arrays.toString(values[k]);
            Row newRow = sheet.createRow(i);
            cell = newRow.createCell(title);
            cell.setCellValue(aa);
            Cell cell1 = newRow.createCell(1);
            cell1.setCellValue("user" + i);
            Cell cell2 = newRow.createCell(2);
            cell2.setCellValue(24);
            }
        }
    }*/

    public String getExcelCellsByColumnName(int row, int column) {
        String s = null;
        try {
            Map<Integer, Map<Integer, String>> excelCells = this.getExcelCells();
            s = excelCells.get(row - 1).get(column - 1);
        } catch (Exception e) {
            LogFunction.logInfo("第一行为标题行，请从第二行开始录入");
        }
        return s;
    }

    public String getExcelCellsByColumnName(int row, String columnName) {
        Map<Integer, Map<Integer, String>> excelCells = this.getExcelCells();
        String s = null;
        for (int i = 0; i < excelCells.get(0).size(); i++) {
            if (columnName.equals(excelCells.get(0).get(i))) {
                int b = i;
                s = excelCells.get(row - 1).get(b);
                break;
            }
        }
        return s;
    }

    public Map<Integer, Map<Integer, String>> getExcelHeaderCells() {
        Sheet sheetAt = wk.getSheetAt(0);
        Row row = sheetAt.getRow(0);
        int columnNum = row.getPhysicalNumberOfCells();
//            System.out.println("columnNum" + columnNum);
        Map<Integer, String> mapchild = new HashMap<Integer, String>();
        for (int j = 0; j < columnNum; j++) {
            cell = row.getCell(j);
            String cellValue = this.getStringCellValue(cell);
            mapchild.put(j, cellValue);
//                System.out.print("------" + cellValue + "------");
        }
        map.put(0, mapchild);
       /* for (int i = 0; i < columnNum; i++) {
            System.out.println(map.get(0).get(i));
        }*/
        return map;
    }


    public Map<Integer, Map<Integer, String>> getExcelCells() {
        Sheet sheetAt = wk.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        int firstRowNum = sheetAt.getFirstRowNum();
//        System.out.println("firstRowNum:" + firstRowNum + "lastRowNum" + lastRowNum);
        for (int i = 1; i < lastRowNum; i++) {
            Row row = sheetAt.getRow(i);
            int columnNum = row.getPhysicalNumberOfCells();
//            System.out.println("columnNum" + columnNum);
            Map<Integer, String> mapchild = new HashMap<Integer, String>();
            for (int j = 0; j < columnNum; j++) {
                cell = row.getCell(j);
                String cellValue = this.getStringCellValue(cell);
                mapchild.put(j, cellValue);
//                System.out.print("------" + cellValue + "------");
            }
            map.put(i, mapchild);
        }
        return map;
    }

    public void getExcelCellsByColumnName(String ColumnName) {
        Sheet sheetAt = wk.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        int firstRowNum = sheetAt.getFirstRowNum();
        Row row1 = sheetAt.getRow(0);
        Iterator<Cell> cellIterator = row1.cellIterator();
        a = 0;
        while (cellIterator.hasNext()) {
            Cell next = cellIterator.next();
            if (next.toString().equals(ColumnName)) {
                break;
            }
            a = a + 1;
        }
        Map<Integer, Map<Integer, String>> excelCells = this.getExcelCells();

        Collection<String> values = map.get(a).values();
        for (int i = 0; i < values.size(); i++) {

        }


    }

/*    public  void getExcelCells(String filepath) {
//       "e://abc.xlsx"
         file = new File(filepath);
        wk = this.getWorkBook(file);

        Sheet sheetAt = wk.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        int firstRowNum = sheetAt.getFirstRowNum();
//        System.out.println("firstRowNum:" + firstRowNum + "lastRowNum" + lastRowNum);
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheetAt.getRow(i);
            int columnNum = row.getPhysicalNumberOfCells();
//            System.out.println("columnNum" + columnNum);
            for (int j = 0; j < columnNum; j++) {
                Cell cell = row.getCell(j);
                String cellValue = this.getStringCellValue(cell);
                System.out.print("------" + cellValue + "------");
            }
//            System.out.println();
        }
    }*/

    public int getLastCellNum(String filepath) {
//       "e://abc.xlsx"
        File file = new File(filepath);
        wk = this.getWorkBook(file);
        Sheet sheetAt = wk.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
//        System.out.println(lastRowNum);
        return lastRowNum;
    }

    public String getStringCellValue(Cell cell) {
        String value = "";
        if (value != null) {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                case NUMERIC:
//                    short dataFormat = cell.getCellStyle().getDataFormat();
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//非线程安全
                        return sdf.format(cell.getDateCellValue());
                    } else {
                        return String.valueOf((int) cell.getNumericCellValue());
                    }
                case BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    value = "";
                    break;
                default:
                    value = "";
                    break;
            }
            if (value.equals("") || value == null) {
                return "";
            }
            if (cell == null) {
                return "";
            }
        }
        return value;
    }

    public Object[][] getObjectCells() {
        Map<Integer, Map<Integer, String>> e = this.getExcelHeaderCells();
        Sheet sheetAt = wk.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        int firstRowNum = sheetAt.getFirstRowNum();
        Object[][] objects = new Object[lastRowNum][];
        for (int i = 0; i < lastRowNum; i++) {
            Row row = sheetAt.getRow(i + 1);
            int columnNum = row.getPhysicalNumberOfCells();
            String excelCellsByColumnName = this.getExcelCellsByColumnName(2, 1);
//            System.out.println(excelCellsByColumnName);
            Map<String, String> mapchild = new HashMap<String, String>();
            Object[] temp = null;
            for (int j = 0; j < columnNum; j++) {
                cell = row.getCell(j);
                String cellValue = this.getStringCellValue(cell);
                mapchild.put(e.get(0).get(j), cellValue);
            }
            temp = new Object[]{mapchild};
            objects[i] = temp;
        }
        return objects;
    }

    public static void main(String[] args) {
        /*ExcelFunction excelFunction = new ExcelFunction("e:/abcdef.xlsx");
        String a = excelFunction.getExcelCellsByColumnName(3, "哈哈");
        String b = excelFunction.getExcelCellsByColumnName(3, 1);
        System.out.println("a:" + a + '\n' + "b:" + b);
        System.out.println(new Date().getTime());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd hh_mm_ss");
        String screenName=String.valueOf(simpleDateFormat.format(new Date().getTime()));
        System.out.println(screenName);*//*
        ExcelFunction e = new ExcelFunction("e:/abcdef.xlsx");
        e.getExcelCellsByColumnName("嘿嘿");*/
        ExcelFunction excelFunction = new ExcelFunction("e:/abcdef.xlsx");
        Map<Integer, Map<Integer, String>> e = excelFunction.getExcelHeaderCells();

    }
}