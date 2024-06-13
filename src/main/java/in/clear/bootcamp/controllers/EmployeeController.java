package in.clear.bootcamp.controllers;

import in.clear.bootcamp.dto.DepartmentCountdto;
import in.clear.bootcamp.dto.Employee;
import in.clear.bootcamp.dto.EmployeeResponsedto;
import in.clear.bootcamp.model.EmployeeModel;
import in.clear.bootcamp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping(path = "/api/employees")
    public EmployeeResponsedto createEmployee(@RequestBody EmployeeModel employeeDto) {
        return employeeService.createEmployee(employeeDto);
    }

    @GetMapping(path = "/api/employees/{id}")
    public Employee getEmployee(@PathVariable String id) {
        return employeeService.getEmployee(id);
    }

    @PutMapping(path = "/api/employees/{id}")
    public EmployeeResponsedto updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }
    @DeleteMapping(path = "/api/employees/{id}")
    public EmployeeResponsedto deleteEmployee(@PathVariable String id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/api/employees/average-experience")
    public Map<String, Double> getAverageExperience() {
        double averageExperience = employeeService.getAverageExperience();
        return Map.of("averageExperience", averageExperience);
    }

    @GetMapping("/api/employees/department-count")
    public ResponseEntity<List<DepartmentCountdto>> getEmployeeCountByDepartmentAndDesignation(
            @RequestParam(name = "designation", required = false) String designation) {
        List<DepartmentCountdto> departmentCounts = employeeService.getEmployeeCountByDepartmentAndDesignation(designation);
        return ResponseEntity.ok(departmentCounts);
    }
}
