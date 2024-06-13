package in.clear.bootcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentCountdto {
    private String department;
    private String designation;
    private long employeeCount;
}
