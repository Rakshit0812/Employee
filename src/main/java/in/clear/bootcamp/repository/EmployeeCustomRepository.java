package in.clear.bootcamp.repository;

import in.clear.bootcamp.dto.DepartmentCountdto;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeCustomRepository {
    Optional<Double> getAverageExperience();

    //@Aggregation("{ $group: { _id: { department: \"$department\", designation: \"$designation\" }, employeeCount: { $sum: 1 } } }")
    List<DepartmentCountdto> getEmployeeCountByDepartmentAndDesignation(@Param("designation") String designation);
}
