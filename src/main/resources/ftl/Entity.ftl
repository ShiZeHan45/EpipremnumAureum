package ${entityPackagePath};

import gddxit.waterhub.data.common.entitylistener.BaseEntity;
import gddxit.waterhub.data.common.entitylistener.BaseEntityListener;
import gddxit.waterhub.data.common.tenant.MultiTenantSupport;
import java.util.*;
import java.math.*;
import java.time.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.*;

@Table(name="${tableName}")
@Entity
@EntityListeners(BaseEntityListener.class)
public class ${entityName} extends MultiTenantSupport implements BaseEntity {

<#list params as param>
    /**
    * ${param.remark}
    */
    <#if param.fieldName=="id">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    private ${param.fieldType?cap_first} ${param.fieldName};

</#list>
<#list params as param>
    public void set${param.fieldName?cap_first}(${param.fieldType} ${param.fieldName}){
    this.${param.fieldName} = ${param.fieldName};
    }

    public ${param.fieldType} get${param.fieldName?cap_first}(){
    return this.${param.fieldName};
    }
}
</#list>