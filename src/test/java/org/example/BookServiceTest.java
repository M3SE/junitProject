package org.example;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BookServiceTest {

    private BookService bookService;
    private User user;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting BookService tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed BookService tests.");
    }

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        user = new User("john_doe", "password123", "john@example.com");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Completed a test.");
    }

    @Test
    void testSearchBook_Success() {
        Book book1 = new Book("Effective Java", "Joshua Bloch", "Programming", 45.00);
        bookService.addBook(book1);

        List<Book> results = bookService.searchBook("Java");

        assertEquals(1, results.size(), "Should return one book with 'Java' in title.");
        assertTrue(results.contains(book1), "Results should contain 'Effective Java'.");
    }

    @Test
    void testSearchBook_NoResults() {
        List<Book> results = bookService.searchBook("Nonexistent");

        assertTrue(results.isEmpty(), "Should return no books for nonexistent keyword.");
    }

    @Test
    void testSearchBook_EdgeCase_EmptyKeyword() {
        Book book = new Book("Refactoring", "Martin Fowler", "Programming", 40.00);
        bookService.addBook(book);

        List<Book> results = bookService.searchBook("");

        assertEquals(1, results.size(), "Should return all books for an empty keyword.");
    }

    @Test
    void testPurchaseBook_Success() {
        Book book = new Book("The Pragmatic Programmer", "Andy Hunt", "Programming", 42.00);
        bookService.addBook(book);

        boolean result = bookService.purchaseBook(user, book);

        assertTrue(result, "Book purchase should be successful if the book exists.");
    }

    @Test
    void testPurchaseBook_BookNotInDatabase() {
        Book book = new Book("The Mythical Man-Month", "Frederick P. Brooks", "Software Engineering", 39.99);

        boolean result = bookService.purchaseBook(user, book);

        assertFalse(result, "Book purchase should fail if the book does not exist.");
    }

    @Test
    void testPurchaseBook_NullBook() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            bookService.purchaseBook(user, null);
        });
        assertEquals("Book cannot be null", exception.getMessage());
    }
}
