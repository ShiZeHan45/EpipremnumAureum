package com.gr.imp;

import com.gr.config.Config;
import com.gr.in.BaseCreate;
import com.gr.utils.Column;
import com.gr.utils.DbInfoUtil;
import com.gr.utils.NameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description: 基于freemarker的实体生成
 * @author: Shizh
 * @create: 2022-03-24 10:39
 */
@Service
public class ControllerCreateImpl implements BaseCreate {
    @Autowired
    private Config config;

    @Autowired
    private DbInfoUtil dbInfoUtil;
    
    @Autowired
    private FreemarkerService freemarkerService;

    @Override
    public void create() {
        Map<String, List> maps = dbInfoUtil.getTablesInfo();
        Iterator<Map.Entry<String, List>> controllerInfoMap = maps.entrySet().iterator();
        //将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
        String controllerFileBasePath = config.getControllerPackagePath().replace(".", "\\");
        //Bean实体类的路径
        String controllerFilePath = config.getProjectPath() + "\\src\\" + controllerFileBasePath + "\\";
        while (controllerInfoMap.hasNext()) {
            Map.Entry<String, List> entry = controllerInfoMap.next();
            System.out.println("key= " + entry.getKey() + " and value= " + org.apache.commons.lang3.StringUtils.join(entry.getValue(), ","));
            //文件名
            String entityName = NameUtil.fileName(entry.getKey());
            //获得每个表的所有列结构
            List<Column> columns = entry.getValue();
            if (!CollectionUtils.isEmpty(columns)) {
                Map<String, Object> controllerMap = new HashMap<String, Object>();
                controllerMap.put("controllerPackagePath", config.getControllerPackagePath());// controller类名
                controllerMap.put("entityName", entityName);//类名
                controllerMap.put("voPackagePath", config.getVOPackagePath());//类名
                controllerMap.put("servicePackagePath", config.getServicePackagePath());//service路徑
                try {
                    freemarkerService.generatorFile("Controller.ftl", controllerFilePath, entityName + "Controller.java", controllerMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}