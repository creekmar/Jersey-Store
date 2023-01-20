package com.estore.api.estoreapi.model;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A representation of a Jersey
 * @author Maple Leaves Team SWEN 2022
 */
public class Jersey {
    private static final Logger LOG = Logger.getLogger(Jersey.class.getName());
    
    /*
     * enum to represent the size of a jersey
     * 0: SMALL
     * 1: Medium
     * 3: Large
     * 4: xL
     */
    public enum Size {
        SMALL, MEDIUM, LARGE, XL;
    }

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("cost") private float cost;
    @JsonProperty("size") private Size size;
    @JsonProperty("home") private boolean isHome;
    @JsonProperty("number") private int number;
    @JsonProperty("discount") private int discount;

    /**
     * Create a jersey with a given id, name, cost, size, isHome, number
     * @param id The id of a jersey
     * @param name the player name on the jersey
     * @param cost the cost of the jersey
     * @param size the size of the jersey
     * @param isHome if jersey is in colors isHome or Away
     * @param number player number on the jersey
     * @param discount the current jersey discount, 0 if there is none
     * 
     * @literal @JsonProperty is used to deserialize and serialize JSON objects
     *  to java objects in mapping the fields
     */
    public Jersey(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("cost") float cost, @JsonProperty("size") Size size,
    @JsonProperty("home") boolean isHome, @JsonProperty("number") int number, @JsonProperty("discount") int discount) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.size = size;
        this.isHome = isHome;
        this.number = number;
        this.discount = discount;
    }

    /**
     * gets the Jersey's id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * gets the player name on the jersey
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the cost of the jersey
     * @return cost
     */
    public float getCost(){
        return cost;
    }

    /**
     * gets the size of the jersey
     * @return size
     */
    public Size getSize(){
        return size;
    }

    /**
     * gets whether the colors are home or away for the jersey
     * @return isHome?
     */
    public boolean getIsHome(){
        return isHome;
    }

    /**
     * gets the number on the jersey
     * @return number
     */
    public int getNumber(){
        return number;
    }

    /**
     * gets the current discount of the jersey in percentage, 0 if there is none
     * @return discount
     */
    public int getDiscount() {
        return discount;
    }
    
    @Override
    /**
     * Determines whether a Jersey is equal to another jersey
     * They are equal if they have the same id
     * @return true if it is equal, false otherwise
     */
    public boolean equals(Object o){
        if(o instanceof Jersey){
            Jersey object = (Jersey)(o);
            if(object.getId() == this.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether a jersey has the same content as another jersey
     * They have the same content if every private variable is the same except id and size
     * @param o A jersey to compare to
     * @return true if same, false
     */
    public boolean isSameContent(Object o) {
        if(o instanceof Jersey) {
            Jersey jer = (Jersey)(o);
            if (jer.getName().equals(this.getName()) && (jer.getIsHome() == (this.getIsHome()))
                && (jer.getCost() == (this.getCost())) && (jer.getNumber() == (this.getNumber()))
                && (jer.getDiscount() == (this.getDiscount()))) {
                return true;
            }
        }
        return false;
    }

}
