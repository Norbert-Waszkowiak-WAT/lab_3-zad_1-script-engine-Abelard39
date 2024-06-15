package pl.edu.wat.knowledge.service;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.repository.ArticleRepository;
import pl.edu.wat.knowledge.repository.AuthorRepository;
import pl.edu.wat.knowledge.repository.BookRepository;
import pl.edu.wat.knowledge.repository.ChapterRepository;
import pl.edu.wat.knowledge.repository.JournalRepository;
import pl.edu.wat.knowledge.repository.PublisherRepository;
import pl.edu.wat.knowledge.repository.AffiliationRepository;

@Service
@Slf4j
public class ScriptService {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ChapterRepository chapterRepository;
    private final JournalRepository journalRepository;
    private final PublisherRepository publisherRepository;
    private final AffiliationRepository affiliationRepository;

    @Autowired
    public ScriptService(ArticleRepository articleRepository, AuthorRepository authorRepository, 
                         BookRepository bookRepository, ChapterRepository chapterRepository, 
                         JournalRepository journalRepository, PublisherRepository publisherRepository, 
                         AffiliationRepository affiliationRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.chapterRepository = chapterRepository;
        this.journalRepository = journalRepository;
        this.publisherRepository = publisherRepository;
        this.affiliationRepository = affiliationRepository;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("articleRepository", articleRepository);
            bindings.putMember("authorRepository", authorRepository);
            bindings.putMember("bookRepository", bookRepository);
            bindings.putMember("chapterRepository", chapterRepository);
            bindings.putMember("journalRepository", journalRepository);
            bindings.putMember("publisherRepository", publisherRepository);
            bindings.putMember("affiliationRepository", affiliationRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing script", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }
}
