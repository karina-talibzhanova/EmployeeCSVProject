package com.sparta.employeecsv;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CSVProcessorShould {
    private CSVProcessor processor;

    @BeforeEach
    public void setUp() {
        processor = new CSVProcessor();
    }

    @Test
    public void givenValidFile_ReturnNumLinesRead() {
        String validFile = "EmployeeRecords.csv";
        // there are 10,000 records, not including the column headings at the start
        assertEquals(10000, processor.readFile(validFile));
    }

    @Test
    public void givenInvalidFile_ReturnMinusOne() {
        String invalidFile = "NotAFile.txt";
        assertEquals(-1, processor.readFile(invalidFile));
    }

    @Test
    public void givenValidCSVLine_ReturnTrue() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F,serafina.bumgarner@exxonmobil.com,9/21/1982,2/1/2008,69294";
        assertTrue(processor.parseLine(csv));
    }

    @Test
    public void givenValidCSVLine_AddEmployeeObjectToClean() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F,serafina.bumgarner@exxonmobil.com,9/21/1982,2/1/2008,69294";
        processor.parseLine(csv);
        assertEquals(1, processor.getClean().size());  // there should be 1 object in the array
    }

    @Test
    public void givenTooLongCSVLine_ReturnFalse() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F,serafina.bumgarner@exxonmobil.com,9/21/1982,2/1/2008,69294,an extra element";
        assertFalse(processor.parseLine(csv));
    }

    @Test
    public void givenTooLongCSVLine_AddStringToFaulty() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F,serafina.bumgarner@exxonmobil.com,9/21/1982,2/1/2008,69294,an extra element";
        processor.parseLine(csv);
        assertEquals(1, processor.getFaulty().size());
    }

    @Test
    public void givenTooShortCSVLine_ReturnFalse() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F";
        assertFalse(processor.parseLine(csv));
    }

    @Test
    public void givenTooShortCSVLine_AddStringToFaulty() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F";
        processor.parseLine(csv);
        assertEquals(1, processor.getFaulty().size());
    }

    @Test
    public void givenRecordWithMissingElement_ReturnFalse() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F,,9/21/1982,2/1/2008,69294";
        assertFalse(processor.parseLine(csv));
    }

    @Test
    public void givenRecordWithMissingElement_AddStringToFaulty() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F,,9/21/1982,2/1/2008,69294";
        processor.parseLine(csv);
        assertEquals(1, processor.getFaulty().size());
    }

    @Test
    public void givenRecordWithWhitespaceAsElement_ReturnFalse() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F,   ,9/21/1982,2/1/2008,69294";
        assertFalse(processor.parseLine(csv));
    }

    @Test
    public void givenDuplicateRecord_AddStringToDuplicate() {
        String record1 = "198429,Mrs.,Serafina,I,Bumgarner,F,serafina.bumgarner@exxonmobil.com,9/21/1982,2/1/2008,69294";
        String record2 = "198429,Mrs.,Serafina,I,Bumgarner,F,serafina.bumgarner@exxonmobil.com,9/21/1982,2/1/2008,69294";

        processor.parseLine(record1);
        processor.parseLine(record2);
        assertEquals(1, processor.getDuplicate().size());
    }
}
