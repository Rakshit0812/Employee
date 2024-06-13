package in.clear.bootcamp.service.impl;

import in.clear.bootcamp.dto.DepartmentCountdto;
import in.clear.bootcamp.dto.Employee;
import in.clear.bootcamp.dto.EmployeeResponsedto;
import in.clear.bootcamp.model.EmployeeModel;
import in.clear.bootcamp.repository.EmployeeCustomRepository;
import in.clear.bootcamp.repository.EmployeeRepository;
import in.clear.bootcamp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    //private final EmployeeCustomRepository employeeCustomRepository;

    @Override
    public EmployeeResponsedto createEmployee(EmployeeModel employee) {

                /*Employee.builder()
                .id(UUID.randomUUID().toString())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .joinDate(employee.getJoinDate())
                .build();*/

        employeeRepository.insert(employee);

        EmployeeResponsedto response = new EmployeeResponsedto();
        response.set_id(employee.getUserId());
        response.setMessage("Employee created successfully");

        return response;
    }

    @Override
    public Employee getEmployee(String userId){
        EmployeeModel employeeModel = employeeRepository.findByuserId(userId);
        Employee employee = new Employee();
        employee.setDepartment(employeeModel.getDepartment());
        employee.setName(employeeModel.getName());
        employee.setDesignation(employeeModel.getDesignation());
        employee.setEmail(employee.getEmail());
        employee.setJoinDate(employeeModel.getJoinDate());
        return employee;
    }

    @Override
    public EmployeeResponsedto updateEmployee(String id, Employee employee) {
        EmployeeModel employeeModel = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeModel.setName(employee.getName());
        employeeModel.setEmail(employee.getEmail());
        employeeModel.setDepartment(employee.getDepartment());
        employeeModel.setDesignation(employee.getDesignation());

        employeeRepository.save(employeeModel);

        EmployeeResponsedto response = new EmployeeResponsedto();
        response.setMessage("Employee updated successfully");

        return response;
    }
    @Override
    public EmployeeResponsedto deleteEmployee(String id) {
        EmployeeModel employeeModel = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepository.delete(employeeModel);

        EmployeeResponsedto response = new EmployeeResponsedto();
        response.setMessage("Employee deleted successfully");

        return response;
    }

    @Override
    public double getAverageExperience() {
        return employeeRepository.getAverageExperience().orElse(0.0);
    }

    @Override
    public List<DepartmentCountdto> getEmployeeCountByDepartmentAndDesignation(@Param("designation") String designation){
        return employeeRepository.getEmployeeCountByDepartmentAndDesignation(designation);
    }
}
