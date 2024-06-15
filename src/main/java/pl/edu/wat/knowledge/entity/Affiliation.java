package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import pl.edu.wat.knowledge.entity.Entity;

@Data
public class Affiliation{
    @MongoId
    private String name;
    @DBRef
    private Affiliation parent;
}