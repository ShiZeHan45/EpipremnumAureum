package ${controllerPackagePath};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import gddxit.waterhub.data.form.PageForm;
import gddxit.waterhub.cloud.expection.BusinessException;
import gddxit.waterhub.cloud.results.BaseResult;
import gddxit.waterhub.cloud.results.PageResult;
import ${voPackagePath}.${entityName}VO;

@RestController
@RequestMapping({"/${entityName?uncap_first}"})
@Api(tags = "${entityName}")
public class ${entityName}Controller{

    @Autowired
    private ${entityName}Service  ${entityName?uncap_first}Service;


    @PostMapping
    @ApiOperation(value = "列表查询", notes = "根据条件查询")
    public PageResult<${entityName}VO> list(@RequestBody PageForm pageform) {
        return PageResult.build(${entityName?uncap_first}Service.list(pageform),${entityName}VO::new);
    }

    @PostMapping(path = "/get/{id:\\d+}")
    @ApiOperation(value = "详情查询", notes = "根据id查询")
    public BaseResult<${entityName}VO> get(@PathVariable("id") Long id) {
        return new BaseResult(new ${entityName}VO(${entityName?uncap_first}Service.get(id)));
    }

}