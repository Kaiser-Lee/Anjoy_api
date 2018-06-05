package com.coracle.yk.xframework.util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel工具类
 * 创建Sheet, 填充数据
 * author: huangbaidong
 * created : 2016/3/18.
 */
public class ExcelBuilder {

    /**
     * 创建只包含标题列的sheet表
     *
     * @param wb
     * @param sheetName
     * @param titleNames
     * @return
     */
    public static HSSFSheet createExcelSheetWithTitle(HSSFWorkbook wb, String sheetName, List<String> titleNames) {
        if (BlankUtil.isNotEmpty(titleNames)) {
            return createExcelSheetWithTitle(wb, sheetName, titleNames.toArray(new String[titleNames.size()]));
        }
        return createExcelSheetWithTitle(wb, sheetName, new String[]{});
    }

    /**
     * 创建只包含标题列的sheet表
     *
     * @param wb
     * @param sheetName
     * @param titleNames
     * @return
     */
    public static HSSFSheet createExcelSheetWithTitle(HSSFWorkbook wb, String sheetName, String[] titleNames) {
        if (BlankUtil.isEmpty(sheetName)) {//如果没传sheetName,给定默认值
            sheetName = "Sheet";
        }
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);//设置默认列宽
        sheet.setDefaultRowHeight((short) 300);//设置默认行高
        if (BlankUtil.isNotEmpty(titleNames)) {//设置Sheet首行列标题
            Row row = sheet.createRow(0);
            Cell cell = null;
            for (int i = 0; i < titleNames.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(titleNames[i]);
            }
        }
        return sheet;
    }

    /**
     * 创建包含标题列的sheet, 并填充数据
     *
     * @param wb
     * @param sheetName
     * @param titleNames
     * @param datas
     * @return
     */
    public static HSSFSheet createExcelSheetWithTitleAndData(
            HSSFWorkbook wb, String sheetName, List<String> titleNames, List<Map<String, Object>> datas) {
        if (BlankUtil.isNotEmpty(titleNames) && BlankUtil.isNotEmpty(datas)) {
            return createExcelSheetWithTitleAndData(wb, sheetName, titleNames.toArray(new String[titleNames.size()]), datas);
        }
        return createExcelSheetWithTitle(wb, sheetName, new String[]{});
    }


    /**
     * 创建包含标题列的sheet, 并填充数据
     *
     * @param wb
     * @param sheetName
     * @param titleNames
     * @param datas
     * @return
     */
    public static HSSFSheet createExcelSheetWithTitleAndData(
            HSSFWorkbook wb, String sheetName, String[] titleNames, List<Map<String, Object>> datas) {
        HSSFSheet sheet = createExcelSheetWithTitle(wb, sheetName, titleNames);//创建包含标题列的空sheet
        fillDataToSheet(sheet, datas);//给sheet填充数据
        return sheet;
    }

    /**
     * 向sheet中填充数据行
     *
     * @param sheet
     * @param datas
     */
    public static HSSFSheet fillDataToSheet(HSSFSheet sheet, List<Map<String, Object>> datas) {
        if (sheet != null && BlankUtil.isNotEmpty(datas)) {
            Row row = null;
            Cell cell = null;
            Map<String, Object> rowDataMap = null;
            Object columnValue = null;
            //第一行是标题行, 所以从第二行开始插入数据
            for (int rowIndex = 0; rowIndex < datas.size(); rowIndex++) {
                row = sheet.createRow(rowIndex + 1);//从第二行开始
                rowDataMap = datas.get(rowIndex);
                int columnIndex = 0;
                for (String key : rowDataMap.keySet()) {
                    cell = row.createCell(columnIndex);
                    columnValue = rowDataMap.get(key);
                    columnIndex++;
                    if (columnValue == null) {
                        continue;
                    }
                    if (columnValue instanceof Integer) {
                        cell.setCellValue((Integer) columnValue);
                    } else if (columnValue instanceof Long) {
                        cell.setCellValue((Long) columnValue);
                    } else {
                        cell.setCellValue(String.valueOf(columnValue));
                    }
                }
            }
        }
        return sheet;
    }

    /***
     * 解析excel并封装成list
     *
     * @param inputStream
     * @return
     */
    public static List<Map<String, String>> readExcelandToList(InputStream inputStream) {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        BufferedInputStream bis = null;
        // 获得一个特定的工作薄
        try {
            bis = new BufferedInputStream(inputStream);
            Workbook workbook = WorkbookFactory.create(bis);
            // 循环获得每一个sheet(只处理第一个sheet)
            for (int numSheets = 0; workbook != null
                    && numSheets < workbook.getNumberOfSheets(); numSheets++) {
                if (numSheets != 0) {
                    break;
                }
                // 获得一个sheet
                Sheet sheet = workbook.getSheetAt(numSheets);
                if (null == sheet) {
                    continue;
                }
                // 循环每一行
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    Row aRow = sheet.getRow(rowNum);
                    if (null == aRow) {
                        continue;
                    }
                    Map<String, String> datum = new HashMap<String, String>();
//                    Map<String, String> datum =new LinkedHashMap<String, String>();

                    // 循环特定行的每一列
                    for (short cellNum = 0; cellNum < aRow.getLastCellNum(); cellNum++) {
                        // 得到特定单元格
                        Cell aCell = aRow.getCell(cellNum);
                        if (aCell == null) {
                            continue;
                        }
                        aCell.setCellType(Cell.CELL_TYPE_STRING);
                        String cellValue = (aCell == null ? null : aCell.getRichStringCellValue().toString());
                        datum.put("cell" + cellNum, cellValue.trim());
                    }
                    data.add(datum);
                }
            }

            bis.close();
            if (inputStream != null) {
                inputStream.close();
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                return null;
            }
        }
    }

    /***
     * 解析excel并封装成list,Map中包含列名
     *
     * @param inputStream
     * @return
     */
    public static List<Map<String, String>> readExcelToList(InputStream inputStream) {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        BufferedInputStream bis = null;
        // 获得一个特定的工作薄
        try {
            bis = new BufferedInputStream(inputStream);
            Workbook workbook = WorkbookFactory.create(bis);
            // 循环获得每一个sheet(只处理第一个sheet)
            for (int numSheets = 0; workbook != null
                    && numSheets < workbook.getNumberOfSheets(); numSheets++) {
                if (numSheets != 0) {
                    break;
                }
                // 获得一个sheet
                Sheet sheet = workbook.getSheetAt(numSheets);
                if (null == sheet) {
                    continue;
                }
                //得到表头
                Row tRow = sheet.getRow(0);
                List<String> cellName = new ArrayList<>();
                for (short cellNum = 0; cellNum < tRow.getLastCellNum(); cellNum++) {
                    Cell aCell = tRow.getCell(cellNum);
                    if (aCell == null) {
                        continue;
                    }
                    aCell.setCellType(Cell.CELL_TYPE_STRING);
                    String cellValue = (aCell == null ? null : aCell.getRichStringCellValue().toString());
                    cellName.add(cellValue.trim());
                }
                // 循环每一行
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    Row aRow = sheet.getRow(rowNum);
                    if (null == aRow) {
                        continue;
                    }
                    Map<String, String> datum = new HashMap<String, String>();
                    // 循环特定行的每一列
                    for (short cellNum = 0; cellNum < aRow.getLastCellNum(); cellNum++) {
                        // 得到特定单元格
                        Cell aCell = aRow.getCell(cellNum);
                        if (aCell == null) {
                            continue;
                        }
                        aCell.setCellType(Cell.CELL_TYPE_STRING);
                        String cellValue = (aCell == null ? null : aCell.getRichStringCellValue().toString());
                        datum.put(cellName.get(cellNum), cellValue.trim());
                    }
                    data.add(datum);
                }
            }
            bis.close();
            if (inputStream != null) {
                inputStream.close();
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                return null;
            }
        }
    }
}
