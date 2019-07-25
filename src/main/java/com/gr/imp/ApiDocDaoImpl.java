package com.gr.imp;

import com.gr.in.ApiDocDao;
import com.gr.utils.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: AutoGenerate
 * @description: 生成接口文档
 * @author: Shizh
 * @create: 2518-11-04 15:32
 **/
public class ApiDocDaoImpl implements ApiDocDao {
    //从GetTablesDaoImpl中获得装有所有表结构的List
    Map<String,List> maps = DbInfoUtil.getTablesInfo();
    @Override
    public boolean createDoc() {
        //获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String beanFalg=ConfigUtil.apiDocFlag;
//Bean实体类的包名
        String beanPackage=ConfigUtil.apiDocPath;

        if("true".equals(beanFalg) ){
            //将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath=beanPackage.replace(".", "/");
//Bean实体类的路径
            String path =projectPath+"/src/"+beanPath;
//遍历装有所有表结构的List
            Iterator<Map.Entry<String, List>> it = maps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List> entry = it.next();
                System.out.println("key= " + entry.getKey() + " and value= " +org.apache.commons.lang3.StringUtils.join(entry.getValue(),","));//文件名
                String baseName = NameUtil.fileName(entry.getKey());
                String fileName = baseName+"DOC";
                String tableName = StringUtils.toLowerCaseFirstOne(baseName);
                //获得每个表的所有列结构
                List<Column> columns =entry.getValue();
                StringBuffer docContent = new StringBuffer();
                StringBuffer returnJson = new StringBuffer();
                StringBuffer inJson = new StringBuffer();
                docContent.append("列表\n");
                docContent.append("请求地址:"+tableName+"\n");
                docContent.append("请求方法:GET\n");
                docContent.append("出参:\n");
                docContent.append("|字段名称|类型|长度|字段描述|\n");
                docContent.append("|:----:|:----:|:----:|:----:|\n");
//                inJson.append("字段名\t\t\t\t\trealtion\t\t\t\t\t数据类型\t\t\t\t字段长度\t\t\t\t是否为空\t\t\t\t字段描述\n");
                for (int j = 0; j < columns.size(); j++) {
                    Column column = columns.get(j);
                    String codeName = column.getColumnName();
                    String upCodeName = NameUtil.fileName(codeName);
                    String code = StringUtils.toLowerCaseFirstOne(upCodeName);
                    String remark = column.getColumnRemark()!=null?column.getColumnRemark():column.getColumnName();//获取备注
                    int columnSize = column.getColumnSize();
                    boolean isNull = column.isNull();
                    String dateType =   DataTypeUtil.getType(column.getDbTypeName().toLowerCase());
                    docContent.append("|"+code+"|"+(dateType.equals("Byte")?code:dateType)+"|"+columnSize+"|"+remark+"|\n");



//                    if(remark.indexOf("dict")>=0){
//                        returnJson.append(adjustStr(new StringBuffer(code),25)+adjustStr(new StringBuffer(code),25)+adjustStr(new StringBuffer(dateType),25)+adjustStr(new StringBuffer(columnSize+""),25)+adjustStr(new StringBuffer(isNull?"true":"false"),25)+adjustStr(new StringBuffer(remark),25)+"\n");
//                        returnJson.append(adjustStr(new StringBuffer(code+"Name"),25)+adjustStr(new StringBuffer(code),25)+adjustStr(new StringBuffer(dateType),25)+adjustStr(new StringBuffer(columnSize+""),25)+adjustStr(new StringBuffer(isNull?"true":"false"),25)+adjustStr(new StringBuffer(remark),25)+"\n");
//                    }else{
//                        returnJson.append(adjustStr(new StringBuffer(code),25)+adjustStr(new StringBuffer("否"),25)+adjustStr(new StringBuffer(dateType),25)+adjustStr(new StringBuffer(columnSize+""),25)+adjustStr(new StringBuffer(isNull?"true":"false"),25)+adjustStr(new StringBuffer(remark),25)+"\n");
//                    }
//                    if(remark.indexOf("dict")>=0){
//                        inJson.append(adjustStr(new StringBuffer(code),25)+adjustStr(new StringBuffer(code),25)+adjustStr(new StringBuffer(dateType),25)+adjustStr(new StringBuffer(columnSize+""),25)+adjustStr(new StringBuffer(isNull?"true":"false"),25)+adjustStr(new StringBuffer(remark),25)+"\n");
//                    }else{
//                        inJson.append(adjustStr(new StringBuffer(code),25)+adjustStr(new StringBuffer("否"),25)+adjustStr(new StringBuffer(dateType),25)+adjustStr(new StringBuffer(columnSize+""),25)+adjustStr(new StringBuffer(isNull?"true":"false"),25)+adjustStr(new StringBuffer(remark),25)+"\n");
//                    }


                }
//                docContent.append(returnJson);
//                docContent.append("新增\n");
//                docContent.append("URL:/api/"+tableName+"\n");
//                docContent.append("METHOD:POST\n");
//                docContent.append("INJSON:\n");
//                docContent.append(inJson);
//                docContent.append("编辑\n");
//                docContent.append("URL:/api/"+tableName+"/id\n");
//                docContent.append("METHOD:PATCH\n");
//                docContent.append("INJSON:\n");
//                docContent.append(inJson);
//                docContent.append("删除\n");
//                docContent.append("URL:/api/"+tableName+"/id\n");
//                docContent.append("METHOD:DELETE\n");
                /**
                 * 生成文件
                 */
                FileUtil.createFileAtPath(path+"/", fileName+".md", docContent.toString());
            }
            return true;
        }
        return false;
    }


    /**
     * 微调格式
     */

    public static String adjustStr(StringBuffer str,int size){
        if(str.length()<size){
            for(int i = str.length();i<size;i++){
                str.append(" ");
            }
        }
        return str.toString();
    }
}
