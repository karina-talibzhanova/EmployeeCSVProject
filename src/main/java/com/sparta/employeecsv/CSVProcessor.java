package com.sparta.employeecsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CSVProcessor {
    private ArrayList<Employee> clean = new ArrayList<>();
    private ArrayList<Employee> duplicate;
    private ArrayList<Employee> corrupt;

    public ArrayList<Employee> getClean() {
        return clean;
    }

    public ArrayList<Employee> getDuplicate() {
        return duplicate;
    }

    public ArrayList<Employee> getCorrupt() {
        return corrupt;
    }

    public int readFile(String fileName) {
        String line;
        int linesRead = -1;
        // read each line in the file - send the processing of each line elsewhere
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            in.readLine();  // don't care about the headings, move pointer down one
            linesRead++;  // increment so we start count at 0
            while ((line = in.readLine()) != null) {
                linesRead++;
                // pass the line off somewhere
                parseLine(line);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
        return linesRead;
    }

    public boolean parseLine(String line) {
        // determine if the line contains a valid record and add to appropriate collection
        String[] csv = line.split(",");
        // if the line given doesn't have exactly 10 entries, record is incorrectly formatted
        if (csv.length != 10) {
            return false;
        } else {
            // convert to Employee object
            Employee employee = new Employee();
            employee.setEmployeeID(Integer.parseInt(csv[0]));
            employee.setNamePrefix(csv[1]);
            employee.setFirstName(csv[2]);
            employee.setMiddleInitial(csv[3].charAt(0));
            employee.setLastName(csv[4]);
            employee.setGender(csv[5].charAt(0));
            employee.setEmail(csv[6]);

            try {
                java.util.Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(csv[7]);
                java.sql.Date sqlDob = new java.sql.Date(dob.getTime());
                employee.setDateOfBirth(sqlDob);

                java.util.Date doj = new SimpleDateFormat("MM/dd/yyyy").parse(csv[8]);
                java.sql.Date sqlDoj = new java.sql.Date(doj.getTime());
                employee.setDateOfJoining(sqlDoj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            employee.setSalary(Integer.parseInt(csv[9]));

            // add employee object to array
            clean.add(employee);
            return true;
        }
    }
}
