package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Jersey.Size;

// comment
@Tag("Model-tier")
public class JerseyTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 7;
        String expected_name = "Dom";
        float expectedCost = 32.58f;
        Size expectedSize = Size.MEDIUM;
        boolean expectedIsHome = true;
        int expectedNumber = 93;
        int expectedDiscount = 10;
        

        // Invoke
        Jersey jersey = new Jersey(expected_id,expected_name,expectedCost,expectedSize,expectedIsHome,expectedNumber, expectedDiscount);

        // Analyze
        assertEquals(expected_id,jersey.getId());
        assertEquals(expected_name,jersey.getName());
        assertEquals(expectedCost,jersey.getCost());
        assertEquals(expectedSize,jersey.getSize());
        assertEquals(expectedIsHome,jersey.getIsHome());
        assertEquals(expectedNumber,jersey.getNumber());
        assertEquals(expectedDiscount, jersey.getDiscount());
    }

    @Test
    public void testEquals() {
        // Setup
        int expected_id = 7;
        String expected_name = "Dom";
        float expectedCost = 32.58f;
        Size expectedSize = Size.MEDIUM;
        boolean expectedIsHome = true;
        int expectedNumber = 93;
        int expectedDiscount = 10;
        Jersey jersey = new Jersey(expected_id,expected_name,expectedCost,expectedSize,expectedIsHome,expectedNumber, expectedDiscount);
        Jersey jersey2 = new Jersey(expected_id+1,expected_name,expectedCost,expectedSize,expectedIsHome,expectedNumber, expectedDiscount);

        //Invoke
        boolean same_result = jersey.equals(jersey);
        boolean not_same_result = jersey.equals(jersey2);
        
        //Analyze
        assertEquals(true, same_result);
        assertEquals(false, not_same_result);
    }

    @Test
    public void testIsSame() {
        // Setup
        int expected_id = 7;
        String expected_name = "Dom";
        float expectedCost = 32.58f;
        Size expectedSize = Size.MEDIUM;
        boolean expectedIsHome = true;
        int expectedNumber = 93;
        int expectedDiscount = 10;
        

        // Invoke
        Jersey jersey = new Jersey(expected_id,expected_name,expectedCost,expectedSize,expectedIsHome,expectedNumber, expectedDiscount);
        Jersey jersey2 = new Jersey(expected_id,expected_name,expectedCost+.75f,expectedSize,expectedIsHome,expectedNumber, expectedDiscount);
        Jersey jersey3 = new Jersey(expected_id,expected_name+"m",expectedCost,expectedSize,expectedIsHome,expectedNumber, expectedDiscount);
        Jersey jersey4 = new Jersey(expected_id,expected_name,expectedCost,expectedSize,!expectedIsHome,expectedNumber, expectedDiscount);
        Jersey jersey5 = new Jersey(expected_id,expected_name,expectedCost,expectedSize,expectedIsHome,expectedNumber+5, expectedDiscount);
        Jersey jersey6 = new Jersey(expected_id,expected_name,expectedCost,expectedSize,expectedIsHome,expectedNumber, expectedDiscount+5);
        
        //Analyze
        assertTrue(jersey.isSameContent(jersey));
        assertFalse(jersey.isSameContent(null));
        assertFalse(jersey.isSameContent(jersey2));
        assertFalse(jersey.isSameContent(jersey3));
        assertFalse(jersey.isSameContent(jersey4));
        assertFalse(jersey.isSameContent(jersey5));
        assertFalse(jersey.isSameContent(jersey6));
    }
}
