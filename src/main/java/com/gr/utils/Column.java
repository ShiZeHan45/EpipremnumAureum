package com.gr.utils;

/**
 * @program: wwis-kunming
 * @description: 表列对象
 * @author: Shizh
 * @create: 2018-07-25 16:23
 **/
public class Column {
    private String columnName;
    private String columnRemark;
    private String valueType;
    private String dbTypeName;
    private boolean isNull;
    private int columnSize;
    private Integer decimalDigits;//小数位数
    private Integer numPrecRadix;//基数

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public Integer getNumPrecRadix() {
        return numPrecRadix;
    }

    public void setNumPrecRadix(Integer numPrecRadix) {
        this.numPrecRadix = numPrecRadix;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnRemark() {
        return columnRemark;
    }

    public void setColumnRemark(String columnRemark) {
        this.columnRemark = columnRemark;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getDbTypeName() {
        return dbTypeName;
    }

    public void setDbTypeName(String dbTypeName) {
        this.dbTypeName = dbTypeName;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", columnRemark='" + columnRemark + '\'' +
                ", valueType='" + valueType + '\'' +
                ", dbTypeName='" + dbTypeName + '\'' +
                '}';
    }
}
