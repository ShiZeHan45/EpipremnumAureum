package com.gr.imp;

import com.gr.utils.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

/**
 * @description: freemarker逻辑业务类
 * @author: Shizh
 * @create: 2022-03-24 13:38
 */
@Service
public class FreemarkerService {
    @Value("classpath:ftl")
    private Resource ftlResource;

    public void generatorFile(String ftlName, String path, String fileName, Map<String, Object> entityMap) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(ftlResource.getFile());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = cfg.getTemplate(ftlName);
        FileUtil.createDir(path);
        File entityFile = new File(path + "\\" + fileName);
        if(!entityFile.exists()){
            entityFile.createNewFile();
        }
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entityFile)));
        template.process(entityMap, out);
        out.flush();
        out.close();
    }
}