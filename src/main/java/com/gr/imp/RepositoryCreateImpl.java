package com.gr.imp;

import com.gr.config.Config;
import com.gr.in.BaseCreate;
import com.gr.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @description: 基于freemarker的实体生成
 * @author: Shizh
 * @create: 2022-03-24 10:39
 */
@Service
public class RepositoryCreateImpl implements BaseCreate {
    @Autowired
    private Config config;

    @Autowired
    private DbInfoUtil dbInfoUtil;
    
    @Autowired
    private FreemarkerService freemarkerService;

    @Override
    public void create() {
        Map<String, List> maps = dbInfoUtil.getTablesInfo();
        Iterator<Map.Entry<String, List>> repositoryInfoMap = maps.entrySet().iterator();
        //将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
        String repositoryFileBasePath = config.getRepositoryPackagePath().replace(".", "\\");
        //Bean实体类的路径
        String repositoryFilePath = config.getProjectPath() + "\\src\\" + repositoryFileBasePath + "\\";
        while (repositoryInfoMap.hasNext()) {
            Map.Entry<String, List> entry = repositoryInfoMap.next();
            System.out.println("key= " + entry.getKey() + " and value= " + org.apache.commons.lang3.StringUtils.join(entry.getValue(), ","));
            //文件名
            String entityName = NameUtil.fileName(entry.getKey());
            //获得每个表的所有列结构
            List<Column> columns = entry.getValue();
            if (!CollectionUtils.isEmpty(columns)) {
                Map<String, Object> repositoryMap = new HashMap<String, Object>();
                repositoryMap.put("repositoryName", entityName);// 实体类名
                repositoryMap.put("repositoryPackagePath", config.getRepositoryPackagePath());// repository类名
                repositoryMap.put("entityPackagePath", config.getEntityPackagePath());// repository类名
                repositoryMap.put("entityName", entityName);// repository类名
                try {
                    freemarkerService.generatorFile("Repository.ftl", repositoryFilePath, entityName + "Repository.java", repositoryMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}