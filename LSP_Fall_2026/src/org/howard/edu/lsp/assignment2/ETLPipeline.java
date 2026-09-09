package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * CSCI 363 - Assignment 2
 * Employee Payroll ETL Pipeline
 * Kyle Dillon
 *
 * Reads data/employees.csv, applies the required transformations,
 * and writes data/transformed_employees.csv.
 */
public class ETLPipeline {

    private static final String INPUT_PATH = "data/employees.csv";
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    public static void main(String[] args) {

        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        List<String> outputLines = new ArrayList<>();
        outputLines.add("EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus");

        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_PATH))) {
            String line;
            boolean isHeaderRow = true;

            while ((line = reader.readLine()) != null) {

                // The header row is never counted or transformed.
                if (isHeaderRow) {
                    isHeaderRow = false;
                    continue;
                }

                rowsRead++;

                // Rule: skip blank lines
                if (line.trim().isEmpty()) {
                    rowsSkipped++;
                    continue;
                }

                // Split on comma, keeping empty trailing fields
                String[] fields = line.split(",", -1);

                // Rule: must have exactly 5 fields
                if (fields.length != 5) {
                    rowsSkipped++;
                    continue;
                }

                String rawId = fields[0].trim();
                String rawName = fields[1].trim();
                String rawDept = fields[2].trim();
                String rawHours = fields[3].trim();
                String rawRate = fields[4].trim();

                // Rule: EmployeeID must be an integer
                int employeeId;
                try {
                    employeeId = Integer.parseInt(rawId);
                } catch (NumberFormatException e) {
                    rowsSkipped++;
                    continue;
                }

                // Rule: HoursWorked and HourlyRate must be valid decimals
                double hoursWorked;
                double hourlyRate;
                try {
                    hoursWorked = Double.parseDouble(rawHours);
                    hourlyRate = Double.parseDouble(rawRate);
                } catch (NumberFormatException e) {
                    rowsSkipped++;
                    continue;
                }

                // Rule: neither may be negative
                if (hoursWorked < 0 || hourlyRate < 0) {
                    rowsSkipped++;
                    continue;
                }

                // ---- Row is valid: apply transformations ----

                // 1. Normalize
                String name = rawName.toUpperCase();
                String department = rawDept; // trimmed already, otherwise unchanged

                // 3. Base/overtime pay
                double grossPayRaw;
                if (hoursWorked <= 40.00) {
                    grossPayRaw = hoursWorked * hourlyRate;
                } else {
                    double regular = 40.00 * hourlyRate;
                    double overtime = (hoursWorked - 40.00) * hourlyRate * 1.5;
                    grossPayRaw = regular + overtime;
                }

                // 4. IT bonus (case-sensitive, applied after overtime)
                if (department.equals("IT")) {
                    grossPayRaw = grossPayRaw * 1.05;
                }

                // 5. Round to 2 decimals, round-half-up
                BigDecimal grossPay = BigDecimal.valueOf(grossPayRaw).setScale(2, RoundingMode.HALF_UP);

                // 6. PayLevel based on final rounded GrossPay
                double gp = grossPay.doubleValue();
                String payLevel;
                if (gp < 500.00) {
                    payLevel = "Low";
                } else if (gp < 1000.00) {
                    payLevel = "Standard";
                } else if (gp < 2000.00) {
                    payLevel = "High";
                } else {
                    payLevel = "Executive";
                }

                // 7. EmploymentStatus
                String employmentStatus = (hoursWorked < 30.00) ? "Part-Time" : "Full-Time";

                // Build output row (HoursWorked, HourlyRate, GrossPay always 2 decimals)
                String outRow = String.format(
                        "%d,%s,%s,%.2f,%.2f,%.2f,%s,%s",
                        employeeId, name, department, hoursWorked, hourlyRate, gp, payLevel, employmentStatus
                );

                outputLines.add(outRow);
                rowsTransformed++;
            }

        } catch (IOException e) {
            System.out.println("Error reading input file '" + INPUT_PATH + "': " + e.getMessage());
            return;
        }

        // ---- Load: write output file ----
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH))) {
            for (String outLine : outputLines) {
                writer.write(outLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing output file '" + OUTPUT_PATH + "': " + e.getMessage());
            return;
        }

        // ---- Run summary ----
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: " + OUTPUT_PATH);
    }
}
