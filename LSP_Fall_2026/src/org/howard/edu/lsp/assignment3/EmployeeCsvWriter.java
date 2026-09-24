package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Responsible for one thing: writing a list of already-transformed
 * Employee objects to the output CSV file, including the header row.
 */
public class EmployeeCsvWriter {

    private static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    public void write(String path, List<Employee> employees) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(HEADER);
            writer.newLine();

            for (Employee employee : employees) {
                writer.write(employee.toCsvRow());
                writer.newLine();
            }
        }
    }
}