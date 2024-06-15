package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;
import pl.edu.wat.knowledge.*;

@Data
public class Article {
    @MongoId
    private String title;
    private Integer no;
    private String collection;
    private Integer score;
    private Integer articleNo;
    private Integer vol;
    @DBRef
    private Set<Author> authors;
    private Journal journal;
}
