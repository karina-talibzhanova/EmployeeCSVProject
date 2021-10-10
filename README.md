# Data Migration Project

## Purpose
The aim of this project is to be able to read in data from a CSV file, parse it and check for inaccuracies,
 create objects from the data, and persist the data from the objects to a database using JDBC.

## Current state of the project
There are two main functions of the project - reading in the data and persisting it to a database.
### Reading from CSV
Given a CSV file, the project reads it in line by line and checks whether the data is valid. The checks for validity are 
limited in scope and encompass things such as missing data, invalid types (e.g. a string in an integer field) and data length 
(e.g. expecting a single character but receiving a whole string). If a record is valid, the data is used to create an 
Employee object. This object is then stored in an ArrayList, ready to be written to the database.

However, if the data is invalid, the string representing the line from the CSV file is added to a separate collection. There 
are two collections - one for duplicate data and one for faulty data. A record is deemed a duplicate if the employee ID 
matches one that already exists in the valid collection. A record is deemed faulty if it fails any validity checks.

### Writing to the database
The database used is MySQL as this allows for multithreading, whereas SQLite does not. Only valid data is written to the 
database. This data is extracted from the Employee objects. The project supports multithreading - each thread deals with 
a separate chunk of Employee objects, ensuring there is no overlap between threads.

## Usage instructions
To be able to use this project, you must download MySQL - this project used the [MySQL Community Server](https://dev.mysql.com/downloads/mysql/). 
You will then need to supply your own username and password to the database connections (see [here](https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html) 
on how to add environment variables to IntelliJ). Beyond that, there is no further installation needed. Changes to the CSV file 
being read in or the number of threads used when writing to the database can only currently be done in the code.

## Further development
Currently, validation of the data is very rudimentary - this could be improved with more rigorous checks using regex (which 
would necessitate using regex patterns to prevent a bottleneck being formed).  
The duplicated and faulty records could be output to a file, allowing for the user to review the data and make any amends.  
Writing to the database still takes a substantial amount of time for 65,000+ records (1 thread: 10 minutes, 2 threads: 7 minutes, 
4 threads: 4 minutes, 8 threads: 2 minutes, 16 threads: 38 seconds, 32 threads: 27 seconds). One way to speed this up is to execute the SQL commands in a batch as opposed 
to one by one.  
The project could be refactored to make use of the [Producer-Consumer pattern](https://en.wikipedia.org/wiki/Producer%E2%80%93consumer_problem) 
to more easily balance the workload between threads. This also allows for any threads that have finished early to pick up any extra 
work instead of idling and waiting for the other threads to finish.