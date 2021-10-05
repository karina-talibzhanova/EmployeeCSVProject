package com.sparta.employeecsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVProcessor {
    private String fileName;
    private ArrayList<Employee> clean;
    private ArrayList<Employee> duplicate;
    private ArrayList<Employee> corrupt;

    public CSVProcessor(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Employee> getClean() {
        return clean;
    }

    public ArrayList<Employee> getDuplicate() {
        return duplicate;
    }

    public ArrayList<Employee> getCorrupt() {
        return corrupt;
    }

    public void readFile() {
        String line;
        // read each line in the file - send the processing of each line elsewhere
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            while ((line = in.readLine()) != null) {
                // pass the line off somewhere
                System.out.println(line);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
