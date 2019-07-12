package com.gr.imp;




import com.gr.in.ServiceAutoDao;
import com.gr.utils.*;
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
public class ServiceAutoDaoImpl implements ServiceAutoDao {
    //从GetTablesDaoImpl中获得装有所有表结构的List
    Map<String,List> maps = DbInfoUtil.getTablesInfo();

    //通过表名、字段名称、字段类型创建Bean实体
    @Override
    public boolean createService() {
//获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String serviceImplFlag=ConfigUtil.serviceImplFlag;
//Bean实体类的包名
        String serviceImplPackage=ConfigUtil.serviceImplPackage;
//判断是否生成实体类
        if("true".equals(serviceImplFlag) ){
//将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath=serviceImplPackage.replace(".", "/");
//Bean实体类的路径
            String path =projectPath+"/src/"+beanPath;
//遍历装有所有表结构的List
            Iterator<Map.Entry<String, List>> it = maps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List> entry = it.next();
                String  baseName = NameUtil.fileName(entry.getKey());
//文件名
                String fileName=baseName+"Service";
                String baseNameBean = baseName+"Bean";
//(实体类）文件内容
                String packageCon ="package"+"\t"+serviceImplPackage+";\n\n";
                StringBuffer importCon=new StringBuffer();
                importCon.append("import"+"\t"+ConfigUtil.beanPackage+"."+baseNameBean+";\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.common.util.BeanUtils;\n");
                importCon.append("import"+"\t"+"com.gddxit.wwis.common.utils.DateUtil;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.jpa.service.AbstractEntityService;\n");
                importCon.append("import"+"\t"+ConfigUtil.formPackage+"."+baseName+"Form"+";\n");
                importCon.append("import"+"\t"+ConfigUtil.repositoryPackage+"."+baseName+"Repository;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.mvc.result.AddResult;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.mvc.result.BaseResult;\n");
                importCon.append("import"+"\t"+"org.springframework.beans.factory.annotation.Autowired;\n");
                importCon.append("import"+"\t"+"org.springframework.data.jpa.domain.Specification;\n");
                importCon.append("import"+"\t"+"org.springframework.transaction.annotation.Transactional;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.base.jpa.orm.LogOperation;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.base.thread.RBACThreadContext;\n");
                importCon.append("import"+"\t"+"org.springframework.stereotype.Service;\n");
                importCon.append("import"+"\t"+"java.util.List;\n");
                importCon.append("import"+"\t"+"java.util.HashMap;\n");
                importCon.append("import"+"\t"+"com.gddxit.wwis.common.utils.LoggerUtil;\n");
                importCon.append("/**\n*auto generate all by szh\n*\n*/\n\n");
                importCon.append("@Service\n");
                String className ="public"+"\t"+"class"+"\t"+fileName+" extends"+" AbstractEntityService<"+baseNameBean+">{\n\n";
//拼接(实体类）文件内容er
                StringBuffer classCon =new StringBuffer();
                classCon.append("@Autowired\n");
                String tableName = StringUtils.toLowerCaseFirstOne(baseName);
                classCon.append("private"+"\t"+baseName+"Repository"+"\t"+StringUtils.toLowerCaseFirstOne(baseName)+"Repository;\n");
                String upBeanName = baseName;
                String baseForm = baseName+"Form";
                String lowForm = tableName+"Form";


                /**
                 * 保存
                 */
                classCon.append("@Transactional\n");
                classCon.append("public"+"\t"+baseNameBean+"\tsave("+baseName+"Form\t"+lowForm+",AddResult\tresult){\n");
                classCon.append("\t"+baseNameBean+"\t"+tableName+"=new\t"+baseNameBean+"();\n");
                classCon.append("\t"+"CglibBeanCopierUtils.copyProperties("+lowForm+","+tableName+");\n");
                classCon.append("\t"+"this."+tableName+"Repository"+".save("+tableName+");\n");
                classCon.append("\t"+"//LogOperation.write(String.format(\"新增-#-信息【%s-%s】\",,));\n");
                classCon.append("\t"+"//LoggerUtil.writeInfo(String.format(\"用户【%s】新增-#-信息【%s】\",RBACThreadContext.getLoginUser().getLoginName(),));\n");
                classCon.append("\t"+"return "+tableName+";\n");
                classCon.append("}\n\n");


                /**
                 * 编辑
                 */
                classCon.append("@Transactional\n");
                classCon.append("public"+"\tvoid\tedit("+baseName+"Form\t"+lowForm+",BaseResult\tresult){\n");
//                classCon.append("\t"+baseNameBean+"\t"+tableName+"=new\t"+baseNameBean+"();\n");
                classCon.append("\t"+"Optional<"+baseNameBean+">  "+tableName+"Optional =this."+tableName+"Repository.findById("+lowForm+".getId());\n");
                classCon.append("\t"+baseNameBean+" "+tableName+" = null;\n");
                classCon.append("\t"+"if(!"+tableName+"Optional.isPresent()){\n");
                classCon.append("\t"+"\t"+"result.addError(\"找不到-#-信息\");\n");
                classCon.append("\t"+"\t"+"return;\n");
                classCon.append("\t"+"}\n");
                classCon.append("\t"+tableName+" = "+tableName+"Optional.get(); \n");
                classCon.append("\t"+"int id = "+tableName+".getId();\n");
                classCon.append("\t"+"CglibBeanCopierUtils.copyProperties("+lowForm+","+tableName+");\n");
                classCon.append("\t"+""+tableName+".setId(id);\n");
                classCon.append("\t"+"this."+tableName+"Repository"+".save("+tableName+");\n");
                classCon.append("\t"+"//LogOperation.write(String.format(\"编辑-#-信息【%s-%s】\",,));\n");
                classCon.append("\t"+"//LoggerUtil.writeInfo(String.format(\"用户【%s】编辑-#-信息【%s】\",RBACThreadContext.getLoginUser().getLoginName(),));\n");
                classCon.append("}\n\n");


                /**
                 * 删除
                 */
                classCon.append("@Transactional\n");
                classCon.append("public"+"\tvoid\tdelete(int id,BaseResult  result){\n");
                classCon.append("\t"+"Optional<"+baseNameBean+">  "+tableName+"Optional =this."+tableName+"Repository.findById(id);\n");
                classCon.append("\t"+baseNameBean+" "+tableName+" = null;\n");
                classCon.append("\t"+"if(!"+tableName+"Optional.isPresent()){\n");
                classCon.append("\t"+"\t"+"result.addError(\"找不到-#-信息\");\n");
                classCon.append("\t"+"\t"+"return;\n");
                classCon.append("\t"+"}\n");
                classCon.append("\t"+tableName+" = "+tableName+"Optional.get(); \n");
                classCon.append("\t"+"this."+tableName+"Repository"+".delete("+tableName+");\n");
                classCon.append("\t"+"//LogOperation.write(String.format(\"删除-#-信息【%s-%s】\",,));\n");
                classCon.append("\t"+"//LoggerUtil.writeInfo(String.format(\"用户【%s】删除-#-信息【%s】\",RBACThreadContext.getLoginUser().getLoginName(),));\n");
                classCon.append("}\n\n");


                /**
                 * 查询
                 */
                classCon.append("@Transactional\n");
                classCon.append("public"+"\tMap<String, Object>\tlist(PageForm\tpageForm"+"){\n");
                classCon.append("\t"+"Map<String, Object> resultMap = new HashMap();\n");
                classCon.append("\t"+"Map<String, String> paramKeyMap = new HashMap();\n");
                classCon.append("\t"+"return this.page(pageForm, paramKeyMap, this."+StringUtils.toLowerCaseFirstOne(baseName)+"Repository"+", resultMap, new Specification[0]);\n");
                classCon.append("}\n\n");

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
