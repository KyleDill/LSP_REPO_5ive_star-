package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.List;

/**
 * CSCI 363 - Assignment 3
 * Employee Payroll ETL Pipeline (object-oriented redesign of Assignment #2)
 *
 * This class no longer contains any file-parsing or payroll-calculation
 * logic itself. It only orchestrates the three collaborators:
 *   - EmployeeCsvReader:   Extract  (reads + validates the input file)
 *   - PayrollCalculator:   Transform (applies payroll business rules)
 *   - EmployeeCsvWriter:   Load     (writes the output file)
 *
 * This mirrors the Extract-Transform-Load steps the assignment is named
 * for, with each step now owned by a dedicated class instead of being
 * inlined in one long main method.
 */
public class ETLPipeline {

    private static final String INPUT_PATH = "data/employees.csv";
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    public static void main(String[] args) {
        EmployeeCsvReader reader = new EmployeeCsvReader();
        PayrollCalculator calculator = new PayrollCalculator();
        EmployeeCsvWriter writer = new EmployeeCsvWriter();

        List<Employee> employees;
        try {
            employees = reader.read(INPUT_PATH);
        } catch (IOException e) {
            System.out.println("Error reading input file '" + INPUT_PATH + "': " + e.getMessage());
            return;
        }

        for (Employee employee : employees) {
            calculator.process(employee);
        }

        try {
            writer.write(OUTPUT_PATH, employees);
        } catch (IOException e) {
            System.out.println("Error writing output file '" + OUTPUT_PATH + "': " + e.getMessage());
            return;
        }

        System.out.println("Rows read: " + reader.getRowsRead());
        System.out.println("Rows transformed: " + employees.size());
        System.out.println("Rows skipped: " + reader.getRowsSkipped());
        System.out.println("Output file: " + OUTPUT_PATH);
    }
}