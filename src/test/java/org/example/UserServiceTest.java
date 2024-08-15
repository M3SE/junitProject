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

    // Tests for registerUser

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
    void testRegisterUser_EmptyUsername() {
        User user = new User("", "password123", "john@example.com");
        boolean result = userService.registerUser(user);
        assertFalse(result, "User registration should fail when the username is empty.");
    }

    // Tests for loginUser

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

        assertNull(result, "User should not be able to login with the wrong password.");
    }

    @Test
    void testLoginUser_NullUsername() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.loginUser(null, "password123");
        });
        assertEquals("Username cannot be null", exception.getMessage());
    }

    // Tests for updateUserProfile

    @Test
    void testUpdateUserProfile_Success() {
        User user = new User("john_doe", "password123", "john@example.com");
        userService.registerUser(user);

        boolean result = userService.updateUserProfile(user, "new_username", "new_password", "new_email@example.com");

        assertTrue(result, "User profile should be updated successfully.");
    }

    @Test
    void testUpdateUserProfile_UsernameAlreadyExists() {
        User user1 = new User("john_doe", "password123", "john@example.com");
        User user2 = new User("existing_user", "password456", "existing@example.com");
        userService.registerUser(user1);
        userService.registerUser(user2);

        boolean result = userService.updateUserProfile(user1, "existing_user", "new_password", "new_email@example.com");

        assertFalse(result, "User profile update should fail if the new username already exists.");
    }

    @Test
    void testUpdateUserProfile_EmptyNewUsername() {
        User user = new User("john_doe", "password123", "john@example.com");
        userService.registerUser(user);

        boolean result = userService.updateUserProfile(user, "", "new_password", "new_email@example.com");

        assertFalse(result, "User profile update should fail if the new username is empty.");
    }
}
