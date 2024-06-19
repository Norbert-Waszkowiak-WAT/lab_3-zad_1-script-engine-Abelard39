package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
public class Article {
    @MongoId
    private String title;
    private Optional<Integer> no;
    private String collection;
    private Integer score;
    private Optional<Integer> articleNo;
    private Integer vol;
    @DBRef
    private Set<Author> authors;
    private Journal journal;
}
