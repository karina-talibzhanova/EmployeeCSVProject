package com.sparta.employeecsv;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ThreadControllerShould {
    private static ArrayList<Employee> employees = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        // create ArrayList of Employee objects
        for (int i = 0; i < 100; i ++) {
            employees.add(new Employee());
        }
    }

    @Test
    public void givenInputEight_CreateEightThreads() {
        int numThreads = 8;
        ThreadController tc = new ThreadController(numThreads, employees);
        tc.createThreads();
        assertEquals(numThreads, tc.getThreads().size());
    }
}
