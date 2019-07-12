package com.szh.generate.generatebeanfileimp;


import com.szh.generate.generatebeanfileutils.ConfigUtil;
import com.szh.generate.generatebeanfileutils.FileUtil;
import com.szh.generate.generatebeanfileutils.StringUtils;

/**
 * @program: wwis-kunming
 * @description: 字典类
 * @author: Shizh
 * @create: 2018-07-25 17:38
 **/
@SuppressWarnings("all")
public class DictAutoDaoImpl  {
    public static boolean createDict(String dicValue,String code,String name) {
        //获得配置文件的参数
//项目路径
        String projectPath = ConfigUtil.projectPath;
//是否生成实体类
        String dictFlag=ConfigUtil.dictFlag;
//Bean实体类的包名
        String dictPackage=ConfigUtil.dictPackage;
        if("true".equals(dictFlag) ){
            String fileName=code;
//将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath=dictPackage.replace(".", "/");
//Bean实体类的路径
            String path =projectPath+"/src/"+beanPath;
            String packageCon ="package"+"\t"+dictPackage+";\n\n";
            StringBuffer importCon=new StringBuffer();
            importCon.append("import com.alibaba.fastjson.JSONException;\n");
            importCon.append("import com.gddxit.dxbase.base.annotation.ApiRelation;\n");
            importCon.append("import com.gddxit.dxbase.base.annotation.RelationType;\n");
            importCon.append("import com.gddxit.dxbase.core.jpa.orm.DictOption;\n");
            importCon.append("import com.gddxit.dxbase.core.mvc.result.JSONObject;\n");
            importCon.append("import javax.persistence.Column;\n");
            importCon.append("import javax.persistence.DiscriminatorColumn;\n");
            importCon.append("import javax.persistence.Entity;\n");
            importCon.append("import javax.persistence.Id;\n");
            importCon.append("import javax.persistence.Table;\n");
            importCon.append("import org.hibernate.annotations.DiscriminatorOptions;\n\n");
            importCon.append("/**\n*auto grnerate all by szh\n*\n*/\n\n");
            StringBuffer classHead=new StringBuffer();
            classHead.append("@Entity\n");
            classHead.append("@Table(name = \"ac_dict_option\""+")\n");
            classHead.append("@DiscriminatorOptions(force = true)\n");
            classHead.append("@DiscriminatorColumn(name = \"dict_type\")\n");
            classHead.append("@DiscriminatorValue(\""+dicValue+"\")\n");
            classHead.append("@ApiRelation(code = \""+StringUtils.toLowerCaseFirstOne(code)+"\", name = \""+name+"\",type=RelationType.DATA)\n");
            String className = "public class "+code+" {\n\n";
            StringBuffer classContent=new StringBuffer();
            classContent.append("public "+code+"(String id) {this.id = id;}\n");
            classContent.append("public "+code+"() {}\n");
            classContent.append("@Id\n");
            classContent.append("@Column(name = \"option_value\")\n");
            classContent.append("private String id;\n");
            classContent.append("@Column(name = \"label\")\n");
            classContent.append("private String name;\n");
            classContent.append("@Column(name = \"show_order\")\n");
            classContent.append("private Integer showOrder;\n");
            classContent.append(" public String getId() {return this.id;}\n");
            classContent.append(" public void setId(String id) {this.id = id;}\n");
            classContent.append(" public String getName() {return this.name;}\n");
            classContent.append(" public void setName(String name) {this.name = name;}\n");
            classContent.append(" public Integer getShowOrder() {return this.showOrder;}\n");
            classContent.append(" public void setShowOrder(Integer showOrder) {this.showOrder = showOrder;}\n");
            classContent.append(" public JSONObject toJSONObject(DictOption dictOption) throws JSONException {\n");
            classContent.append(" return JSONObject.build().put(\"value\", dictOption.getOptionValue()).put(\"label\", dictOption.getLabel());\n");
            classContent.append(" }\n");
            classContent.append(" public String toString() {\n");
            classContent.append("  return \""+code+" [id=\" + this.id + \", name=\" + this.name + \", showOrder=\" + this.showOrder + \"]\";\n");
            classContent.append(" }\n");
            StringBuffer content=new StringBuffer();
            content.append(packageCon);
            content.append(importCon.toString());
            content.append(classHead);
            content.append(className);
            content.append(classContent);
            content.append("}");
            FileUtil.createFileAtPath(path+"/", fileName+".java", content.toString());
            return true;
        }
        return false;
    }
}
