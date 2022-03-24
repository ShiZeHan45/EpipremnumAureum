package com.gr.imp;


import com.gr.in.RepositoryAutoDao;
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
public class RepositoryAutoDaoImpl implements RepositoryAutoDao {
    //从GetTablesDaoImpl中获得装有所有表结构的List
    Map<String,List> maps = DbInfoUtil.getTablesInfo();

    //通过表名、字段名称、字段类型创建Bean实体
    @Override
    public boolean createRepository() {
//获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String repositoryFlag=ConfigUtil.repositoryFlag;
//Bean实体类的包名
        String repositoryPackage=ConfigUtil.repositoryPackage;
//判断是否生成实体类
        if("true".equals(repositoryFlag) ){
//将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath=repositoryPackage.replace(".", "/");
//Bean实体类的路径
            String path =projectPath+"/src/"+beanPath;
//遍历装有所有表结构的List
            Iterator<Map.Entry<String, List>> it = maps.entrySet().iterator();
            while (it.hasNext()) {
//文件名+
                Map.Entry<String, List> entry = it.next();
                String  baseName = NameUtil.fileName(entry.getKey());
                String fileName=baseName+"Repository";
//(实体类）文件内容
                String packageCon ="package"+"\t"+repositoryPackage+";\n\n";
                StringBuffer importCon=new StringBuffer();
                importCon.append("import"+"\t"+ConfigUtil.beanPackage+"."+baseName+";\n");
                importCon.append("import"+"\t"+"org.springframework.data.jpa.repository.JpaRepository;\n");
                importCon.append("import"+"\t"+"org.springframework.data.jpa.repository.JpaSpecificationExecutor;\n");
//                importCon.append("/**\n*auto generate all by szh\n*\n*/\n\n");
                String className ="public"+"\t"+"interface"+"\t"+fileName+" extends JpaRepository<"+baseName+", Long>,JpaSpecificationExecutor<"+baseName+">{\n\n";
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
