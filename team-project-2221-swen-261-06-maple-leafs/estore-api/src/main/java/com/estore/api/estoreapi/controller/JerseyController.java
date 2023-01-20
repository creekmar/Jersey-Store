package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Jersey;
import com.estore.api.estoreapi.persistence.JerseyDAO;

@RestController
@RequestMapping("jerseys")
public class JerseyController {
    private JerseyDAO jerseyDAO;

    private static final Logger LOG = Logger.getLogger(JerseyController.class.getName());

    public JerseyController(JerseyDAO jerseyDAO){
        this.jerseyDAO = jerseyDAO;
    }
    
    /**
    * Gets a jersey with the specified id, id.
    * 
    * @param id the id to search jerseys for
    *
    * @return ResponseEntity with found jersey and status OK if found,
    * if not found returns with status NOT_FOUND, otherwise status INTERNAL_SERVER_ERROR
    */
    @GetMapping("/{id}")
    public ResponseEntity<Jersey> getJersey(@PathVariable int id) {
        LOG.info("GET /jerseys/" + id);
        try {
            Jersey jersey = jerseyDAO.getJersey(id);
            if (jersey != null)
                return new ResponseEntity<Jersey>(jersey,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
    * Gets an array of all jerseys
    *
    * @return ResponseEntity with array of jerseys and status OK if successful, 
    * status INTERNAL_SERVER_ERROR otherwise.
    */
    @GetMapping("")
    public ResponseEntity<Jersey[]> getJerseys() {
        LOG.info("GET /jerseys");

        try {
            Jersey[] jerseys = jerseyDAO.getJerseys();
            if(jerseys != null)
            {
                return new ResponseEntity<Jersey[]>(jerseys, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
    * Searches jerseys for a jersey name containing name
    *
    * @param name string to search jerseys for.
    *
    * @return ResponseEntity with array of jerseys and status OK if successful, if not, status NOT_Found
    * otherwise returns status INTERNAL_SERVER_ERROR.
    */
    @GetMapping("/")
    public ResponseEntity<Jersey[]> searchJerseys(@RequestParam String name) {
        LOG.info("GET /jerseys/?name="+name);
        try {
            Jersey[] jerseys = jerseyDAO.findJersey(name);
            if (jerseys != null)
                return new ResponseEntity<Jersey[]>(jerseys,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
    * Creates a jersey with the same data as input
    *
    * @param jersey the jersey to be created
    *
    * @return ResponseEntity with jersey created and status CREATED if successful, if not, 
    * status CONFLICT, otherwise, status INTERNAL_SERVER_ERROR.
    */
    @PostMapping("")
    public ResponseEntity<Jersey> createJersey (@RequestBody Jersey jersey)
    {
        LOG.info("POST /jerseys " + jersey);

        try{
            if (jerseyDAO.createJersey(jersey) != null)
                return new ResponseEntity<Jersey>(jersey, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
    * Updates a jersey with the same id as the input jersey
    *
    * @param jersey jersey object that has updated information
    *
    * @return ResponseEntity with status OK if ok, with the input jersey and
    * status of NOT_FOUND if not found, and status of INTERNAL_SERVER_ERROR
    * otherwise.
    */
    @PutMapping("")
    public ResponseEntity<Jersey> updateJersey(@RequestBody Jersey jersey) {
        LOG.info("PUT /jerseys " + jersey);
        try {
            jersey = jerseyDAO.updateJersey(jersey);
            if(jersey != null){
                return new ResponseEntity<Jersey>(jersey, HttpStatus.OK);
            }
            return new ResponseEntity<Jersey>(jersey,HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
    * Deletes a jersey with the id id.
    *
    * @param id id of the jersey
    *
    * @return ResponseEntity with the status of OK if deleted, 
    * NOT_FOUND if not found, INTERNAL_SERVER_ERROR otherwise
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Jersey> deleteJersey(@PathVariable int id) {
        LOG.info("DELETE /jerseys/" + id);
        try{ 
            if(jerseyDAO.deleteJersey(id)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
