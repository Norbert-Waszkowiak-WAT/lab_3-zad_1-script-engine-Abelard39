package pl.edu.wat.knowledge.service;


import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.repository.*;

@Service
@Slf4j
public class ScriptService {
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final AffiliationRepository affiliationRepository;
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final ChapterRepository chapterRepository;
    private final JournalRepository journalRepository;

    @Autowired
    public ScriptService(ArticleRepository articleRepository,
                        AuthorRepository authorRepository,
                        AffiliationRepository affiliationRepository,
                        PublisherRepository publisherRepository,
                        BookRepository bookRepository,
                        ChapterRepository chapterRepository,
                        JournalRepository journalRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.affiliationRepository = affiliationRepository;
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
        this.chapterRepository = chapterRepository;
        this.journalRepository = journalRepository;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("articleRepository", articleRepository);
            bindings.putMember("authorRepository", authorRepository);
            bindings.putMember("affiliationRepository", affiliationRepository);
            bindings.putMember("publisherRepository", publisherRepository);
            bindings.putMember("bookRepository", bookRepository);
            bindings.putMember("chapterRepository", chapterRepository);
            bindings.putMember("journalRepository", journalRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }
}
