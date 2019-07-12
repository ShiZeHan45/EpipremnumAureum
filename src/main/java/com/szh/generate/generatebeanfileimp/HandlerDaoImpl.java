package com.szh.generate.generatebeanfileimp;


import com.szh.generate.generatebeanfilein.HandlerDao;
import com.szh.generate.generatebeanfileutils.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: wwis-kunming
 * @description: 生成Bean实体类的dao层实现类
 * @author: Shizh
 * @create: 2018-07-23 15:04
 **/
@SuppressWarnings("all")
public class HandlerDaoImpl implements HandlerDao {
    //从GetTablesDaoImpl中获得装有所有表结构的List
    Map<String,List> maps = DbInfoUtil.getTablesInfo();

    //通过表名、字段名称、字段类型创建Bean实体
    @Override
    public boolean createHandler() {
//获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String handlerFlag=ConfigUtil.handlerFlag;
        String dictPackage=ConfigUtil.dictPackage;
//Bean实体类的包名
        String handlerPackage=ConfigUtil.handlerPackage;
//判断是否生成实体类
        if("true".equals(handlerFlag) ){
//将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath=handlerPackage.replace(".", "/");
//Bean实体类的路径
            String path =projectPath+"/src/"+beanPath;
//遍历装有所有表结构的List
            Iterator<Map.Entry<String, List>> it = maps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List> entry = it.next();
                String  baseName = NameUtil.fileName(entry.getKey());
                String fileName=baseName+"Handler";
                String upBeanName = baseName+"Bean";
                String lowBeanName = StringUtils.toLowerCaseFirstOne(baseName);
//(实体类）文件内容
                String packageCon ="package"+"\t"+handlerPackage+";\n\n";
                StringBuffer importCon=new StringBuffer();
                importCon.append("import"+"\tjava.util.ArrayList;\n");
                importCon.append("import"+"\t"+"java.util.Iterator;\n");
                importCon.append("import"+"\t"+"java.util.LinkedHashMap;\n");
                importCon.append("import"+"\t"+"java.util.List;\n");
                importCon.append("import"+"\t"+"java.util.Map;\n");
                importCon.append("/**\n*auto generate all by szh\n*\n*/\n\n");
                String className ="public"+"\t"+"class"+"\t"+fileName+"{\n\n";
                StringBuffer classCon =new StringBuffer();
                classCon.append("\t"+"public List<Object> page(Map<String, Object> content) {\n");
                classCon.append("\t"+"List<"+upBeanName+"> "+lowBeanName+"s = (List)content.get(\"data\");\n");
                classCon.append("\t"+"List<Object> "+lowBeanName+"List = new ArrayList();\n");
                classCon.append("\t"+"Iterator iterator = "+lowBeanName+"s.iterator();\n");
                classCon.append("\t"+"while(iterator.hasNext()) {\n");
                classCon.append("\t"+"\t"+upBeanName+" "+lowBeanName+" = ("+upBeanName+")iterator.next();\n");
                //获得每个表的所有列结构
                List<Column> columns =entry.getValue();
                classCon.append("\t"+"\t"+"Map<String, Object> "+lowBeanName+"Map = new LinkedHashMap();\n");
                for (int j = 0; j < columns.size(); j++) {
                    String columnName =NameUtil.columnName(columns.get(j).getColumnName());
                    String upColumnName = StringUtils.toUpperCaseFirstOne(columnName);
                    /**
                     * 扫描根据类名扫描dict包中 是否存在  如果存在就getId
                     */
                    classCon.append("\t"+"\t"+""+lowBeanName+"Map.put(\""+columnName+"\","+lowBeanName+".get"+upColumnName+"());\n");
                    if(columns.size()-1==j){
                        classCon.append("\t"+"\t"+""+lowBeanName+"List.add("+lowBeanName+"Map);\n");
                    }
                }
                classCon.append("\t"+"}\n");
                classCon.append("\t"+"return "+lowBeanName+"List;\n");
                classCon.append("\t"+"}\n");
//拼接(实体类）文件内容
                StringBuffer content=new StringBuffer();
                content.append(packageCon);
                content.append(importCon.toString());
                content.append(className);
                content.append(classCon);
                content.append("}");
                FileUtil.createFileAtPath(path+"/", fileName+".java", content.toString());
            }
            return true;
        }
        return false;
    }
}
