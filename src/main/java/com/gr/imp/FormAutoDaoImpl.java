package com.gr.imp;


import com.gr.in.FormAutoDao;
import com.gr.utils.ConfigUtil;
import com.gr.utils.DbInfoUtil;
import com.gr.utils.FileUtil;
import com.gr.utils.NameUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: wwis-kunming
 * @description: 生成Bean实体类的dao层实现类
 * @author: Shizh
 * @create: 2018-07-23 15:04
 **/
public class FormAutoDaoImpl implements FormAutoDao {
    //从GetTablesDaoImpl中获得装有所有表结构的List
    Map<String,List> maps = DbInfoUtil.getTablesInfo();

    //通过表名、字段名称、字段类型创建Bean实体
    @Override
    public boolean createForm() {
//获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String formFlag=ConfigUtil.formFlag;
//Bean实体类的包名
        String formPackage=ConfigUtil.formPackage;
//判断是否生成实体类
        if("true".equals(formFlag) ){
//将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath=formPackage.replace(".", "/");
//Bean实体类的路径
            String path =projectPath+"/src/"+beanPath;
//遍历装有所有表结构的List
            Iterator<Map.Entry<String, List>> it = maps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List> entry = it.next();
                String  baseName = NameUtil.fileName(entry.getKey());
//文件名
                String fileName=baseName+"Form";
//(实体类）文件内容
                String packageCon ="package"+"\t"+formPackage+";\n\n";
                StringBuffer importCon=new StringBuffer();
                importCon.append("import"+"\t"+ConfigUtil.beanPackage+"."+baseName+";\n");
                String className ="public"+"\t"+"class"+"\t"+fileName+"\textends"+"\t"+baseName+"{\n\n";
//拼接(实体类）文件内容
                StringBuffer content=new StringBuffer();
                content.append(packageCon);
                content.append(importCon.toString());
                content.append(className);
                content.append("}");
                FileUtil.createFileAtPath(path+"/", fileName+".java", content.toString());
            }
            return true;
        }
        return false;
    }
}
