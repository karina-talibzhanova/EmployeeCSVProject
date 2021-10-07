package com.sparta.employeecsv;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {
    public static int write(ArrayList<Employee> employees) {
        int rowsWritten = 0;

        // create a database connection
        // create the columns names
        // write in the employee details
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:employee.db")) {
            Statement writeColumns = conn.createStatement();
            writeColumns.executeUpdate("CREATE TABLE IF NOT EXISTS employees(" +
                                            "employeeID INTEGER PRIMARY KEY," +
                                            "namePrefix TEXT," +
                                            "firstName TEXT," +
                                            "middleInitial TEXT," +
                                            "lastName TEXT," +
                                            "gender TEXT," +
                                            "email TEXT," +
                                            "dateOfBirth DATE," +
                                            "dateOfJoining DATE," +
                                            "salary INTEGER);");

            PreparedStatement writeEmployee = conn.prepareStatement("INSERT INTO employees(employeeID, namePrefix," +
                                                                        "firstName, middleInitial, lastName, gender," +
                                                                        "email, dateOfBirth, dateOfJoining, salary)" +
                                                                        "VALUES(?,?,?,?,?,?,?,?,?,?)");
            for (Employee employee : employees) {
                writeEmployee.setInt(1, employee.getEmployeeID());
                writeEmployee.setString(2, employee.getNamePrefix());
                writeEmployee.setString(3, employee.getFirstName());
                writeEmployee.setString(4, employee.getMiddleInitial());
                writeEmployee.setString(5, employee.getLastName());
                writeEmployee.setString(6, employee.getGender());
                writeEmployee.setString(7, employee.getEmail());
                writeEmployee.setDate(8, employee.getDateOfBirth());
                writeEmployee.setDate(9, employee.getDateOfJoining());
                writeEmployee.setInt(10, employee.getSalary());

                writeEmployee.execute();
                rowsWritten++;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsWritten;
    }
}
