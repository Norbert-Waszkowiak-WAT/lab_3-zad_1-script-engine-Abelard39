package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import pl.edu.wat.knowledge.*;

@Data
public class Book{
    @MongoId
    private String isbn;
    private Integer year;
    private String title;
    private Integer baseScore; 
    @DBRef
    private Author editor;
    private Publisher publisher;
}