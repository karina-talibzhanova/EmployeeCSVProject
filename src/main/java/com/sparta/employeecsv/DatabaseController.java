package com.sparta.employeecsv;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseController implements Runnable {
    private ArrayList<Employee> employees;
    private int lower;
    private int upper;

    public DatabaseController(ArrayList<Employee> employees, int lower, int upper) {
        this.employees = employees;
        this.lower = lower;
        this.upper = upper;
    }

    public DatabaseController() {}

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    public static void createTable() {
        // drop existing table (if there is one)
        // create new table (unless there already is one)
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", System.getenv("root"))) {
            Statement dropTable = conn.createStatement();
            dropTable.executeUpdate("DROP TABLE IF EXISTS employees");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int write() {
        int rowsWritten = 0;

        // write in the employee details
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", System.getenv("root"))) {

            PreparedStatement writeEmployee = conn.prepareStatement("INSERT INTO employees(employeeID, namePrefix," +
                                                                        "firstName, middleInitial, lastName, gender," +
                                                                        "email, dateOfBirth, dateOfJoining, salary)" +
                                                                        "VALUES(?,?,?,?,?,?,?,?,?,?)");
            for (int i = lower; i < upper; i++) {
                writeEmployee.setInt(1, employees.get(i).getEmployeeID());
                writeEmployee.setString(2, employees.get(i).getNamePrefix());
                writeEmployee.setString(3, employees.get(i).getFirstName());
                writeEmployee.setString(4, employees.get(i).getMiddleInitial());
                writeEmployee.setString(5, employees.get(i).getLastName());
                writeEmployee.setString(6, employees.get(i).getGender());
                writeEmployee.setString(7, employees.get(i).getEmail());
                writeEmployee.setDate(8, employees.get(i).getDateOfBirth());
                writeEmployee.setDate(9, employees.get(i).getDateOfJoining());
                writeEmployee.setInt(10, employees.get(i).getSalary());

                writeEmployee.execute();
                rowsWritten++;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsWritten;
    }

    @Override
    public void run() {
        write();
    }
}
