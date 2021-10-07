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
    private ArrayList<String> duplicate = new ArrayList<>();
    private ArrayList<String> faulty = new ArrayList<>();

    public ArrayList<Employee> getClean() {
        return clean;
    }

    public ArrayList<String> getDuplicate() {
        return duplicate;
    }

    public ArrayList<String> getFaulty() {
        return faulty;
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

        if (!isRecordValid(csv)) {
            faulty.add(line);
            return false;
        } else {
            // create an Employee object
            Employee employee = createEmployee(csv);

            // check if Employee object already exists in clean (i.e. is there a matching id?)
            if (clean.contains(employee)) {
                duplicate.add(line);
                return false;
            } else {
                clean.add(employee);
                return true;
            }
        }
    }

    public boolean isRecordValid(String[] record) {
        // if the line given doesn't have exactly 10 entries, record is incorrectly formatted
        if (record.length != 10) {
            return false;
        }
        // check if all array elements actually contain a value - either empty or just whitespace
        for (String elem : record) {
            if (elem.isBlank()) {
                return false;
            }
        }
        // only will check if employeeId is int, middle initial + gender are exactly 1 char, dates are dates, and salary is positive int
        try {
            Integer.parseInt(record[0]);  // test if employeeId is int
            Integer.parseInt(record[9]);  // test if salary is int

            new SimpleDateFormat("MM/dd/yyyy").parse(record[7]);  // test if date of birth string can be parsed into Date
            new SimpleDateFormat("MM/dd/yyyy").parse(record[8]);  // test if date of joining string can be parsed into Date
        } catch (NumberFormatException | ParseException e) {
            return false;
        }

        // test if middle initial + gender are exactly 1 character
        if (record[3].length() > 1 || record[5].length() > 1) {
            return false;
        }
        // test if salary is a positive integer
        if (Integer.parseInt(record[9]) < 0) {
            return false;
        }

        // if everything is okay, return true
        return true;
    }

    public Employee createEmployee(String[] record) {
        Employee employee = new Employee();

        employee.setEmployeeID(Integer.parseInt(record[0]));
        employee.setNamePrefix(record[1]);
        employee.setFirstName(record[2]);
        employee.setMiddleInitial(record[3].charAt(0));
        employee.setLastName(record[4]);
        employee.setGender(record[5].charAt(0));
        employee.setEmail(record[6]);

        try {
            java.util.Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(record[7]);
            java.sql.Date sqlDob = new Date(dob.getTime());
            employee.setDateOfBirth(sqlDob);

            java.util.Date doj = new SimpleDateFormat("MM/dd/yyyy").parse(record[8]);
            java.sql.Date sqlDoj = new Date(doj.getTime());
            employee.setDateOfJoining(sqlDoj);
        } catch (ParseException e) {  // we shouldn't get an exception anyway bc this should all have been checked previously
            e.printStackTrace();
        }
        employee.setSalary(Integer.parseInt(record[9]));

        return employee;
    }
}
