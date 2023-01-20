package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Jersey;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.Jersey.Size;
import com.estore.api.estoreapi.persistence.UserDAO;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

@Tag("Controller-Tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testGetUser() throws IOException {
        //Setup
        User user = new User(0, "Hi", null);
        when(mockUserDAO.getUser(user.getName())).thenReturn(user);

        //Invoke
        ResponseEntity<User> response = userController.getUser(user.getName());

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User body = response.getBody();
        assertNotNull(body);
        assertEquals(user.getId(), body.getId());
        assertEquals(user.getName(), body.getName());
        assertEquals(user.getCart(), body.getCart());
    }

    @Test
    public void testGetUserNotFount() throws IOException {
        //Setup
        String username = "NotFound";
        when(mockUserDAO.getUser(username)).thenReturn(null);

        //Invoke
        ResponseEntity<User> response = userController.getUser(username);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws IOException {
        //Setup
        String username = "Exception";
        doThrow(IOException.class).when(mockUserDAO).getUser(username);

        //Invoke
        ResponseEntity<User> response = userController.getUser(username);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateUser() throws IOException {
        //Setup
        String username = "Test";
        User user = new User(0, username, new Jersey[0]);
        when(mockUserDAO.createUser(username)).thenReturn(user);

        //Invoke
        ResponseEntity<User> response = userController.createUser(username);

        //Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        User body = response.getBody();
        assertNotNull(body);
        assertEquals(user.getId(), body.getId());
        assertEquals(user.getName(), body.getName());
        assertEquals(user.getCart(), body.getCart());
    }

    @Test
    public void testCreateUserConflict() throws IOException {
        //Setup
        String username = "Test";
        when(mockUserDAO.createUser(username)).thenReturn(null);

        //Invoke
        ResponseEntity<User> response = userController.createUser(username);

        //Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateUserHandleException() throws IOException {
        //Setup
        String username = "Test";
        doThrow(IOException.class).when(mockUserDAO).createUser(username);

        //Invoke
        ResponseEntity<User> response = userController.createUser(username);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException {
        //Setup
        String username = "Test";
        when(mockUserDAO.deleteUser(username)).thenReturn(true);

        //Invoke
        ResponseEntity<User> response = userController.deleteUser(username);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException {
        //Setup
        String username = "Test";
        when(mockUserDAO.deleteUser(username)).thenReturn(false);

        //Invoke
        ResponseEntity<User> response = userController.deleteUser(username);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws IOException {
        //Setup
        String username = "Test";
        doThrow(IOException.class).when(mockUserDAO).deleteUser(username);

        //Invoke
        ResponseEntity<User> response = userController.deleteUser(username);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCart() throws IOException {
        //setup
        Jersey[] testJerseys = new Jersey[3];
        testJerseys[0] = new Jersey(0, "Matt", 39.99f, Size.SMALL, false, 16, 0);
        testJerseys[1] = new Jersey(1, "Dave", 39.99f, Size.MEDIUM, true, 3, 0);
        testJerseys[2] = new Jersey(2, "Sidney", 50f, Size.LARGE, false, 29, 10);
        String username = "Test";
        when(mockUserDAO.getCart(username)).thenReturn(testJerseys);

        //invoke
        ResponseEntity<Jersey[]> response = userController.getCart(username);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Jersey[] result_cart = response.getBody();
        assertNotNull(result_cart);
        assertEquals(testJerseys.length, result_cart.length);
        for(int i = 0; i < testJerseys.length; i++) {
            assertEquals(testJerseys[i], result_cart[i]);
        }
    }

    @Test
    public void testGetCartNotFound() throws IOException {
        //setup
        String username = "NotFound";
        when(mockUserDAO.getCart(username)).thenReturn(null);

        //Invoke
        ResponseEntity<Jersey[]> response = userController.getCart(username);

        //analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCartException() throws IOException {
        //Setup
        String username = "exception";
        doThrow(IOException.class).when(mockUserDAO).getCart(username);

        //invoke
        ResponseEntity<Jersey[]> response = userController.getCart(username);

        //analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetTotalCost() throws IOException {
        //setup
        Jersey[] testJerseys = new Jersey[3];
        testJerseys[0] = new Jersey(0, "Matt", 40f, Size.SMALL, false, 16, 0);
        testJerseys[1] = new Jersey(1, "Dave", 40f, Size.MEDIUM, true, 3, 0);
        testJerseys[2] = new Jersey(2, "Sidney", 50f, Size.LARGE, false, 29, 10);
        String username = "Test";
        when(mockUserDAO.getCart(username)).thenReturn(testJerseys);

        //invoke
        ResponseEntity<Float> response = userController.getTotalCost(username);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        float actual = response.getBody();
        assertNotNull(actual);
        assertEquals(125, actual);
    }

    @Test
    public void testGetTotalCostNotFound() throws IOException {
        //setup
        String username = "NotFound";
        when(mockUserDAO.getCart(username)).thenReturn(null);

        //Invoke
        ResponseEntity<Float> response = userController.getTotalCost(username);

        //analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetTotalCostException() throws IOException {
        //Setup
        String username = "exception";
        doThrow(IOException.class).when(mockUserDAO).getCart(username);

        //invoke
        ResponseEntity<Float> response = userController.getTotalCost(username);

        //analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddToCart() throws IOException {
        //setup
        String username = "Nic";
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        when(mockUserDAO.addJersey(username, jersey)).thenReturn(jersey);

        //invoke
        ResponseEntity<Jersey> response = userController.addToCart(username, jersey);

        //analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jersey, response.getBody());
    }

    @Test
    public void testAddToCartNotFound() throws IOException {
        //setup
        String username = "NotFound";
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        when(mockUserDAO.addJersey(username, jersey)).thenReturn(null);

        //Invoke
        ResponseEntity<Jersey> response = userController.addToCart(username, jersey);

        //analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddToCartException() throws IOException {
        //setup
        String username = "Exception";
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        doThrow(IOException.class).when(mockUserDAO).addJersey(username, jersey);

        //Invoke
        ResponseEntity<Jersey> response = userController.addToCart(username, jersey);

        //analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveJerseyFromCart() throws IOException {
        //setup
        String username = "Exception";
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        when(mockUserDAO.removeJersey(username, jersey)).thenReturn(true);

        //invoke
        ResponseEntity<Jersey> response = userController.removeJerseyFromCart(username, jersey);

        //analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveJerseyFromCartNotFound() throws IOException {
        //setup
        String username = "Exception";
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        when(mockUserDAO.removeJersey(username, jersey)).thenReturn(false);

        //invoke
        ResponseEntity<Jersey> response = userController.removeJerseyFromCart(username, jersey);

        //analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveJerseyFromCartException() throws IOException {
        //setup
        String username = "Exception";
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        doThrow(IOException.class).when(mockUserDAO).removeJersey(username, jersey);

        //invoke
        ResponseEntity<Jersey> response = userController.removeJerseyFromCart(username, jersey);

        //analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testClearCart() throws IOException {
        //setup
        String username = "Exception";
        when(mockUserDAO.clearCart(username)).thenReturn(true);

        //invoke
        ResponseEntity<Jersey> response = userController.clearCart(username);

        //analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testClearCartFailed() throws IOException {
        //setup
        String username = "Exception";
        when(mockUserDAO.clearCart(username)).thenReturn(false);

        //invoke
        ResponseEntity<Jersey> response = userController.clearCart(username);

        //analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testClearCartException() throws IOException {
        //setup
        String username = "Exception";
        doThrow(IOException.class).when(mockUserDAO).clearCart(username);

        //invoke
        ResponseEntity<Jersey> response = userController.clearCart(username);

        //analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
