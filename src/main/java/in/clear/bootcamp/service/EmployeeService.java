package in.clear.bootcamp.service;

import in.clear.bootcamp.dto.DepartmentCountdto;
import in.clear.bootcamp.dto.Employee;
import in.clear.bootcamp.dto.EmployeeResponsedto;
import in.clear.bootcamp.model.EmployeeModel;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    EmployeeResponsedto createEmployee(EmployeeModel employee);
    public Employee getEmployee(String id);
    EmployeeResponsedto updateEmployee(String id, Employee employee);
    EmployeeResponsedto deleteEmployee(String id);
    public double getAverageExperience();
    public List<DepartmentCountdto> getEmployeeCountByDepartmentAndDesignation(@Param("designation") String designation);
}
