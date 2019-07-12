package com.gr.in;

import java.util.List;

/**
 * @program: wwis-kunming
 * @description: 获取数据表及其结构的dao层接口
 * @author: Shizh
 * @create: 2018-07-23 14:59
 **/
public interface GetTablesDao {
    //获得数据库的所有表名
     List getTablesName();

    //获得数据表中的字段名称、字段类型
     List getTablesStruct();
}
