package com.sparta.employeecsv;



public class CSVMain {
    public static void main(String[] args) {
        // get file name (either hardcode or user input)
        // pass it off to a reader
        // expected output: number of unique clean records, number of duplicates, number of incomplete records
        // possibly display the faulty records
        String fileName = "EmployeeRecordsLarge.csv";

        CSVProcessor processor = new CSVProcessor();
        int linesRead = processor.readFile(fileName);
        System.out.println(linesRead + " records processed.");
        System.out.println(processor.getClean().size() + " valid records.");
        System.out.println(processor.getFaulty().size() + " faulty records.");
        System.out.println(processor.getDuplicate().size() + " duplicate records.");

        DatabaseController.createTable();  // drops existing table, creates new one

        System.out.println("Creating threads...");
        ThreadController threadController = new ThreadController(8, processor.getClean());
        threadController.createThreads();

        System.out.println("Writing to database...");
        long start = System.currentTimeMillis();
        threadController.runThreads();
        long end = System.currentTimeMillis();
        System.out.println("Records written in " + (end-start) + "ms");
    }
}
