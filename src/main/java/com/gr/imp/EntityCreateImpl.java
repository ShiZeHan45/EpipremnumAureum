package com.gr.imp;

import com.gr.config.Config;
import com.gr.in.BaseCreate;
import com.gr.in.EntityCreate;
import com.gr.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @description: 基于freemarker的实体生成
 * @author: Shizh
 * @create: 2022-03-24 10:39
 */
@Service
public class EntityCreateImpl implements BaseCreate {
    @Autowired
    private Config config;

    @Autowired
    private DbInfoUtil dbInfoUtil;

    @Value("classpath:ftl")
    private Resource entityResource;
    @Autowired
    private FreemarkerService freemarkerService;

    @Override
    public void create() {
        Map<String, List> maps = dbInfoUtil.getTablesInfo();
        Iterator<Map.Entry<String, List>> entityInfoMap = maps.entrySet().iterator();
        //将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
        String entityFileBasePath = config.getEntityPackagePath().replace(".", "\\");
        //Bean实体类的路径
        String entityFilePath = config.getProjectPath() + "\\src\\" + entityFileBasePath + "\\";
        while (entityInfoMap.hasNext()) {
            Map.Entry<String, List> entry = entityInfoMap.next();
            System.out.println("key= " + entry.getKey() + " and value= " + org.apache.commons.lang3.StringUtils.join(entry.getValue(), ","));
            //文件名
            String tableName = entry.getKey();
            String entityName = NameUtil.fileName(entry.getKey());
            //获得每个表的所有列结构
            List<Column> columns = entry.getValue();
            if (!CollectionUtils.isEmpty(columns)) {
                Map<String, Object> entityMap = new HashMap<String, Object>();
                entityMap.put("entityName", entityName);// 实体类名
                entityMap.put("entityPackagePath", config.getEntityPackagePath());// 实体类名
                entityMap.put("tableName", tableName);// 表名
                List<Map<String, String>> paramsList = new ArrayList<Map<String, String>>();
                for (Column column : columns) {
                    //注释
                    String remark = column.getColumnRemark() != null ? column.getColumnRemark() : column.getColumnName();//获取备注
                    //字段名
                    String columnName = NameUtil.fileName(column.getColumnName());
                    String lowColumnName = StringUtils.toLowerCaseFirstOne(columnName);
                    //数据类型
                    String dateType = DataTypeUtil.getType(column.getDbTypeName().toLowerCase());
                    if (!lowColumnName.equals("tenantId")) {
                        Map<String, String> entityDetailMap = new HashMap<String, String>();
                        entityDetailMap.put("remark", remark);
                        entityDetailMap.put("fieldType", dateType);
                        entityDetailMap.put("fieldName", lowColumnName);
                        paramsList.add(entityDetailMap);
                    }
                }
                entityMap.put("params", paramsList);
                try {
                    freemarkerService.generatorFile("Entity.ftl", entityFilePath, entityName + ".java", entityMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}