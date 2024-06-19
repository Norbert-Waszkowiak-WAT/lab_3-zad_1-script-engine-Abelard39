package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
public class Book{
    @MongoId
    private String isbn;
    private Integer year;
    private String title;
    private Integer baseScore; 
    @DBRef
    private Optional<Author> editor;
    private Publisher publisher;
}