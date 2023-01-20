package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.estore.api.estoreapi.model.Jersey.Size;
import com.estore.api.estoreapi.persistence.JerseyDAO;

import net.bytebuddy.implementation.InvokeDynamic;

@Tag("Controller-tier")
public class JerseyControllerTest {
    private JerseyController jerseyController;
    private JerseyDAO mockJerseyDAO;


    @Test
    public void testGetJersey() throws IOException {  // getJersey may throw IOException
        // Setup
        Jersey jersey = new Jersey(7, "Dom", 32.58f, Size.MEDIUM, true, 93, 10);
        // When the same id is passed in, our mock Jersey DAO will return the Jersey object
        when(mockJerseyDAO.getJersey(jersey.getId())).thenReturn(jersey);

        // Invoke
        ResponseEntity<Jersey> response = jerseyController.getJersey(jersey.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(jersey,response.getBody());
    }

    @Test
    public void testGetJerseyNotFound() throws Exception { // createJersey may throw IOException
        // Setup
        int jerseyId = 99;
        // When the same id is passed in, our mock Jersey DAO will return null, simulating
        // no hero found
        when(mockJerseyDAO.getJersey(jerseyId)).thenReturn(null);

        // Invoke
        ResponseEntity<Jersey> response = jerseyController.getJersey(jerseyId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetJerseyHandleException() throws Exception { // createJersey may throw IOException
        // Setup
        int jerseyId = 99;
        // When getJersey is called on the Mock Jersey DAO, throw an IOException
        doThrow(new IOException()).when(mockJerseyDAO).getJersey(jerseyId);

        // Invoke
        ResponseEntity<Jersey> response = jerseyController.getJersey(jerseyId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    /**
     * Before each test, create a new JerseyController object and inject
     * a mock Jersey DAO
     */
    @BeforeEach
    public void setupJerseyController() {
        mockJerseyDAO = mock(JerseyDAO.class);
        jerseyController = new JerseyController(mockJerseyDAO);
    }

    /**
     * Test whether when controller creates a new jersey it will return the jersey
     * created and give HTTP status of OK
     * @throws IOException
     */
    @Test
    public void testCreateJersey() throws IOException {
        //setup
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 10);
        when(mockJerseyDAO.createJersey(jersey)).thenReturn(jersey);
        
        //Invoke
        ResponseEntity<Jersey> response = jerseyController.createJersey(jersey);
        
        //Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(jersey, response.getBody());
    }

    /**
     * Test CreateJersey in controller
     * when given a null it should return conflict status
     * @throws IOException
     */
    @Test
    public void testCreateJerseyFailed() throws IOException {
        //Setup
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        when(mockJerseyDAO.createJersey(jersey)).thenReturn(null);

        //Invoke
        ResponseEntity<Jersey> response = jerseyController.createJersey(jersey);

        //Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Test Create Jersey
     * when IOException occurs an HttpStatus internal service is returned
     * @throws IOException
     */
    @Test
    public void testCreateJerseyHandleException() throws IOException {
        //Setup
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        doThrow(new IOException()).when(mockJerseyDAO).createJersey(jersey);

        //Invoke
        ResponseEntity<Jersey> response = jerseyController.createJersey(jersey);
        
        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testUpdateJersey() throws IOException { 
        // Setup
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        when(mockJerseyDAO.updateJersey(jersey)).thenReturn(jersey);
        ResponseEntity<Jersey> response = jerseyController.updateJersey(jersey);
        
        // Invoke
        response = jerseyController.updateJersey(jersey);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(jersey, response.getBody());
        assertEquals(7, response.getBody().getId());
        assertTrue(jersey.isSameContent(response.getBody()));
    }

    @Test
    public void testUpdateJerseyFailed() throws IOException { // updateJersey may throw IOException
        // Setup
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        // when updateJersey is called, return true simulating successful
        // update and save
        when(mockJerseyDAO.updateJersey(jersey)).thenReturn(null);

        // Invoke
        ResponseEntity<Jersey> response = jerseyController.updateJersey(jersey);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateJerseyHandleException() throws IOException { // updateJersey may throw IOException
        // Setup
        Jersey jersey = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        // When updateJersey is called on the Mock Jersey DAO, throw an IOException
        doThrow(new IOException()).when(mockJerseyDAO).updateJersey(jersey);

        // Invoke
        ResponseEntity<Jersey> response = jerseyController.updateJersey(jersey);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    @Test
    public void testGetJerseys() throws IOException { // getJerseys may throw IOException
        // Setup
        Jersey[] jerseys = new Jersey[2];
        jerseys[0] = new Jersey(7, "Ming", 32.58f, Size.LARGE, false, 23, 20);
        jerseys[1] = new Jersey(5, "Dom", 32.58f, Size.MEDIUM, true, 23, 0);
        // When getHeroes is called return the heroes created above
        when(mockJerseyDAO.getJerseys()).thenReturn(jerseys);

        // Invoke
        ResponseEntity<Jersey[]> response = jerseyController.getJerseys();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(jerseys,response.getBody());
    }

    @Test
    public void testGetJerseysFail() throws IOException {
        //Setup
        when(mockJerseyDAO.getJerseys()).thenReturn(null);

        //Invoke
        ResponseEntity<Jersey[]> response = jerseyController.getJerseys();

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testGetJerseysHandleException() throws IOException { // getJerseys may throw IOException
        // Setup
        // When getHeroes is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockJerseyDAO).getJerseys();

        // Invoke
        ResponseEntity<Jersey[]> response = jerseyController.getJerseys();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }



    /**
     * Test Search Jerseys
     * Should give status of OK when an array of jerseys is returned
     * @throws IOException
     */
    @Test
    public void testSearchJerseys() throws IOException {
        //Setup
        String searchString = "D";
        Jersey[] jerseys = new Jersey[2];
        jerseys[0] = new Jersey(0, "Dave", 39.99f, Size.MEDIUM, true, 3, 10);
        jerseys[1] = new Jersey(1, "Derek", 39.99f, Size.SMALL, true, 6, 0);
        when(mockJerseyDAO.findJersey(searchString)).thenReturn(jerseys);

        //Invoke
        ResponseEntity<Jersey[]> response = jerseyController.searchJerseys(searchString);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jerseys, response.getBody());
    }

    @Test
    public void testSearchJerseysFail() throws IOException {
        //Setup
        String searchString = "D";
        when(mockJerseyDAO.findJersey(searchString)).thenReturn(null);

        //Invoke
        ResponseEntity<Jersey[]> response = jerseyController.searchJerseys(searchString);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    /**
     * Test search jersey
     * When given an exception, should return status code of internal service error
     * @throws IOException
     */
    @Test
    public void testSearchJerseysHandleException() throws IOException {
        //Setup
        String searchString = "D";
        doThrow(new IOException()).when(mockJerseyDAO).findJersey(searchString);

        //Invoke
        ResponseEntity<Jersey[]> response = jerseyController.searchJerseys(searchString);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
 
    @Test
    public void testDeleteJersey() throws IOException { // deleteJersey may throw IOException
        // Setup
        int JerseyId = 99;
        // when deleteJersey is called return true, simulating successful deletion
        when(mockJerseyDAO.deleteJersey(JerseyId)).thenReturn(true);
  
        // Invoke
        ResponseEntity<Jersey> response = jerseyController.deleteJersey(JerseyId);
  
        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
  
    @Test
    public void testDeleteJerseyNotFound() throws IOException { // deleteJersey may throw IOException
        // Setup
        int JerseyId = 99;
        // when deleteJersey is called return false, simulating failed deletion
        when(mockJerseyDAO.deleteJersey(JerseyId)).thenReturn(false);
  
        // Invoke
        ResponseEntity<Jersey> response = jerseyController.deleteJersey(JerseyId);
  
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
  
    @Test
    public void testDeleteJerseyHandleException() throws IOException { // deleteJersey may throw IOException
        // Setup
        int JerseyId = 99;
        // When deleteJersey is called on the Mock Jersey DAO, throw an IOException
        doThrow(new IOException()).when(mockJerseyDAO).deleteJersey(JerseyId);
  
        // Invoke
        ResponseEntity<Jersey> response = jerseyController.deleteJersey(JerseyId);
  
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
 
 
}
