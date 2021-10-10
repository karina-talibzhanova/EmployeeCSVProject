package com.sparta.employeecsv;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ThreadController {
    // purpose of this class is to be able to create as many threads as required automatically
    // i.e. i give input of 8, array is split into 8 even-ish chunks and 8 threads are created
    private int numThreads;
    private ArrayList<Employee> employees;
    private ArrayList<Thread> threads = new ArrayList<>();

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public ThreadController(int numThreads, ArrayList<Employee> employees) {
        this.numThreads = numThreads;
        this.employees = employees;
    }

    public void createThreads() {
        // create n DatabaseController objects
        // set the upper and lower bounds
        // create Thread instance
        // add Thread instance to arraylist
        int employeeNum = employees.size();
        int lower = 0;
        int upper = employeeNum/numThreads;

        for (int i = 0; i < numThreads; i++) {
            DatabaseController dbController = new DatabaseController(employees, lower, upper);
            Thread t = new Thread(dbController);
            threads.add(t);

            // new lower bound now becomes previous upper bound
            lower = upper;
            // upper bound shifts by employeeNum/numThreads
            // need to be careful that upper does not exceed employees.size()
            // also need to keep in mind that the last chunk of data to be processed may be bigger
            upper = employeeNum - (upper + employeeNum/numThreads) > employeeNum/numThreads ? Math.min(upper + employeeNum/numThreads, employeeNum) : employeeNum;
        }
    }

    public void runThreads() {
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
