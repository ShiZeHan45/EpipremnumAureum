package com.gr.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @program: wwis-kunming
 * @description: 获得数据源工具类
 * @author: Shizh
 * @create: 2018-07-23 15:02
 **/
public class DataSourceUtil {
    //链接数据库的参数
    public static String driver;
    public static String url;
    public static String userName;
    public static String userPs;
    //操作数据库的对象
    public Connection con;
    public Statement sta;
    public ResultSet rs;

    //获取配置文件参数并加载驱动
    static{
        try {
//得到配置文件的流信息
            InputStream in = new FileInputStream(new File("D:/config.properties"));
//加载properties文件的工具类
            Properties pro = new Properties();
//工具类去解析配置文件的流信息
            pro.load(in);
//将文件得到的信息,赋值到全局变量
            driver = pro.getProperty("jdbc.driverClass");
            url = pro.getProperty("jdbc.url");
            userName = pro.getProperty("jdbc.username");
            userPs = pro.getProperty("jdbc.password");
//加载驱动
            Class.forName(driver);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得链接
     */
    private void getConnection(){
        try {
            con=DriverManager.getConnection(url,userName,userPs);
        } catch (SQLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 创建一个状态通道
     */
    private void createStatement(){
//获得链接的方法
        this.getConnection();
        try {
            sta =con.createStatement();
        } catch (SQLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 基于状态通道的 查询方法
     * @param sql
     * @return
     */
    public ResultSet query(String sql){
        this.createStatement();
        try {
            rs = sta.executeQuery(sql);
        } catch (SQLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 关闭资源方法
     */
    public void closeRes(){
        if(rs !=null){
            try {
                rs.close();
            } catch (SQLException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(sta != null){
            try {
                sta.close();
            } catch (SQLException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
