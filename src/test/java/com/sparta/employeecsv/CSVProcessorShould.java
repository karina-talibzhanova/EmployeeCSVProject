package com.sparta.employeecsv;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CSVProcessorShould {
    private static CSVProcessor processor;

    @BeforeAll
    public static void setUp() {
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
    public void givenValidCSVLine_AddEmployeeObjectToArrayList() {
        String csv = "198429,Mrs.,Serafina,I,Bumgarner,F,serafina.bumgarner@exxonmobil.com,9/21/1982,2/1/2008,69294";
        processor.parseLine(csv);
        assertEquals(1, processor.getClean().size());  // there should be 1 object in the array
    }
}
