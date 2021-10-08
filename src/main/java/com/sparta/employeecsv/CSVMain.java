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
        System.out.println("Writing to database...");

        DatabaseController.createTable();  // drops existing table, creates new one

        // prepare my threads
        DatabaseController d1 = new DatabaseController();
        d1.setEmployees(processor.getClean());
        d1.setLower(0);
        d1.setUpper(processor.getClean().size()/2);

        DatabaseController d2 = new DatabaseController();
        d2.setEmployees(processor.getClean());
        d2.setLower(processor.getClean().size()/2);
        d2.setUpper(processor.getClean().size());

        Thread t1 = new Thread(d1);
        Thread t2 = new Thread(d2);

        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Records written in " + (end-start) + "ms");
    }
}
