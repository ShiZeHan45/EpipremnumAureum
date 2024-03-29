package com.gr.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @program: wwis-kunming
 * @description: mysql数据类型处理工具类
 * @author: Shizh
 * @create: 2018-07-23 15:09
 **/
public class DataTypeUtil {
    public static String getType(String dataType){
        String type="";
        if("tinyint".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        if("smallint".equals(StringUtils.substringBefore(dataType, "("))){
            type="Short";
        }
        if("mediumint".equals(StringUtils.substringBefore(dataType, "("))){
            type="Integer";
        }
        if("int".equals(StringUtils.substringBefore(dataType, "("))){
            type="Integer";
        }
        if("integer".equals(StringUtils.substringBefore(dataType, "("))){
            type="Integer";
        }
        if("bigint".equals(StringUtils.substringBefore(dataType, "("))){
            type="Long";
        }
        if("bit".equals(StringUtils.substringBefore(dataType, "("))){
            type="Boolean";
        }
        if("double".equals(StringUtils.substringBefore(dataType, "("))){
            type="Double";
        }
        if("float".equals(StringUtils.substringBefore(dataType, "("))){
            type="Float";
        }
        if("decimal".equals(StringUtils.substringBefore(dataType, "("))){
            type="BigDecimal";
        }
        if("char".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        if("varchar".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        if("timestamp".equals(StringUtils.substringBefore(dataType, "("))){
            type="LocalDateTime";
        }
        if("datetime".equals(StringUtils.substringBefore(dataType, "("))){
            type="LocalDateTime";
        }
        if("date".equals(StringUtils.substringBefore(dataType, "("))){
            type="LocalDate";
        }
        if("time".equals(StringUtils.substringBefore(dataType, "("))){
            type="LocalTime";
        }
        if("tinytext".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        if("enum".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        if("set".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        if("text".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        if("mediumtext".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        if("longtext".equals(StringUtils.substringBefore(dataType, "("))){
            type="String";
        }
        return type;
    }

}
