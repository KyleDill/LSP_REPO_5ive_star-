package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for one thing: reading the raw employees.csv file and
 * turning valid rows into Employee objects. It knows the file format
 * and validation rules, but nothing about payroll math or output
 * formatting.
 *
 * Tracks how many rows were read and how many were skipped so the
 * caller can report the run summary without re-deriving it.
 */
public class EmployeeCsvReader {

    private int rowsRead;
    private int rowsSkipped;

    public List<Employee> read(String path) throws IOException {
        List<Employee> employees = new ArrayList<>();
        rowsRead = 0;
        rowsSkipped = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isHeaderRow = true;

            while ((line = reader.readLine()) != null) {
                if (isHeaderRow) {
                    isHeaderRow = false;
                    continue;
                }

                rowsRead++;

                Employee employee = parseLine(line);
                if (employee == null) {
                    rowsSkipped++;
                } else {
                    employees.add(employee);
                }
            }
        }

        return employees;
    }

    /**
     * Parses and validates a single data row.
     * Returns null if the row should be skipped for any reason.
     */
    private Employee parseLine(String line) {
        if (line.trim().isEmpty()) {
            return null;
        }

        String[] fields = line.split(",", -1);
        if (fields.length != 5) {
            return null;
        }

        String rawId = fields[0].trim();
        String rawName = fields[1].trim();
        String rawDept = fields[2].trim();
        String rawHours = fields[3].trim();
        String rawRate = fields[4].trim();

        int employeeId;
        double hoursWorked;
        double hourlyRate;
        try {
            employeeId = Integer.parseInt(rawId);
            hoursWorked = Double.parseDouble(rawHours);
            hourlyRate = Double.parseDouble(rawRate);
        } catch (NumberFormatException e) {
            return null;
        }

        if (hoursWorked < 0 || hourlyRate < 0) {
            return null;
        }

        return new Employee(employeeId, rawName, rawDept, hoursWorked, hourlyRate);
    }

    public int getRowsRead() {
        return rowsRead;
    }

    public int getRowsSkipped() {
        return rowsSkipped;
    }
}
