package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Data
public class Journal {
    private Integer baseScore;
    private String title;
    @MongoId
    private String issn;

    @DBRef
    private Publisher publisher;
}