package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Jersey;
import com.fasterxml.jackson.databind.ObjectMapper;


// Implements the functionality for JSON file-based peristance for Jerseys
@Component
public class JerseyFileDAO implements JerseyDAO {
    private static final Logger LOG = Logger.getLogger(JerseyFileDAO.class.getName());
    Map<Integer, Jersey> jerseys;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    public JerseyFileDAO(@Value("${jerseys.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    // this method will generate a new id for the new jersey in the store

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    // This will return the jerseys array 
    private Jersey[] getJerseysArray() {
        return getJerseysArray(null);
    }
    
    // given a string (filter), the function will return an array matching the filter
    private Jersey[] getJerseysArray(String containsText) { // if containsText == null, no filter
        ArrayList<Jersey> jerseyArrayList = new ArrayList<>();

        for (Jersey jersey : jerseys.values()) {
            if (containsText == null || jersey.getName().contains(containsText)) {
                jerseyArrayList.add(jersey);
            }
        }

        Jersey[] jerseyArray = new Jersey[jerseyArrayList.size()];
        jerseyArrayList.toArray(jerseyArray);
        return jerseyArray;
    }

    // load Jersey from the JSON into a map, will return true if read successfully 
    private boolean load() throws IOException {
        jerseys = new TreeMap<>();
        nextId = 0;

        Jersey[] jerseyArray = objectMapper.readValue(new File(filename), Jersey[].class);

        for(Jersey jersey: jerseyArray) {
            jerseys.put(jersey.getId(), jersey);
            if (jersey.getId() > nextId) {
                nextId = jersey.getId();
            }
        }

        ++nextId;
        return true;
    }

    // Saves the Jerseys from the map into the file as an array of JSON objects, return true if the Jerseys were written successfully
    private boolean save() throws IOException {
        Jersey[] jerseyArray = getJerseysArray();
        
        objectMapper.writeValue(new File(filename), jerseyArray);
        return true;
    }

    // given an id, this function will return the Jersey associated with the given id
    @Override
    public Jersey getJersey(int id) throws IOException {
        synchronized(jerseys){
            if(jerseys.containsKey(id)){
                return jerseys.get(id);
            }else{
                return null;
            }
        }
    }

    // Given a Jersey object, if a jersey with the same content hasn't been created
    // this function will return a jersey object while adding it into the array
    // otherwise return null
    public Jersey createJersey(Jersey jersey) throws IOException {
        synchronized(jerseys){
            for (Jersey jer : jerseys.values()) {
                if(jersey.isSameContent(jer)) {
                    return null;
                }
            }
            Jersey newJersey = new Jersey(nextId(),jersey.getName(),jersey.getCost(),
                jersey.getSize(),jersey.getIsHome(),jersey.getNumber(), jersey.getDiscount());
            jerseys.put(newJersey.getId(), newJersey);
            save();
            return newJersey;
        }
    }

    // Will return all of the Jerseys in an array 
    @Override
    public Jersey[] getJerseys() throws IOException {
        synchronized(jerseys) {
            return getJerseysArray();
        }
    }

    // will find the Jersey whose name matches the given text
    @Override
    public Jersey[] findJersey(String text) throws IOException {
        synchronized(jerseys) {
            return getJerseysArray(text);
        }
    }


    // will update the Jersey object given the new Jersey, put it in the map, and will return that Jersey
    @Override
    public Jersey updateJersey(Jersey jersey) throws IOException {
        synchronized(jerseys) {
            if (jerseys.containsKey(jersey.getId()) == false)
                return null;

            jerseys.put(jersey.getId(),jersey);
            save(); // may throw an IOException
            return jersey;
        }
    }

    // will delete the Jersey object given the new Jerseys, remove it from the map, and will return that Jersey
    @Override
    public boolean deleteJersey(int id) throws IOException {
        synchronized(jerseys) {
            if (jerseys.containsKey(id)) {
                jerseys.remove(id);
                return save();
            }
            else
                return false;
        }
    } 
}
