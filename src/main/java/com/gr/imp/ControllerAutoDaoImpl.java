package com.gr.imp;


import com.gr.utils.*;
import com.gr.in.ControllerAutoDao;
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
public class ControllerAutoDaoImpl implements ControllerAutoDao {
    //从GetTablesDaoImpl中获得装有所有表结构的List
    Map<String,List> maps = DbInfoUtil.getTablesInfo();

    //通过表名、字段名称、字段类型创建Bean实体
    @Override
    public boolean createController() {
//获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String controllerFlag=ConfigUtil.controllerFlag;
//Bean实体类的包名
        String controllerPackage=ConfigUtil.controllerPackage;
//判断是否生成实体类
        if("true".equals(controllerFlag) ){
//将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath=controllerPackage.replace(".", "/");
//Bean实体类的路径
            String path =projectPath+"/src/"+beanPath;
//遍历装有所有表结构的List
            Iterator<Map.Entry<String, List>> it = maps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List> entry = it.next();
                String  baseName = NameUtil.fileName(entry.getKey());
                String fileName=baseName+"Controller";
                String upBeanName = baseName;
                String lowBeanName = StringUtils.toLowerCaseFirstOne(baseName);
                String baseForm = baseName+"Form";
                String lowForm = lowBeanName+"Form";
                String baseService = baseName+"Service";
                String lowService = lowBeanName+"Service";
//文件名

//(实体类）文件内容
                String packageCon ="package"+"\t"+controllerPackage+";\n\n";
                StringBuffer importCon=new StringBuffer();
                importCon.append("import"+"\t"+"com.gddxit.dxbase.base.annotation.ApiAction;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.base.annotation.ApiController;\n");
                importCon.append("import"+"\t"+ConfigUtil.formPackage+"."+upBeanName+"Form;\n");
                importCon.append("import"+"\t"+ConfigUtil.serviceImplPackage+"."+baseService+";\n");
                importCon.append("import"+"\t"+ConfigUtil.handlerPackage+"."+upBeanName+"Handler;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.jpa.service.PageForm;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.mvc.AbstractAnnotationController;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.mvc.result.AddResult;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.mvc.result.BaseResult;\n");
                importCon.append("import"+"\t"+"com.gddxit.dxbase.core.mvc.result.PageResult;\n");
                importCon.append("import"+"\t"+"javax.servlet.http.HttpServletRequest;\n");
                importCon.append("import"+"\t"+"javax.servlet.http.HttpServletResponse;\n");
                importCon.append("import"+"\t"+"org.springframework.beans.factory.annotation.Autowired;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.DeleteMapping;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.GetMapping;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.PatchMapping;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.PathVariable;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.PostMapping;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.RequestBody;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.RequestMapping;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.ResponseBody;\n");
                importCon.append("import"+"\t"+"org.springframework.web.bind.annotation.RestController;\n");
                importCon.append("import"+"\t"+"java.util.List;\n");
                importCon.append("import"+"\t"+"java.util.Map;\n\n");
                importCon.append("/**\n*auto generate all by szh\n*\n*/\n\n");
                StringBuffer classHead =new StringBuffer();
                classHead.append("@RestController\n");
                classHead.append("@ApiController(\""+ConfigUtil.moudleName+"模块\")\n");
                classHead.append("@RequestMapping({\"/"+lowBeanName+"\"})\n");
                String className ="public"+"\t"+"class"+"\t"+fileName+" extends"+"\t"+"AbstractAnnotationController{\n\n";
//拼接(实体类）文件内容
                StringBuffer classCon =new StringBuffer();
                classCon.append("@Autowired\n");
                classCon.append("private"+"\t"+baseService+"\t"+lowService+";\n\n");

                /**
                 * 查询
                 */
                classCon.append("@ApiAction(name = \""+ConfigUtil.moudleName+"列表\")\n");
                classCon.append("@GetMapping\n");
                classCon.append("@ResponseBody\n");
                classCon.append("public PageResult list(PageForm pageForm) {\n");
                classCon.append("\t"+"Map<String, Object> data = this."+lowService+".list(pageForm);\n");
                classCon.append("\t"+"return (new PageResult(data)).setData((new "+upBeanName+"Handler()).page(data));\n");
                classCon.append("}\n\n");
                /**
                 * 保存
                 */
                classCon.append("@ApiAction(name = \"保存"+ConfigUtil.moudleName+"\")\n");
                classCon.append("@PostMapping\n");
                classCon.append("public BaseResult save(@RequestBody @Validated "+baseForm+" form, BindingResult errForm, HttpServletRequest request, HttpServletResponse response) {\n");
                classCon.append("\t"+"AddResult result = new AddResult();\n");
                classCon.append("\t"+" if (errForm.hasErrors()) {\n");
                classCon.append("\t\t"+"for (ObjectError error : errForm.getAllErrors()) {\n");
                classCon.append("\t\t\t"+" result.addError(error.getDefaultMessage());\n");
                classCon.append("\t\t\t"+"return result;\n");
                classCon.append("\t\t"+"}\n");
                classCon.append("\t"+"}\n");
                classCon.append("\t"+"this."+lowService+".save(form, result);\n");
                classCon.append("\t"+"response.setHeader(\"Location\", request.getRequestURL().toString() + \"/\" + result.getData().getId());\n");
                classCon.append("\t"+"response.setStatus(201);\n");
                classCon.append("\t"+" return result;\n");
                classCon.append("}\n\n");
//                /**
//                 * 编辑
//                 */
                classCon.append("@ApiAction(name = \"编辑"+ConfigUtil.moudleName+"\")\n");
                classCon.append("@PatchMapping(path = {\"/{id:"+"\\"+"\\"+"d+}\"})\n");
                classCon.append("  public BaseResult edit(@PathVariable(\"id\") int id, @RequestBody @Validated "+baseForm+" form, BindingResult errForm,  HttpServletRequest request, HttpServletResponse response) {\n");
                classCon.append("\t"+" BaseResult result = new BaseResult();\n");
                classCon.append("\t"+" if (errForm.hasErrors()) {\n");
                classCon.append("\t\t"+"for (ObjectError error : errForm.getAllErrors()) {\n");
                classCon.append("\t\t\t"+" result.addError(error.getDefaultMessage());\n");
                classCon.append("\t\t\t"+"return result;\n");
                classCon.append("\t\t"+"}\n");
                classCon.append("\t"+"}\n");
                classCon.append("\t"+"form.setId(id);\n");
                classCon.append("\t"+" this."+lowService+".edit(form, result);\n");
                classCon.append("\t"+" response.setHeader(\"Location\", request.getRequestURL().toString());\n");
                classCon.append("\t"+"  response.setStatus(201);\n");
                classCon.append("\t"+" return result;\n");
                classCon.append("}\n\n");

                /**
                 * 删除
                 */
                classCon.append("@ApiAction(name = \"删除"+ConfigUtil.moudleName+"\")\n");
                classCon.append("@DeleteMapping(path = {\"/{id:"+"\\"+"\\"+"d+}\"})\n");
                classCon.append("public BaseResult del(@PathVariable(\"id\") int id) {\n");
                classCon.append("\t"+"BaseResult result = new BaseResult();\n");
                classCon.append("\t"+"this."+lowService+".delete(id, result);\n");
                classCon.append("\t"+"return result;\n");
                classCon.append("}\n\n");
///**
// //                 * 编辑
// //                 */
//                classCon.append("@ApiAction(name = \"编辑"+ConfigUtil.moudleName+"\")\n");
//                classCon.append("@PatchMapping(path = {\"/edit/{id:"+"\\"+"\\"+"d+}\"})\n");
//                classCon.append("  public BaseResult edit(@PathVariable(\"id\") int id, @RequestBody "+baseForm+" form, HttpServletRequest request, HttpServletResponse response) {\n");
//                classCon.append("\t"+" BaseResult result = new BaseResult();\n");
//                classCon.append("\t"+"form.setId(id);\n");
//                classCon.append("\t"+" this."+lowService+".edit(form, result);\n");
//                classCon.append("\t"+" response.setHeader(\"Location\", request.getRequestURL().toString());\n");
//                classCon.append("\t"+"  response.setStatus(201);\n");
//                classCon.append("\t"+" return result;\n");
//                classCon.append("}\n\n");
//
//                /**
//                 * 删除
//                 */
//                classCon.append("@ApiAction(name = \"删除"+ConfigUtil.moudleName+"\")\n");
//                classCon.append("@PatchMapping(path = {\"/del/{id:"+"\\"+"\\"+"d+}\"})\n");
//                classCon.append("public BaseResult del(@PathVariable(\"id\") int id, @RequestBody "+baseForm+" form, HttpServletRequest request, HttpServletResponse response) {\n");
//                classCon.append("\t"+"BaseResult result = new BaseResult();\n");
//                classCon.append("\t"+"this."+lowService+".delete(id,form, result);\n");
//                classCon.append("\t"+"return result;\n");
//                classCon.append("}\n\n");

                StringBuffer content=new StringBuffer();
                content.append(packageCon);
                content.append(importCon.toString());
                content.append(classHead);
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
