package com.gr.utils;


import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * @program: wwis-kunming
 * @description:
 * @author: Shizh
 * @create: 2018-07-25 16:14
 **/
public class DbInfoUtil {
    /**
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
     *
     * @param driver 数据库连接驱动
     * @param url    数据库连接url
     * @param user   数据库登陆用户名
     * @param pwd    数据库登陆密码
     * @param table  表名
     * @return Map集合
     */
    public static List getTableInfo(String driver, String url, String user, String pwd, String table) {
        List result = new ArrayList();

        Connection conn = null;
        DatabaseMetaData dbmd = null;

        try {
            conn = getConnections(driver, url, user, pwd);

            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", table, new String[]{"TABLE"});

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName);

                if (tableName.equals(table)) {
                    ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn), tableName.toUpperCase(), "%");

                    while (rs.next()) {
                        //System.out.println("字段名："+rs.getString("COLUMN_NAME")+"--字段注释："+rs.getString("REMARKS")+"--字段数据类型："+rs.getString("TYPE_NAME"));
                        Map map = new HashMap();
                        String colName = rs.getString("COLUMN_NAME");
                        map.put("code", colName);

                        String remarks = rs.getString("REMARKS");
                        if (remarks == null || remarks.equals("")) {
                            remarks = colName;
                        }

                        map.put("name", remarks);
                        String dbType = rs.getString("TYPE_NAME");
                        map.put("dbType", dbType);
                        map.put("valueType", changeDbType(dbType));
                        result.add(map);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
     *
     * @return Map集合
     */
    public static Map<String, List> getTablesInfo() {
        String tableHead = ConfigUtil.tableHead;
        String driver = DataSourceUtil.driver;
        String url = DataSourceUtil.url;
        String user = DataSourceUtil.userName;
        String pwd = DataSourceUtil.userPs;
        Map<String, List> tables = new HashMap();
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        try {
            conn = getConnections(driver, url, user, pwd);
            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", null, new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (tableName.indexOf(tableHead) != -1) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    //获取连接
    private static Connection getConnections(String driver, String url, String user, String pwd) throws Exception {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user",user);
            props.put("password",pwd);
            Class.forName(driver);
            conn = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
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

    public String get() {
        return this.getClass().getClassLoader().getResource("").toString();
    }

    public static void main(String[] args) {

        //这里是Oracle连接方法

//        String table = "ac_access_token";

        //mysql
        /*
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String pwd = "123456";
        String url = "jdbc:mysql://localhost/onlinexam"
                + "?useUnicode=true&characterEncoding=UTF-8";
        String table = "oe_student";
        */
//        DbInfoUtil dbInfoUtil = new DbInfoUtil();
        Properties props = new Properties();
        InputStream in = DbInfoUtil.class.getResourceAsStream("src/main/resources/config.properties");
        try {
            props.load(in);
            in.close();
            System.out.println(props.get("controllerPackage"));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(dbInfoUtil.get());

//        Map<String,List> maps = getTablesInfo();
//        Iterator<Map.Entry<String, List>> it = maps.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, List> entry = it.next();
//            System.out.println("key= " + entry.getKey() + " and value= " +StringUtils.join(entry.getValue(),","));
//
//        }
    }

}
