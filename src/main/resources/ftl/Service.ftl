package ${servicePackagePath};

import ${entityPackagePath}.${entityName};
import ${repositoryPackagePath}.${entityName}Repository;

import gddxit.waterhub.cloud.expection.BusinessException;
import	gddxit.waterhub.data.form.PageForm;
import	java.util.HashMap;
import	java.util.Map;
import gddxit.waterhub.cloud.results.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${entityName}Service {
    @Autowired
    private ${entityName}Repository ${entityName?uncap_first}Repository;

    public PageResult<${entityName}> list(PageForm pageForm) {
    Map<String, String> paramKeyMap = new HashMap<>();
    return ${entityName?uncap_first}Repository.page(pageForm, paramKeyMap);
    }

    public ${entityName} get(Long id) {
    return ${entityName?uncap_first}Repository.findById(id).orElseThrow(() -> new BusinessException(String.format("根据id【%s】找不到记录信息", id)));
    }

}