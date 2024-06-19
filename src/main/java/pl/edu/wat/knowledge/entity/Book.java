package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.lang.Nullable;

@Data
public class Book extends Entity {

    private String isbn;
    private Integer year;
    private Integer baseScore;
    private String title;

    @DBRef
    private Publisher publisher;

    @DBRef
    @Nullable
    private Author editor;
}