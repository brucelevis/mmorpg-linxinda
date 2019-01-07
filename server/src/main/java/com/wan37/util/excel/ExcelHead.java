package com.wan37.util.excel;

public class ExcelHead {

    /**
     * Excel名
     */
    private String excelName;

    /**
     * 实体类属性名
     */
    private String entityName;

    /**
     * 值必填
     */
    private boolean required = false;

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ExcelHead(String excelName, String entityName, boolean required) {
        this.excelName = excelName;
        this.entityName = entityName;
        this.required = required;
    }

    public ExcelHead(String excelName, String entityName) {
        this.excelName = excelName;
        this.entityName = entityName;
    }

    public ExcelHead() {
        // NOOP
    }
}