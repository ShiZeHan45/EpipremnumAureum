package com.gr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 配置信息
 * @author: Shizh
 * @create: 2022-03-24 10:42
 */
@Component
public class Config {
    @Value("${projectPath}")
    public String projectPath;
    @Value("${entityPackagePath}")
    public String entityPackagePath;
    @Value("${tableHead}")
    public String tableHead;
    @Value("${VOPackagePath}")
    public String VOPackagePath;
    @Value("${repositoryPackagePath}")
    public String repositoryPackagePath;
    @Value("${servicePackagePath}")
    public String servicePackagePath;
    @Value("${controllerPackagePath}")
    public String controllerPackagePath;
    @Value("${formPackagePath}")
    public String formPackagePath;

    public String getFormPackagePath() {
        return formPackagePath;
    }

    public String getRepositoryPackagePath() {
        return repositoryPackagePath;
    }

    public String getServicePackagePath() {
        return servicePackagePath;
    }

    public String getControllerPackagePath() {
        return controllerPackagePath;
    }

    public String getVOPackagePath() {
        return VOPackagePath;
    }

    public String getTableHead() {
        return tableHead;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public String getEntityPackagePath() {
        return entityPackagePath;
    }
}