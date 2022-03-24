package com.gr.utils;

/**
 * @program: wwis-kunming
 * @description: 名字处理工具类（文件名、变量名等）
 * @author: Shizh
 * @create: 2018-07-23 15:07
 **/
public class NameUtil {
    /**
     * 处理文件名
     *
     * @param tableName 数据表表名
     * @return
     */
    public static String fileName(String tableName) {
        String fileName = "";
    //获得表名
    //去掉表名的下划线
        String[] tablesName = tableName.split("_");
        for (int j = 0; j < tablesName.length; j++) {
//将每个单词的首字母变成大写
            tablesName[j] = tablesName[j].substring(0, 1).toUpperCase() + tablesName[j].substring(1);
            fileName += tablesName[j].replace("Tb", "");
        }
        return fileName;
    }

    /**
     * 处理变量名（属性名）
     *
     * @param columnName 字段名称
     * @return
     */
    public static String columnName(String columnName) {
//将字段名称user_name格式变成userName格式
        String colName = "";
//根据下划线将名字分为数组
        String[] columnsName = columnName.split("_");
//遍历数组，将除第一个单词外的单词的首字母大写
        for (int h = 0; h < columnsName.length; h++) {
            for (int k = 1; k < columnsName.length; k++) {
                columnsName[k] = columnsName[k].substring(0, 1).toUpperCase() + columnsName[k].substring(1);
            }
//拼接字符串以获得属性名（字段名称）
            colName += columnsName[h];
        }
        return colName;
    }
}
