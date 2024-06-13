package in.clear.bootcamp.repository;

import in.clear.bootcamp.dto.DepartmentCountdto;
import in.clear.bootcamp.model.EmployeeModel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<EmployeeModel,String>, EmployeeCustomRepository {

    EmployeeModel findByuserId(String userId);

}
