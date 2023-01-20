package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Comparable{
    private static final Logger LOG = Logger.getLogger(Jersey.class.getName());

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("cart") private Jersey[] cart;
    private ArrayList<Jersey> variableCart;
    private int nextCartId;

    public User(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("cart") Jersey[] cart){
        this.id = id;
        this.username = username;
        this.cart = cart;
        variableCart = new ArrayList<>();
        if(cart != null && cart.length != 0) {
            nextCartId = cart[cart.length-1].getId() + 1;
            for(Jersey jersey: cart) {
            variableCart.add(jersey);
            }
        }
        
    }

    /**
     * gets the user's id
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * gets the username
     * @return String username
     */
    public String getName() {
        return username;
    }

    /**
     * Gets the user's shopping cart
     * @return an array of Jerseys
     */
    public Jersey[] getCart() {
        return cart;
    }

    /**
     * Adds a jersey to the user's cart
     * @param jersey jersey to add
     * @return the jersey that was added or null if there was an error
     */
    public Jersey addJersey(Jersey jersey) {
        Jersey addedJersey = new Jersey(nextCartId, jersey.getName(),
            jersey.getCost(), jersey.getSize(), jersey.getIsHome(), jersey.getNumber(),
            jersey.getDiscount());
        boolean wasAdded = variableCart.add(addedJersey);
        if(wasAdded) {
            cart = new Jersey[variableCart.size()];
            variableCart.toArray(cart);
            nextCartId++;
            return addedJersey;
        }
        return null;
    }

    /**
     * Removes a jersey from the user's cart
     * @param jersey jersey to delete
     * @return true if jersey was deleted, false otherwise
     */
    public boolean deleteJersey(Jersey jersey) {
        boolean wasDeleted = variableCart.remove(jersey);
        if(wasDeleted) {
            cart = new Jersey[variableCart.size()];
            variableCart.toArray(cart);
        }
        return wasDeleted;
    }

    /**
     * Removes all jerseys in a user's shopping cart
     */
    public void clearCart() {
        variableCart.clear();
        cart = new Jersey[0];
    }

    @Override
    /**
     * Determines whether a User is equal to another User
     * They are equal if they have the same id
     * @return true if it is equal, false otherwise
     */
    public boolean equals(Object o){
        if(o instanceof User){
            User object = (User)(o);
            if(object.getName().equals(this.getName())){
                return true;
            }
        }
        return false;
    }

    public boolean sameName(String name) {
        if(this.getName().equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    /**
     * Compares the given user object with itself based on the id
     */
    public int compareTo(Object o) {
        User object = (User)o;
        return this.getId()-object.getId();
    }

}
