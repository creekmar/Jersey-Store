package com.estore.api.estoreapi.persistence;
import java.io.IOException;
import com.estore.api.estoreapi.model.Jersey;

/* 
 * This is an interface that will be implemented by the JerseyFileDAO
 */
public interface JerseyDAO {

    /* 
     * Will return all of the Jerseys in an array 
     */
    Jersey[] getJerseys() throws IOException;

    /* 
     * will find the Jersey whose name matches the given text
     */
    Jersey[] findJersey(String text) throws IOException;

    /* 
    * given an id, this function will return the Jersey associated with the given id
    */
    Jersey getJersey(int id) throws IOException;

    /* 
    * Given a Jersey object, this function will return a jersey object while adding it into the array
    */
    Jersey createJersey(Jersey jersey) throws IOException;

    /* 
    * will update the Jersey object given the new Jersey, put it in the map, and will return that Jersey
    */
    Jersey updateJersey(Jersey jersey) throws IOException;

    /* 
    * will delete the Jersey object given the new Jerseys, remove it from the map, and will return that Jersey
    */
    boolean deleteJersey(int id) throws IOException;

}
