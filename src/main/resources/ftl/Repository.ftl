package ${repositoryPackagePath};

import ${entityPackagePath}.${entityName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ${entityName}Repository extends JpaRepository<${entityName}, Long>, JpaSpecificationExecutor<${entityName}>{


}