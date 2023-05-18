package ${repositoryPackagePath};

import ${entityPackagePath}.${entityName};
import gddxit.waterhub.data.repository.ComplexRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ${entityName}Repository extends ComplexRepository<${entityName}, Long>{


}