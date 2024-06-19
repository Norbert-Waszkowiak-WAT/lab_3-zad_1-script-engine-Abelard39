package pl.edu.wat.knowledge;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.edu.wat.knowledge.entity.*;
import pl.edu.wat.knowledge.repository.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
@Testcontainers
public abstract class AbstractContainerBaseTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.6");

    @Autowired
    protected AffiliationRepository affiliationRepository;

    @Autowired
    protected AuthorRepository authorRepository;

    @Autowired
    protected PublisherRepository publisherRepository;

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected ChapterRepository chapterRepository;

    @Autowired
    protected JournalRepository journalRepository;

    @Autowired
    protected ArticleRepository articleRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private static final Random RANDOM = new Random();
    private static int counter = 100;

    private String randomString(int length) {
        return RANDOM.ints('A', 'z' + 1)
            .filter(i -> (i <= 'Z' || i >= 'a') && (i <= '9' || i >= '0'))
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    private Instant currentDatetime() {
        return Instant.now();
    }

    private String generateId(String entityType) {
        return entityType + (counter++);
    }

    private Affiliation addAffiliation() {
        Affiliation affiliation = new Affiliation();
        affiliation.setId(generateId("affiliation"));
        affiliation.setCreateDate(currentDatetime());
        affiliation.setName(randomString(10));
        affiliation.setParent(null); // Assuming there is no parent affiliation initially
        affiliationRepository.save(affiliation);
        return affiliation;
    }

    private Author addAuthor(Affiliation affiliation) {
        Author author = new Author();
        author.setId(generateId("author"));
        author.setCreateDate(currentDatetime());
        author.setName(randomString(10));
        author.setSurname(randomString(10));
        author.setAffiliation(affiliation);
        authorRepository.save(author);
        return author;
    }

    private Publisher addPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId(generateId("publisher"));
        publisher.setCreateDate(currentDatetime());
        publisher.setName(randomString(10));
        publisher.setLocation(randomString(10));
        publisherRepository.save(publisher);
        return publisher;
    }

    private Book addBook(Publisher publisher, Author editor) {
        Book book = new Book();
        book.setId(generateId("book"));
        book.setCreateDate(currentDatetime());
        book.setIsbn(String.valueOf(RANDOM.nextInt(900000000) + 1000000000));
        book.setYear(RANDOM.nextInt(124) + 1900);
        book.setPublisher(publisher);
        book.setBaseScore(RANDOM.nextInt(100) + 1);
        book.setTitle(randomString(10));
        book.setEditor(editor);
        bookRepository.save(book);
        return book;
    }

    private Chapter addChapter(Book book, List<Author> authors) {
        Chapter chapter = new Chapter();
        chapter.setId(generateId("chapter"));
        chapter.setCreateDate(currentDatetime());
        chapter.setAuthors(authors);
        chapter.setScore(RANDOM.nextInt(100) + 1);
        chapter.setCollection(randomString(10));
        chapter.setTitle(randomString(10));
        chapter.setBook(book);
        chapterRepository.save(chapter);
        return chapter;
    }

    private Journal addJournal(Publisher publisher) {
        Journal journal = new Journal();
        journal.setId(generateId("journal"));
        journal.setCreateDate(currentDatetime());
        journal.setBaseScore(RANDOM.nextInt(100) + 1);
        journal.setTitle(randomString(10));
        journal.setPublisher(publisher);
        journal.setIssn(String.valueOf(RANDOM.nextInt(9000) + 1000));
        journalRepository.save(journal);
        return journal;
    }

    private Article addArticle(Journal journal, List<Author> authors) {
        Article article = new Article();
        article.setId(generateId("article"));
        article.setCreateDate(currentDatetime());
        article.setTitle(randomString(10));
        article.setNo(RANDOM.nextInt(20) + 1);
        article.setCollection(randomString(10));
        article.setScore(RANDOM.nextInt(100) + 1);
        article.setArticleNo(RANDOM.nextInt(100) + 1);
        article.setAuthors(authors);
        article.setVol(RANDOM.nextInt(10) + 1);
        article.setJournal(journal);
        articleRepository.save(article);
        return article;
    }

    @BeforeEach
    public void setUpDatabase() {
        List<Affiliation> affiliations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            affiliations.add(addAffiliation());
        }

        List<Publisher> publishers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            publishers.add(addPublisher());
        }

        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            authors.add(addAuthor(affiliations.get(RANDOM.nextInt(affiliations.size()))));
        }

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            books.add(addBook(publishers.get(RANDOM.nextInt(publishers.size())), authors.get(RANDOM.nextInt(authors.size()))));
        }

        for (int i = 0; i < 5; i++) {
            addChapter(books.get(RANDOM.nextInt(books.size())), List.of(authors.get(RANDOM.nextInt(authors.size())), authors.get(RANDOM.nextInt(authors.size()))));
        }

        List<Journal> journals = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            journals.add(addJournal(publishers.get(RANDOM.nextInt(publishers.size()))));
        }

        for (int i = 0; i < 5; i++) {
            addArticle(journals.get(RANDOM.nextInt(journals.size())), List.of(authors.get(RANDOM.nextInt(authors.size())), authors.get(RANDOM.nextInt(authors.size()))));
        }

        System.out.println("Data population complete.");
    }
}
