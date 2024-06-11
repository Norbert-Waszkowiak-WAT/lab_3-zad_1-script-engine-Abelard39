package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
public class Chapter {
    @MongoId
    private String score;
    private String collection;
    private String title;
    @DBRef
    private Set<Author> authors;
    private Book book;
}