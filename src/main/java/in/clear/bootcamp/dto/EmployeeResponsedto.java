package in.clear.bootcamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmployeeResponsedto {
    @JsonProperty(value = "userId")
    private String _id;
    private String message;
}
