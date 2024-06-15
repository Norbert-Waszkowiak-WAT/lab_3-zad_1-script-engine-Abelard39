package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;
import pl.edu.wat.knowledge.*;

@Data
public class Chapter {
    private String score;
    private String collection;
    @MongoId
    private String title;
    @DBRef
    private Set<Author> authors;
    @DBRef
    private Book book;
}