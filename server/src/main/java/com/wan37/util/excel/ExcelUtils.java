package com.wan37.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 配置导表工具类
 *
 * @author linda
 */
public class ExcelUtils {

    private static final String FILE_NAME_SUFFIX = ".xlsx";
    private static final String FILE_NAME_SUFFIX2 = "xls";

    /**
     * Excel表头对应Entity属性 解析封装javaBean
     *
     * @param classType  类
     * @param in         excel流
     * @param fileName   文件名
     * @param excelHeads excel表头与entity属性对应关系
     */
    public static <T> List<T> readExcelToEntity(Class<T> classType, InputStream in, String fileName, List<ExcelHead> excelHeads) throws Exception {
        checkFile(fileName);
        Workbook workbook = getWorkBoot(in, fileName);
        return readExcel(classType, workbook, excelHeads);
    }

    /**
     * 解析Excel转换为Entity
     *
     * @param classType 类
     * @param in        excel流
     * @param fileName  文件名
     */
    public static <T> List<T> readExcelToEntity(Class<T> classType, InputStream in, String fileName) throws Exception {
        return readExcelToEntity(classType, in, fileName, null);
    }

    /**
     * 校验是否是Excel文件
     *
     * @param fileName 文件名
     * @throws Exception 异常
     */
    public static void checkFile(String fileName) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            throw new Exception("文件名为空！");
        }

        if (!(fileName.endsWith(FILE_NAME_SUFFIX) || fileName.endsWith(FILE_NAME_SUFFIX2))) {
            throw new Exception("不是Excel文件！");
        }
    }

    /**
     * 兼容新老版Excel
     *
     * @param in       输入流
     * @param fileName 文件名
     * @return Workbook
     * @throws IOException IO异常
     */
    private static Workbook getWorkBoot(InputStream in, String fileName) throws IOException {
        if (fileName.endsWith(FILE_NAME_SUFFIX)) {
            return new XSSFWorkbook(in);
        } else {
            return new HSSFWorkbook(in);
        }
    }

    /**
     * 解析Excel
     *
     * @param classType  类
     * @param workbook   工作簿对象
     * @param excelHeads excel与entity对应关系实体
     */
    private static <T> List<T> readExcel(Class<T> classType, Workbook workbook, List<ExcelHead> excelHeads) throws Exception {
        List<T> beans = new ArrayList<>();
        int sheetNum = workbook.getNumberOfSheets();
        for (int sheetIndex = 0; sheetIndex < sheetNum; sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            String sheetName = sheet.getSheetName();
            int firstRowNum = sheet.getFirstRowNum() + 1;
            int lastRowNum = sheet.getLastRowNum();
            Row head = sheet.getRow(firstRowNum);
            if (head == null) {
                continue;
            }
            short firstCellNum = head.getFirstCellNum();
            short lastCellNum = head.getLastCellNum();
            Field[] fields = classType.getDeclaredFields();
            for (int rowIndex = firstRowNum + 1; rowIndex <= lastRowNum; rowIndex++) {
                Row dataRow = sheet.getRow(rowIndex);
                if (dataRow == null) {
                    continue;
                }

                T instance = classType.newInstance();
                //非头部映射方式，默认不校验是否为空，提高效率
                if (CollectionUtils.isEmpty(excelHeads)) {
                    firstCellNum = dataRow.getFirstCellNum();
                    lastCellNum = dataRow.getLastCellNum();
                }
                for (int cellIndex = firstCellNum; cellIndex < lastCellNum; cellIndex++) {
                    Cell headCell = head.getCell(cellIndex);
                    if (headCell == null) {
                        continue;
                    }
                    Cell cell = dataRow.getCell(cellIndex);
                    headCell.setCellType(Cell.CELL_TYPE_STRING);
                    String headName = headCell.getStringCellValue().trim();
                    if (StringUtils.isEmpty(headName)) {
                        continue;
                    }
                    ExcelHead eHead = null;
                    if (!CollectionUtils.isEmpty(excelHeads)) {
                        for (ExcelHead excelHead : excelHeads) {
                            if (headName.equals(excelHead.getExcelName())) {
                                eHead = excelHead;
                                headName = eHead.getEntityName();
                                break;
                            }
                        }
                    }
                    for (Field field : fields) {
                        if (headName.equalsIgnoreCase(field.getName())) {
                            String methodName = MethodUtils.setMethodName(field.getName());
                            Method method = classType.getMethod(methodName, field.getType());
                            if (isDateField(field)) {
                                Date date = null;
                                if (cell != null) {
                                    date = cell.getDateCellValue();
                                }
                                if (date == null) {
                                    validateValueRequired(eHead, sheetName, rowIndex);
                                    break;
                                }
                                method.invoke(instance, cell.getDateCellValue());
                            } else {
                                String value = null;
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    value = cell.getStringCellValue();
                                }
                                if (StringUtils.isEmpty(value)) {
                                    validateValueRequired(eHead, sheetName, rowIndex);
                                    break;
                                }
                                method.invoke(instance, convertType(field.getType(), value.trim()));
                            }
                            break;
                        }
                    }
                }
                beans.add(instance);
            }
        }
        return beans;
    }

    /**
     * 是否日期字段
     *
     * @param field 字段
     * @return boolean
     */
    private static boolean isDateField(Field field) {
        return (Date.class == field.getType());
    }

    /**
     * 空值校验
     *
     * @param excelHead 表头
     */
    private static void validateValueRequired(ExcelHead excelHead, String sheetName, int rowIndex) throws Exception {
        if (excelHead != null && excelHead.isRequired()) {
            throw new Exception("《" + sheetName + "》第" + (rowIndex + 1) + "行:\"" + excelHead.getExcelName() + "\"不能为空！");
        }
    }

    /**
     * 类型转换
     *
     * @param classType 类
     * @param value     值
     * @return Object
     */
    private static Object convertType(Class classType, String value) {
        if (Integer.class == classType || int.class == classType) {
            return Integer.valueOf(value);
        }
        if (Short.class == classType || short.class == classType) {
            return Short.valueOf(value);
        }
        if (Byte.class == classType || byte.class == classType) {
            return Byte.valueOf(value);
        }
        if (Character.class == classType || char.class == classType) {
            return value.charAt(0);
        }
        if (Long.class == classType || long.class == classType) {
            return Long.valueOf(value);
        }
        if (Float.class == classType || float.class == classType) {
            return Float.valueOf(value);
        }
        if (Double.class == classType || double.class == classType) {
            return Double.valueOf(value);
        }
        if (Boolean.class == classType || boolean.class == classType) {
            return Boolean.valueOf(value.toLowerCase());
        }
        if (BigDecimal.class == classType) {
            return new BigDecimal(value);
        }

        return value;
    }

    /**
     * 获取properties的set和get方法
     */
    static class MethodUtils {
        private static final String SET_PREFIX = "set";

        private static String capitalize(String name) {
            if (name == null || name.length() == 0) {
                return name;
            }
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        public static String setMethodName(String propertyName) {
            return SET_PREFIX + capitalize(propertyName);
        }
    }
}