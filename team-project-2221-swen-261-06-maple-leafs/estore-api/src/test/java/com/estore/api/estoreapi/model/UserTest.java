package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Jersey.Size;

@Tag("Model-Tier")
public class UserTest {

    Jersey[] expected_cart;


    /**
     * Test the constructor of User Java Class along with the get methods
     */

    @BeforeEach
    public void setup() {
        //Setup
        int expected_id = 1;
        String expected_username = "SuperSally45";
        expected_cart = new Jersey[5];

        expected_cart[0] = new Jersey(0, "Matt", 39.99f, Size.SMALL, false, 16, 0);
        expected_cart[1] = new Jersey(1, "Dave", 39.99f, Size.MEDIUM, true, 3, 0);
        expected_cart[2] = new Jersey(2, "Sidney", 50f, Size.LARGE, false, 29, 10);
        expected_cart[3] = new Jersey(3, "Aaron", 50f, Size.XL, false, 5, 15);
        expected_cart[4] = new Jersey(4, "Derek", 39.99f, Size.SMALL, true, 6, 0);


    }

    @Test
    public void testCtor() {
        //Setup
        int expected_id = 1;
        String expected_username = "SuperSally";


        //Invoke
        User user = new User(expected_id, expected_username, expected_cart);

        //Analyze
        assertEquals(expected_id, user.getId());
        assertEquals(expected_username, user.getName());
        assertEquals(expected_cart, user.getCart());
    }

    /**
     * Test the equals method of User class. They should be equal if
     * they have the same id. If they have different id's they should not be equal
     */
    @Test
    public void testEquals() {
        //Setup
        int expected_id = 1;
        String expected_username = "Ming";

        Jersey[] expected_cart = null;

        User user1 = new User(expected_id, expected_username, expected_cart);
        User user2 = new User(expected_id, expected_username + " ", expected_cart);

        //Inovke
        boolean same_result = user1.equals(user1);
        boolean different_result = user1.equals(user2);

        //Analyze
        assertEquals(true, same_result);
        assertEquals(false, different_result);
    }

    /**
     * Tests the method sameName in User class
     * Should be the same if the given string is the same as the user's name
     * Should be different if the given string is not the same as the user's name
     */
    @Test
    public void testSameName() {
        //Setup
        int expected_id = 1;
        String expected_username = "Ming";

        Jersey[] expected_cart = null;

        User user = new User(expected_id, expected_username, expected_cart);

        //Invoke/Analyze
        assertTrue(user.sameName(expected_username));
        assertFalse(user.sameName("Test"));
    }


    /**
     * Test compareTo method. Should subtract given user's id from the user's id
     * that invoked the test
     */
    @Test
    public void testCompareTo() {
        //Setup
        int id_1 = 1;
        int id_2 = 4;
        User user1 = new User(id_1, "One", expected_cart);
        User user2 = new User(id_2, "Two", expected_cart);

        //Invoke
        int result = user1.compareTo(user2);

        //Analyze
        assertEquals(id_1-id_2, result);
    }

    /**
     * test addJersey. Should add a new jersey to the cart with updated id
     * Should add even if it is a jersey with the same values
     */
    @Test
    public void testAddJersey() {
        //Setup
        User user = new User(0, "Testing", expected_cart);
        Jersey jersey = new Jersey(0, "Matt", 39.99f, Size.SMALL, false, 16, 10);

        //Invoke
        Jersey result = user.addJersey(jersey);

        //Analyze the jersey added
        assertEquals(5, result.getId());
        assertEquals("Matt", result.getName());
        assertEquals(39.99f, result.getCost());
        assertEquals(Size.SMALL, result.getSize());
        assertEquals(false, result.getIsHome());
        assertEquals(16, result.getNumber());
        assertEquals(10, result.getDiscount());
        //analyze cart 
        assertEquals(expected_cart.length+1, user.getCart().length);
    }

    /**
     * test deleteJersey. Should delete the given jersey from the cart
     * return true that it was deleted
     */
    public void testDeleteJersey() {
        //Setup
        User user = new User(0, "Testing", expected_cart);
        Jersey deletedJersey = new Jersey(2, "Sidney", 50f, Size.LARGE, false, 29, 10);

        //invoke
        boolean result = user.deleteJersey(deletedJersey);

        //Analyze
        assertTrue(result);
        assertEquals(expected_cart.length-1, user.getCart().length);
    }

    /**
     * test DeleteJerseyNotFound. should not delete the given jersey from cart
     * since it does not exist. Because of the way equals method is, should only
     * delete if different id
     * Should return false
     */
    @Test
    public void testDeleteJerseyNotFound() {
        //Setup
        User user = new User(0, "Testing", expected_cart);
        Jersey deletedJersey = new Jersey(9, "Si", 50f, Size.LARGE, false, 29, 10);

        //invoke
        boolean result = user.deleteJersey(deletedJersey);

        //Analyze
        assertFalse(result);
        assertEquals(expected_cart.length, user.getCart().length);
    }

    @Test
    public void clearCart() {
        //Setup
        User user = new User(0, "Test", expected_cart);

        //invoke
        user.clearCart();

        //Analyze
        assertEquals(0, user.getCart().length);
    }
    
}
