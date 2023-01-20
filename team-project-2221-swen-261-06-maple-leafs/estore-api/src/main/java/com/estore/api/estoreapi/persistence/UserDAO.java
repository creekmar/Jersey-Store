package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Jersey;
import com.estore.api.estoreapi.model.User;

public interface UserDAO {
    
    /**
     * Given a user, this function will add the user to the user array and return
     * the user that was added
     * @param user the user to add
     * @return the user that was added
     * @throws IOException
     */
    User createUser(String name) throws IOException;

    /**
     * Delete a user from the array, returning true if removed, false otherwise
     * @param id the id of the user to remove
     * @return whether it was deleted or not
     * @throws IOException
     */
    boolean deleteUser(String name) throws IOException;

    /**
     * Gets a user from the array given the username
     * @param username of the user to get
     * @return a user
     * @throws IOException
     */
    User getUser(String username) throws IOException;

    /**
     * gets the cart of a specified user given the username
     * @param name the user's username
     * @return an array of jerseys
     * @throws IOException
     */
    Jersey[] getCart(String name) throws IOException;

    /**
     * Adds a jersey to a specified user's cart
     * @param name the user's username
     * @param jersey the jersey to add
     * @return the jersey that was added
     * @throws IOException
     */
    Jersey addJersey(String name, Jersey jersey) throws IOException;

    /**
     * Removes a jersey from a specified cart
     * @param name the user's username
     * @param jersey the jersey to remove
     * @return the jersey that was removed
     * @throws IOException
     */
    public boolean removeJersey(String name, Jersey jersey) throws IOException;

    /**
     * Clears the cart of a specified user
     * @param name the user's username
     * @return true if cart was found and cleared, false otherwise
     * @throws IOException
     */
    public boolean clearCart(String name) throws IOException;
}
