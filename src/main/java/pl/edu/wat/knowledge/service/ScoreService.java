package pl.edu.wat.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.entity.Article;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.repository.ArticleRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    
    @Autowired
    private ArticleRepository articleRepository;

    public Integer getScore(Author author, Integer year) {
        List<Article> articles = articleRepository.findByAuthorsAndYear(author, year);

        int totalScore = articles.stream()
                .map(article -> article.getScore() / article.getAuthors().size())
                .sorted(Comparator.reverseOrder())
                .limit(4)
                .mapToInt(Integer::intValue)
                .sum();

        return totalScore;
    }
}
