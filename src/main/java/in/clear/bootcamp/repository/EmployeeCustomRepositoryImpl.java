package in.clear.bootcamp.repository;

import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import in.clear.bootcamp.dto.DepartmentCountdto;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@RequiredArgsConstructor
public class EmployeeCustomRepositoryImpl implements EmployeeCustomRepository {

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Double> getAverageExperience(){
        /*LocalDate now = LocalDate.now();

        Document currentDate = new Document("$dateFromString", new Document("dateString", now.toString()));

        Document projection = new Document("$project", new Document("yearsOfExperience",
                new Document("$divide", Arrays.asList(
                        new Document("$subtract", Arrays.asList(currentDate, "$joinDate")),
                        1000 * 60 * 60 * 24 * 365.25
                ))
        ));

        Document group = new Document("$group", new Document("_id", null)
                .append("averageExperience", new Document("$avg", "$yearsOfExperience")));

        Document result = mongoTemplate.getCollection("employeeModel")
                .aggregate(Arrays.asList(projection, group))
                .first();

        if (result != null) {
            return Optional.ofNullable(result.getDouble("averageExperience"));
        } else {
            return Optional.empty();
        }*/

        LocalDate now = LocalDate.now();
        long nowMillis = java.sql.Date.valueOf(now).getTime();

        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("{$divide: [{$subtract: [" + nowMillis + ", {$toLong: '$joinDate'}]}, " + (1000 * 60 * 60 * 24 * 365.25) + "]}")
                .as("yearsOfExperience");

        GroupOperation groupOperation = Aggregation.group()
                .avg("yearsOfExperience")
                .as("averageExperience");

        Aggregation aggregation = Aggregation.newAggregation(projectionOperation, groupOperation);

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "employeeModel", Document.class);

        Document result = results.getUniqueMappedResult();

        if (result != null) {
            return Optional.ofNullable(result.getDouble("averageExperience"));
        } else {
            return Optional.empty();
        }
    }

    public List<DepartmentCountdto> getEmployeeCountByDepartmentAndDesignation
        (@Param("designation") String designation){
            List<DepartmentCountdto> departmentCounts = new ArrayList<>();
            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
                MongoDatabase database = mongoClient.getDatabase("employee");
                MongoCollection<Document> collection = database.getCollection("employeeModel");

                List<Bson> pipeline = new ArrayList<>();
                pipeline.add(Aggregates.group(
                        new Document("department", "$department").append("designation", "$designation"),
                        Accumulators.sum("employeeCount", 1)
                ));

                if (designation != null) {
                    pipeline.add(Aggregates.match(new Document("designation", designation)));
                }

                List<Document> results = collection.aggregate(pipeline).into(new ArrayList<>());

                for (Document doc : results) {
                    DepartmentCountdto dto = new DepartmentCountdto();
                    Document id = doc.get("_id", Document.class);
                    dto.setDepartment(id.getString("department"));
                    dto.setDesignation(id.getString("designation"));
                    dto.setEmployeeCount(doc.getInteger("employeeCount"));
                    departmentCounts.add(dto);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
            return departmentCounts;
    }
}
