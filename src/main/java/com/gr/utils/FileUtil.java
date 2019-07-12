package com.gr.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @program: wwis-kunming
 * @description: 文件读写操作工具类
 * @author: Shizh
 * @create: 2018-07-23 15:07
 **/
public class FileUtil {
    /**
     *创建目录
     * @param dirName 目录的路径
     */
    public static boolean createDir(String dirName){
        File file=new File(dirName);
        if(!file.exists()){
            if(file.mkdirs()){
                return true;
            }
        }
        return false;
    }

    /**
     * 将内容写进文件
     * @param filepath
     * @param content
     * @return
     */
    public static boolean writeFileContent(String filepath,String content){
        Boolean isok = false;
        File file =new File(filepath);
        if(file.exists()){
            try {
//以写入的方式打开文件
                FileWriter fw = new FileWriter(filepath);
//文件内容写入
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);//向文件里面写入内容
                bw.close();
                fw.close();
                isok=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isok;
    }

    /**
     *创建带内容的文件
     * @param fileName 文件名
     */
    public static boolean createFile(String fileName,String content){
        File file=new File(fileName);
        if(!file.exists()){
            try {
//创建文件并写入内容
                file.createNewFile();
                writeFileContent(fileName,content);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 指定路径下创建带内容的文件
     * @param path 路径
     * @param fileName 文件名
     */
    public static boolean createFileAtPath(String path,String fileName,String content){
//路径不存在先创建
        if(createDir(path)==true){
            StringBuffer bf =new StringBuffer(path);
            bf.append(fileName);
            File file = new File(bf.toString());
            if(!file.exists()){
                try {
//创建文件并写入内容
                    file.createNewFile();
                    writeFileContent(bf.toString(),content);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{//路径存在直接创建文件并写入内容
            return createFile(path+"/"+fileName,content);
        }
        return false;
    }

    /**
     * 创建临时文件
     * @param fileName 文件名
     * @return
     */
    public static File createTempFile(String fileName){
//boolean isok = false;
        File file = new File("temp"+fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
//当你退出的时候，就把我这个临时文件删除掉
                file.deleteOnExit();
//isok=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 删除文件
     * @param fileName 文件名
     */
    public static boolean deleteFile(String fileName){
        try {
//直接创建一个文件的操作对象
            File file = new File(fileName);
            if(file.exists()){
                if(file.delete()){
                    return true;
                }
            }
        } catch (Exception e) {
// TODO: handle exception
        }
        return false;
    }
}
