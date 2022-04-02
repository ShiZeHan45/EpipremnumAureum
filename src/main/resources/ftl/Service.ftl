package ${servicePackagePath};

import import ${entityPackagePath}.${entityName};
import import ${repositoryPackagePath}.${entityName}Repository;

import gddxit.waterhub.cloud.expection.BusinessException;
import gddxit.waterhub.data.service.JPAEntityService;
import	gddxit.waterhub.data.form.PageForm;
import	java.util.HashMap;
import	java.util.Map;
import gddxit.waterhub.cloud.results.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${entityName}Service extends JPAEntityService<${entityName}>{
    @Autowired
    private ${entityName}Repository ${entityName?uncap_first}Repository;

    public PageResult<${entityName}> list(PageForm pageForm) {
    Map<String, String> paramKeyMap = new HashMap<>();
    return page(pageForm, paramKeyMap, ${entityName?uncap_first}Repository);
    }

    private ${entityName} get(Long id) {
    return ${entityName?uncap_first}Repository.findById(id).orElseThrow(() -> new BusinessException(String.format("根据id【%s】找不到记录信息", id)));
    }

}