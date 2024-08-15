package org.example;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting UserService tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed UserService tests.");
    }

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Completed a test.");
    }

    @Test
    void testRegisterUser_Success() {
        User user = new User("john_doe", "password123", "john@example.com");
        boolean result = userService.registerUser(user);
        assertTrue(result, "User should be registered successfully.");
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        User user1 = new User("john_doe", "password123", "john@example.com");
        userService.registerUser(user1);

        User user2 = new User("john_doe", "differentPass", "john.doe@example.com");
        boolean result = userService.registerUser(user2);

        assertFalse(result, "User registration should fail when the username is already taken.");
    }

    @Test
    void testRegisterUser_NullUser() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.registerUser(null);
        });
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void testRegisterUser_EmptyUsername() {
        User user = new User("", "password123", "john@example.com");
        boolean result = userService.registerUser(user);
        assertFalse(result, "User registration should fail when the username is empty.");
    }

    @Test
    void testLoginUser_Success() {
        User user = new User("john_doe", "password123", "john@example.com");
        userService.registerUser(user);

        User result = userService.loginUser("john_doe", "password123");

        assertNotNull(result, "User should be able to login successfully.");
    }

    @Test
    void testLoginUser_WrongPassword() {
        User user = new User("john_doe", "password123", "john@example.com");
        userService.registerUser(user);

        User result = userService.loginUser("john_doe", "wrongPassword");

        assertNull(result, "User should not be able to login with wrong password.");
    }

    @Test
    void testLoginUser_UserNotFound() {
        User result = userService.loginUser("non_existent_user", "password123");

        assertNull(result, "Login should fail for a non-existent user.");
    }

    @Test
    void testLoginUser_NullUsername() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.loginUser(null, "password123");
        });
        assertEquals("Username cannot be null", exception.getMessage());
    }

    @Test
    void testLoginUser_NullPassword() {
        User user = new User("john_doe", "password123", "john@example.com");
        userService.registerUser(user);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.loginUser("john_doe", null);
        });
        assertEquals("Password cannot be null", exception.getMessage());
    }
}
