package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import org.springframework.lang.Nullable;
import java.util.List;

@Data
public class Article {
    @MongoId
    private String title;
    private String collection;
    private Integer score;
    private Integer vol;
    private Integer year;

    @Nullable
    private Integer no;
    @Nullable
    private Integer articleNo;

    @DBRef
    private Journal journal;
    @DBRef
    private List<Author> authors;
}