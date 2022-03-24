package com.gr.imp;


import com.gr.in.BeanAutoDao;
import com.gr.in.GetTablesDao;
import com.gr.utils.ConfigUtil;
import com.gr.utils.DataTypeUtil;
import com.gr.utils.FileUtil;
import com.gr.utils.NameUtil;

import java.util.List;

/**
 * @program: wwis-kunming
 * @description: 生成Bean实体类的dao层实现类
 * @author: Shizh
 * @create: 2018-07-23 15:04
 **/
@SuppressWarnings("all")
public class BeanAutoDaoImpl implements BeanAutoDao {
    //从GetTablesDaoImpl中获得装有所有表结构的List
    GetTablesDao getTables = new GetTablesDaoImpl();
    List<TableStruct> list = getTables.getTablesStruct();

    //通过表名、字段名称、字段类型创建Bean实体
    @Override
    public boolean createBean() {
//获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String beanFalg=ConfigUtil.beanFlag;
//Bean实体类的包名
        String beanPackage=ConfigUtil.beanPackage;
//判断是否生成实体类
        if("true".equals(beanFalg) ){
//将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath=beanPackage.replace(".", "/");
//Bean实体类的路径
            String path =projectPath+"/src/"+beanPath;
//遍历装有所有表结构的List
            for (int i = 0; i < list.size(); i++) {
//文件名
                String fileName= NameUtil.fileName(list.get(i).getTableName())+"Bean";
//获得每个表的所有列结构
                List<ColumnStruct> columns =list.get(i).getColumns();
//(实体类）文件内容
                String packageCon ="package"+"\t"+beanPackage+";\n\n";
                StringBuffer importCon=new StringBuffer();
                importCon.append("import"+"\t"+"javax.persistence.*;\n");
                importCon.append("import"+"\t"+"gddxit.waterhub.data.common.entitylistener.BaseEntity;\n");
                importCon.append("import"+"\t"+"gddxit.waterhub.data.common.tenant.MultiTenantSupport;\n");
                String table = "@Table(name= "+list.get(i).getTableName()+")\n";
                String entity = "@Entity\n";
                String className ="public"+"\t"+"class"+"\t"+fileName+"  extends MultiTenantSupport implements BaseEntity {\n\n";
                StringBuffer classCon =new StringBuffer();
                StringBuffer gettersCon = new StringBuffer();
                StringBuffer settersCon = new StringBuffer();
                StringBuffer noneConstructor =new StringBuffer();
                StringBuffer constructor =new StringBuffer();
                String constructorParam="";
                StringBuffer constructorCon=new StringBuffer();
//遍历List，将字段名称和字段类型写进文件
                for (int j = 0; j < columns.size(); j++) {
//变量名（属性名）
                    String columnName =NameUtil.columnName(columns.get(j).getColumnName());
//获得数据类型
                    String type = columns.get(j).getDataType();
//将mysql数据类型转换为java数据类型
                    String dateType = DataTypeUtil.getType(type);

//有Timestamp类型的数据需导包
                    if("LocalDateTime".equals(dateType)){
                        if(importCon.indexOf("java.time.LocalDateTime")==-1){
                            importCon.append("import"+"\t"+" java.time.LocalDateTime;\n\n");
                        }
                    }
                    if("LocalDate".equals(dateType)){
                        if(importCon.indexOf("java.time.LocalDate")==-1){
                            importCon.append("import"+"\t"+" java.time.LocalDate;\n\n");
                        }
                    }
                    if("Time".equals(dateType)){
                        if(importCon.indexOf("java.time.Time")==-1){
                            importCon.append("import"+"\t"+" java.time.Time;\n\n");
                        }
                    }

                    if(columnName.equals("id")){
                        classCon.append("\t "+"private"+"\t"+"@Column(name=\""+columns.get(j).getColumnName()+"\")\t "+"@Id @GeneratedValue(strategy = GenerationType.IDENTITY)"+"\t "+dateType+"\t "+columnName+";\n");
                    }else{
                        classCon.append("\t "+"private"+"\t"+"@Column(name=\""+columns.get(j).getColumnName()+"\")\t "+dateType+"\t "+columnName+";\n");
                    }
//生成注解属性
//get、set的方法名
                    String getSetName=columnName.substring(0,1).toUpperCase()+columnName.substring(1);
//生成get方法
                    gettersCon.append("\t "+"public"+"\t"+dateType+"\t"+"get"+getSetName+"(){\n"+
                            "\t\t"+"return"+"\t"+columnName+";\n"+
                            "\t"+"}\n");
//生成set方法
                    settersCon.append("\t"+"public void"+"\t"+"set"+getSetName+"("+dateType+" "+columnName+"){\n"+
                            "\t\t"+"this."+columnName+" = "+columnName+";\n"+
                            "\t"+"}\n");
//获得有参构造器参数
                    if(constructorParam==null||"".equals(constructorParam)){
                        constructorParam=dateType+" "+columnName;
                    }else{
                        constructorParam+=","+dateType+" "+columnName;
                    }
//获得有参构造器的内容
                    constructorCon.append("\t\t"+"this."+columnName+" = "+columnName+";\n");
                }
//生成无参构造器
                noneConstructor.append("\t"+"public"+"\t"+fileName+"(){\n"+
                        "\t\t"+"super();\n"+
                        "\t"+"}\n");
//生成有参构造
                constructor.append("\t"+"public"+" "+fileName+"("+constructorParam+"){\n"+
                        "\t\t"+"super();\n"+constructorCon+
                        "\t"+"}\n");
//拼接(实体类）文件内容
                StringBuffer content=new StringBuffer();
                content.append(packageCon);
                content.append(importCon.toString());
                content.append(table);
                content.append(entity);
                content.append(className);
                content.append(classCon.toString());
                content.append(gettersCon.toString());
                content.append(settersCon.toString());
                content.append(noneConstructor.toString());
                content.append(constructor.toString());
                content.append("}");
                FileUtil.createFileAtPath(path+"/", fileName+".java", content.toString());
            }
            return true;
        }
        return false;
    }
}
