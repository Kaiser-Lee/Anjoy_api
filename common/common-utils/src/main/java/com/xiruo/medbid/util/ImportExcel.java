package com.xiruo.medbid.util;

import com.xiruo.medbid.components.StringUtils;
import com.xiruo.medbid.components.date.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImportExcel {

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * 构造函数 读取第一个sheet
     *
     * @param fileName  文件名
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum) throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum);
    }

    /**
     * 构造函数 读取第一个sheet
     *
     * @param file      文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum) throws InvalidFormatException, IOException {
        this(file, headerNum, 0);
    }

    /**
     * 构造函数
     *
     * @param fileName   文件名
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param file       文件对象
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param multipartFile 文件对象
     * @param headerNum     标题行号，数据行号=标题行号+1
     * @param sheetIndex    工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param fileName   文件名
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("导入文档为空!");
        } else if (fileName.toLowerCase().endsWith("xls")) {
            this.wb = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith("xlsx")) {
            this.wb = new XSSFWorkbook(is);
        } else {
            throw new RuntimeException("文档格式不正确!");
        }
        if (this.wb.getNumberOfSheets() < sheetIndex) {
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        System.out.println("Initialize success.");
    }

    /**
     * 获取行对象
     *
     * @param rowNum
     * @return
     */
    public Row getRow(int rowNum) {
        return this.sheet.getRow(rowNum);
    }

    /**
     * 获取数据行号
     *
     * @return
     */
    public int getDataRowNum() {
        return headerNum + 1;
    }

    /**
     * 获取最后一个数据行号
     *
     * @return
     */
    public int getLastDataRowNum() {
        return this.sheet.getLastRowNum() + headerNum;
    }

    /**
     * 获取最后一个列号
     *
     * @return
     */
    public int getLastCellNum() {
        return this.getRow(headerNum).getLastCellNum();
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public String getCellValue(Row row, int column) {
        String val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {//数值型 0
                    val = String.valueOf((long) cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {//字符串型 1
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {//公式型 2
                    val = cell.getCellFormula();
                } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {//空值 3
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {//布尔型 4
                    val = cell.getBooleanCellValue() + "";
                } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {//错误 5
                    val = cell.getErrorCellValue() + "";
                }
            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * 获取单元格值
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public String getCellValue2(Row row, int column){
        String val = "";
        try{
            Cell cell = row.getCell(column);
            if (cell != null){
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){//数值型 0
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        val = DateUtils.formatDate(cell.getDateCellValue());
                    }else {
                        val = String.valueOf((long)cell.getNumericCellValue());
                    }
                }else if (cell.getCellType() == Cell.CELL_TYPE_STRING){//字符串型 1
                    val = cell.getStringCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){//公式型 2
                    val = cell.getCellFormula();
                }else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){//空值 3
                    val = cell.getStringCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){//布尔型 4
                    val = cell.getBooleanCellValue()+"";
                }else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){//错误 5
                    val = cell.getErrorCellValue()+"";
                }
            }
        }catch (Exception e) {
            return val;
        }
        return val;
    }
}
