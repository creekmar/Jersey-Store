package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Jersey;
import com.estore.api.estoreapi.model.JerseyTest;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.Jersey.Size;
import com.fasterxml.jackson.databind.ObjectMapper;

@Tag("Persistence-tier")
public class UserFileDAOTest {
    
    UserFileDAO userFileDAO;
    User[] testUsers;
    Jersey[] testJerseys;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        //jerseys
        testJerseys = new Jersey[5];
        testJerseys[0] = new Jersey(0, "Matt", 39.99f, Size.SMALL, false, 16, 0);
        testJerseys[1] = new Jersey(1, "Dave", 39.99f, Size.MEDIUM, true, 3, 0);
        testJerseys[2] = new Jersey(2, "Sidney", 50f, Size.LARGE, false, 29, 10);
        testJerseys[3] = new Jersey(3, "Aaron", 50f, Size.XL, false, 5, 15);
        testJerseys[4] = new Jersey(4, "Derek", 39.99f, Size.SMALL, true, 6, 0);
        //making user list
        testUsers = new User[5];
        testUsers[0] = new User(0, "Nic", testJerseys);
        testUsers[1] = new User(1, "Claire", Arrays.copyOfRange(testJerseys, 0, 2));
        testUsers[2] = new User(2, "Domenic", testJerseys);
        testUsers[3] = new User(3, "Ming", null);
        testUsers[4] = new User(4, "Audi", Arrays.copyOfRange(testJerseys, 2, 5));

        //when objectMapper reads from file, it will return the user array above
        when(mockObjectMapper.readValue(new File("random.txt"), User[].class))
        .thenReturn(testUsers);
        userFileDAO = new UserFileDAO("random.txt", mockObjectMapper);
    }

    @Test
    public void testGetUser() throws IOException {
        //Invoke
        User user_success = userFileDAO.getUser("Ming");
        User user_fail = userFileDAO.getUser("null");

        //Analyze
        assertEquals(testUsers[3], user_success);
        assertNull(user_fail);
    }

    @Test
    public void testCreateUser() throws IOException {
        //Setup
        String user = "Test";

        //Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.createUser(user), 
            "unexpected exception thrown");

        //AnalyzeResult
        assertNotNull(result);
        assertEquals(testUsers.length+1, userFileDAO.users.size());

        //Analyze whether first created user is the same as what we gave
        User actual = userFileDAO.getUser("Test");
        assertEquals(5, actual.getId());
        assertEquals(user, actual.getName());
        assertEquals(new Jersey[0].length, actual.getCart().length);
    }

    @Test
    public void testCreateUserConflict() throws IOException {
        //Setup
        String user = "Nic";

        //Invoke
        User result = userFileDAO.createUser(user);

        //Analyze
        assertNull(result);
        assertEquals(testUsers.length, userFileDAO.users.size());
    }

    @Test
    public void testDeleteUser() throws IOException {
        //Invoke
        boolean result = userFileDAO.deleteUser("Claire");

        //Analyze
        assertTrue(result);
        assertEquals(testUsers.length-1, userFileDAO.users.size());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException {
        //Invoke
        boolean result = userFileDAO.deleteUser("Does not Exit");

        //Analyze
        assertFalse(result);
        assertEquals(testUsers.length, userFileDAO.users.size());
    }

    @Test
    public void testSaveException() throws IOException {
        //Setup
        String user = "Help";
        doThrow(new IOException()).when(mockObjectMapper)
            .writeValue(any(File.class), any(User[].class));

        //Invoke and Analyze
        assertThrows(IOException.class, () -> userFileDAO.createUser(user), 
                                                "IOException thrown");
    }

    @Test
    public void testConstructorException() throws IOException {
        //Setup
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), User[].class);
        
            //Invoke and Analyze
            assertThrows(IOException.class,
            () -> new UserFileDAO("doesnt_matter.txt",mockObjectMapper),
            "IOException not thrown");
    }

    @Test
    public void testAddJersey() throws IOException {
        //Setup
        Jersey jersey = new Jersey(0, "Matt", 39.99f, Size.SMALL, false, 16, 0);
        String username = "Nic";

        //Invoke
        Jersey result = userFileDAO.addJersey(username, jersey);

        //Analyze
        assertEquals(5, result.getId());
        assertEquals("Matt", result.getName());
        assertEquals(39.99f, result.getCost());
        assertEquals(Size.SMALL, result.getSize());
        assertEquals(false, result.getIsHome());
        assertEquals(16, result.getNumber());
        //Analyze cart
        assertEquals(testJerseys.length+1, userFileDAO.getCart(username).length);
    }

    @Test
    public void testAddJerseyUserNotFound() throws IOException {
        //Setup
        Jersey jersey = new Jersey(0, "Matt", 39.99f, Size.SMALL, false, 16, 0);
        String username = "NotFound";

        //Invoke
        Jersey result = userFileDAO.addJersey(username, jersey);

        //Analyze
        assertNull(result);
    }

    @Test
    public void testGetCart() throws IOException {
        //setup
        String username = "Nic";

        //invoke
        Jersey[] result = userFileDAO.getCart(username);

        //Analyze
        assertEquals(testJerseys.length, result.length);
        for(int i = 0; i < testJerseys.length; i ++) {
            assertEquals(testJerseys[i], result[i]);
        }
    }

    @Test
    public void testGetCartUserNotFound() throws IOException {
        //Setup
        String username = "NotFound";

        //Invoke
        Jersey[] result = userFileDAO.getCart(username);

        //analyze
        assertNull(result);
    }

    @Test
    public void testRemoveJersey() throws IOException {
        //Setup
        Jersey jersey = new Jersey(0, "Matt", 39.99f, Size.SMALL, false, 16, 0);
        String username = "Nic";

        //Invoke
        boolean result = userFileDAO.removeJersey(username, jersey);

        //analyze
        assertTrue(result);
        assertEquals(testJerseys.length-1, userFileDAO.getCart(username).length);
    }

    @Test 
    public void testRemoveJerseyUserNotFound() throws IOException {
         //Setup
         Jersey jersey = new Jersey(0, "Matt", 39.99f, Size.SMALL, false, 16, 0);
         String username = "NotFound";
 
         //Invoke
         boolean result = userFileDAO.removeJersey(username, jersey);
 
         //analyze
         assertFalse(result);
    }

    @Test 
    public void testRemoveJerseyNotFound() throws IOException {
         //Setup
         Jersey jersey = new Jersey(9, "Matt", 39.99f, Size.SMALL, false, 16, 0);
         String username = "Nic";
 
         //Invoke
         boolean result = userFileDAO.removeJersey(username, jersey);
 
         //analyze
         assertFalse(result);
    }

    @Test
    public void testClearCart() throws IOException {
        //Setup
        String username = "Nic";

        //Invoke
        boolean result = userFileDAO.clearCart(username);

        //analyze
        assertTrue(result);
        assertEquals(0, userFileDAO.getCart(username).length);
    }

    @Test
    public void testClearCartNotFound() throws IOException {
        //Setup
        String username = "NotFound";

        //Invoke
        boolean result = userFileDAO.clearCart(username);

        //Analyze
        assertFalse(result);
    }
}
