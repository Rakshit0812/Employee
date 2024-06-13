package in.clear.bootcamp.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.UUID;

@Data
@Document
public class EmployeeModel {
    @Id
    @Field("id")
    private String userId;
    private String name;
    private String email;
    private String department;
    private String designation;
    private Date joinDate;
    public EmployeeModel(){
        this.userId= UUID.randomUUID().toString();
    }
}
