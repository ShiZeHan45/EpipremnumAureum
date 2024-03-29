package ${formPackagePath};

import java.util.*;
import java.math.*;
import java.time.*;
import org.springframework.beans.BeanUtils;
import io.swagger.annotations.ApiModelProperty;
import ${entityPackagePath}.${entityName};

public class ${entityName}Form{

<#list params as param>
    /**
    * ${param.remark}
    */
    @ApiModelProperty(notes = "${param.remark}")
    private ${param.fieldType?cap_first} ${param.fieldName};

</#list>

<#list params as param>
    public void set${param.fieldName?cap_first}(${param.fieldType} ${param.fieldName}){
    this.${param.fieldName} = ${param.fieldName};
    }

    public ${param.fieldType} get${param.fieldName?cap_first}(){
    return this.${param.fieldName};
    }

</#list>
}