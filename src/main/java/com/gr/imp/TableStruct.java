package com.gr.imp;

import java.util.List;

/**
 * @program: wwis-kunming
 * @description: 数据表的表结构
 * @author: Shizh
 * @create: 2018-07-23 14:57
 **/
public class TableStruct {
    private String tableName;//表名
    private List Columns;//所有的列
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public List getColumns() {
        return Columns;
    }
    public void setColumns(List columns) {
        Columns = columns;
    }
    public TableStruct(String tableName, List columns) {
        super();
        this.tableName = tableName;
        Columns = columns;
    }
    public TableStruct() {
        super();
    }
    @Override
    public String toString() {
        return "TableStruct [tableName=" + tableName + ", Columns=" + Columns
                + "]";
    }
}
