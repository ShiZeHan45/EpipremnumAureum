package com.gr.imp;


import com.gr.in.VO;
import com.gr.utils.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * VO生成
 */
public class VOImpl implements VO {
    //从GetTablesDaoImpl中获得装有所有表结构的List
    Map<String, List> maps = DbInfoUtil.getTablesInfo();

    @Override
    public boolean createVO() {
//获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String beanFlag = ConfigUtil.voFlag;
//Bean实体类的包名
        String beanPackage = ConfigUtil.voPackage;
//判断是否生成实体类
        if ("true".equals(beanFlag)) {
//将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath = beanPackage.replace(".", "/");
//Bean实体类的路径
            String path = projectPath + "/src/" + beanPath;
//遍历装有所有表结构的List
            Iterator<Map.Entry<String, List>> it = maps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List> entry = it.next();
                System.out.println("key= " + entry.getKey() + " and value= " + org.apache.commons.lang3.StringUtils.join(entry.getValue(), ","));
//文件名
                String fileName = NameUtil.fileName(entry.getKey());
                String tableName = NameUtil.fileName(entry.getKey());
//获得每个表的所有列结构
                List<Column> columns = entry.getValue();
//(实体类）文件内容
                String packageCon = "package" + "\t" + beanPackage + ";\n\n";
                StringBuffer importCon = new StringBuffer();
                importCon.append("import io.swagger.annotations.ApiModelProperty;\n");
                importCon.append("import org.springframework.beans.BeanUtils;\n");
                String className = "public" + "\t" + "class" + "\t" + fileName + "{\n\n";
                StringBuffer classCon = new StringBuffer();
                StringBuffer gettersCon = new StringBuffer();
                StringBuffer settersCon = new StringBuffer();
                StringBuffer noneConstructor = new StringBuffer();
                StringBuffer constructor = new StringBuffer();
                String constructorParam = "";
                StringBuffer constructorCon = new StringBuffer();
//遍历List，将字段名称和字段类型写进文件
                for (int j = 0; j < columns.size(); j++) {
                    Column column = columns.get(j);
                    String remark = column.getColumnRemark() != null ? column.getColumnRemark() : column.getColumnName();//获取备注
                    boolean isNull = column.isNull();
                    int columnSize = column.getColumnSize();
                    String dateType = DataTypeUtil.getType(column.getDbTypeName().toLowerCase());

                    String codeName = column.getColumnName();
                    String upCodeName = NameUtil.fileName(codeName);
                    String code = StringUtils.toLowerCaseFirstOne(upCodeName);

                    if ("Date".equals(dateType)) {
                        if (importCon.indexOf("java.time.LocalDate") == -1) {
                            importCon.append("import" + "\t" + " java.time.LocalDate;\n\n");
                        }
                    }
//有Timestamp类型的数据需导包
                    if ("LocalDateTime".equals(dateType)) {
                        if (importCon.indexOf("java.time.LocalDateTime") == -1) {
                            importCon.append("import" + "\t" + " java.time.LocalDateTime;\n\n");
                        }
                    }
                    if ("LocalTime".equals(dateType)) {
                        if (importCon.indexOf("java.time.LocalTime") == -1) {
                            importCon.append("import" + "\t" + " java.time.LocalTime;\n\n");
                        }
                    }
                    if (code.equals("tenantId")) {
                        continue;
                    }

                    if (code.equals("id")) {
                        classCon.append("/**\n*" + remark + "\n*\n*/");
                        classCon.append("\t " + "private" + "\t" + "@Column(name=\"" + columns.get(j).getColumnName() + "\")\t " + "@Id @GeneratedValue(strategy = GenerationType.IDENTITY)" + "\t " + dateType + "\t " + code + ";\n");
                    } else {
                        classCon.append("/**\n*" + remark + "\n*\n*/");
                        String clasCon = "";
                        String isNullCon = "";
                        String notBlankCon = "";
                        classCon.append("\t " + "private " + clasCon + " " + dateType + "\t " + code + ";\n");
                    }
                    String getSetName = code.substring(0, 1).toUpperCase() + code.substring(1);
//生成get方法
                    gettersCon.append("\t " + "public" + "\t" + dateType + "\t" + "get" + getSetName + "(){\n" +
                            "\t\t" + "return" + "\t" + code + ";\n" +
                            "\t" + "}\n");
//生成set方法
                    settersCon.append("\t" + "public void" + "\t" + "set" + getSetName + "(" + dateType + " " + code + "){\n" +
                            "\t\t" + "this." + code + " = " + code + ";\n" +
                            "\t" + "}\n");
//拼接(实体类）文件内容
//                    }

                    //获得有参构造器参数
                    if (constructorParam == null || "".equals(constructorParam)) {
                        constructorParam = dateType + " " + code;
                    } else {
                        constructorParam += "," + dateType + " " + code;
                    }
//获得有参构造器的内容
                    constructorCon.append("\t\t" + "this." + code + " = " + code + ";\n");
                }

                //生成无参构造器
                noneConstructor.append("\t" + "public" + "\t" + fileName + "(){\n" +
                        "\t\t" + "super();\n" +
                        "\t" + "}\n");
//生成有参构造
                constructor.append("\t" + "public" + " " + fileName + "(" + constructorParam + "){\n" +
                        "\t\t" + "super();\n" + constructorCon +
                        "\t" + "}\n");
                //判断外键关联
                StringBuffer content = new StringBuffer();
                content.append(packageCon);
                content.append(importCon.toString());
                content.append(className);
                content.append(classCon.toString());
                content.append(gettersCon.toString());
                content.append(settersCon.toString());
                content.append(noneConstructor.toString());
                content.append(constructor.toString());
                content.append("}");
                FileUtil.createFileAtPath(path + "/", fileName + ".java", content.toString());

            }

            return true;
        }
        return false;
    }
}
