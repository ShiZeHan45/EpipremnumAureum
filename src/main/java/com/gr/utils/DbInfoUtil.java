package com.gr.utils;


import com.gr.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * @program: wwis-kunming
 * @description:
 * @author: Shizh
 * @create: 2018-07-25 16:14
 **/
@Component
public class DbInfoUtil {
    @Autowired
    private DataSourceUtil dataSourceUtil;
    @Autowired
    private Config config;

    /**
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
     *
     * @return Map集合
     */
    public Map<String, List> getTablesInfo() {
        String tableHead = config.getTableHead();
        Map<String, List> tables = new HashMap();
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        try {
            conn = dataSourceUtil.getConnection();
            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", null, new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (tableName.contains(tableHead)) {
                    ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn), tableName.toUpperCase(), "%");
                    List<Column> columns = new ArrayList<>();
                    while (rs.next()) {
                        Column column = new Column();
                        String COLUMN_SIZE = rs.getString("COLUMN_SIZE");
                        System.out.println("字段名：" + rs.getString("COLUMN_NAME") + "--字段注释：" + rs.getString("REMARKS") + "--字段数据类型：" + rs.getString("TYPE_NAME") + "---字段大小:" + Integer.parseInt(COLUMN_SIZE == null ? "0" : COLUMN_SIZE));
                        String colName = rs.getString("COLUMN_NAME");
                        column.setColumnName(colName);
                        String remarks = rs.getString("REMARKS");
                        if (remarks == null || remarks.equals("")) {
                            remarks = colName;
                        }
                        column.setColumnRemark(remarks);
                        String dbType = rs.getString("TYPE_NAME");
                        String IS_NULLABLE = rs.getString("IS_NULLABLE");
                        Integer numPrecRadix = rs.getInt("num_prec_radix");
                        Integer decimalDigits = rs.getInt("decimal_digits");
                        if (IS_NULLABLE.equals("YES")) {
                            column.setNull(true);
                        } else {
                            column.setNull(false);
                        }
                        column.setColumnSize(Integer.parseInt(COLUMN_SIZE == null ? "0" : COLUMN_SIZE));
                        column.setDbTypeName(dbType);
                        column.setValueType(changeDbType(dbType));
                        column.setDecimalDigits(decimalDigits);
                        column.setNumPrecRadix(numPrecRadix);
                        columns.add(column);
                    }
                    tables.put(tableName, columns);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataSourceUtil.closeRes(null, null, conn);
        }
        return tables;
    }


    private static String changeDbType(String dbType) {
        dbType = dbType.toUpperCase();
        switch (dbType) {
            case "VARCHAR":
            case "VARCHAR2":
            case "CHAR":
                return "1";
            case "NUMBER":
            case "DECIMAL":
                return "4";
            case "INT":
            case "SMALLINT":
            case "INTEGER":
                return "2";
            case "BIGINT":
                return "6";
            case "DATETIME":
            case "TIMESTAMP":
            case "DATE":
                return "7";
            default:
                return "1";
        }
    }


    //其他数据库不需要这个方法 oracle和db2需要
    private static String getSchema(Connection conn) throws Exception {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase().toString();

    }


}
