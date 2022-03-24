package com.gr.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @program: wwis-kunming
 * @description: 获得properties配置文件的参数工具类
 * @author: Shizh
 * @create: 2018-07-23 15:05
 **/
public class ConfigUtil {
    //项目路径的参数
    public static String projectPath;
    //生成Bean实体类的参数
    public static String beanFlag;
    public static String beanPackage;
    //生成Dao接口的参数
    public static String repositoryFlag;
    public static String repositoryPackage;
    //生成ServiceImpl实现类的参数
    public static String serviceImplFlag;
    public static String serviceImplPackage;
    public static String beanHead;
    public static String beanPath;
    public static String formFlag;
    public static String formPackage;
    public static String controllerFlag;
    public static String controllerPackage;
    public static String handlerFlag;
    public static String handlerPackage;
    public static String moudleName;
    public static String dictFlag;
    public static String dictPackage;
    public static String cpiFlag;
    public static String jpapiFlag;
    public static String jpaFlag;
    public static String webFlg;
    public static String tableHead;
    public static String apiDocFlag;
    public static String isTemplate;
    public static String apiDocPath;
    public static String voFlag;
    public static String voPackage;
    public static String voPath;
//    //获取配置文件参数并加载驱动
    static{
        try {
//得到配置文件的流信息
            InputStream in =  new FileInputStream(new ConfigUtil().getClass().getClassLoader().getResource("config.properties").getFile());
//加载properties文件的工具类
            Properties pro = new Properties();
//工具类去解析配置文件的流信息
            pro.load(in);
//将文件得到的信息,赋值到全局变量
            projectPath = pro.getProperty("projectPath");
            beanHead = pro.getProperty("beanHead");
            beanPath = pro.getProperty("beanPath");
            beanFlag =pro.getProperty("beanFlag");
            beanPackage = pro.getProperty("beanPackage");
            repositoryFlag =pro.getProperty("repositoryFlag");
            repositoryPackage = pro.getProperty("repositoryPackage");
            serviceImplFlag=pro.getProperty("serviceImplFlag");
            serviceImplPackage=pro.getProperty("serviceImplPackage");
            formFlag=pro.getProperty("formFlag");
            formPackage=pro.getProperty("formPackage");
            controllerFlag=pro.getProperty("controllerFlag");
            controllerPackage=pro.getProperty("controllerPackage");
            handlerFlag=pro.getProperty("handlerFlag");
            handlerPackage=pro.getProperty("handlerPackage");
            moudleName=pro.getProperty("moudleName");
            dictFlag=pro.getProperty("dictFlag");
            dictPackage=pro.getProperty("dictPackage");
            tableHead = pro.getProperty("tableHead");
            cpiFlag=pro.getProperty("cpiFlag");
            jpapiFlag=pro.getProperty("jpapiFlag");
            jpaFlag=pro.getProperty("jpaFlag");
            webFlg=pro.getProperty("webFlg");
            isTemplate=pro.getProperty("isTemplate");
            apiDocFlag=pro.getProperty("apiDocFlag");
            apiDocPath=pro.getProperty("apiDocPath");
            voPackage=pro.getProperty("voPackage");
            voFlag=pro.getProperty("voFlag");
            voPath=pro.getProperty("voPath");
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(new ConfigUtil().getClass().getClassLoader().getResource("config.properties").getPath());
    }
}
