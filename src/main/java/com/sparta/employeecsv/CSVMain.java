package com.sparta.employeecsv;

public class CSVMain {
    public static void main(String[] args) {
        // get file name (either hardcode or user input)
        // pass it off to a reader
        // expected output: number of unique clean records, number of duplicates, number of incomplete records
        // possibly display the faulty records
        String fileName = "EmployeeRecords.csv";

        CSVProcessor processor = new CSVProcessor();
        int linesRead = processor.readFile(fileName);
        System.out.println(linesRead + " records processed");
        System.out.println(processor.getClean());
    }
}
