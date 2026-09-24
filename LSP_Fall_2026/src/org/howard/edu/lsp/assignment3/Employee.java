package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Kyle Dillon
 * Represents one employee payroll record.
 *
 * Holds the raw fields read from the source CSV (id, name, department,
 * hoursWorked, hourlyRate) plus the derived fields produced by
 * {@link PayrollCalculator} (grossPay, payLevel, employmentStatus).
 *
 * The class only knows how to hold and format its own data; it does not
 * know how to read a CSV file or how payroll rules are computed. That
 * keeps a single, clear responsibility for this class: represent one
 * employee record.
 */
public class Employee {

    private final int employeeId;
    private String name;
    private final String department;
    private final double hoursWorked;
    private final double hourlyRate;

    // Populated later by PayrollCalculator, once validation/parsing has succeeded.
    private BigDecimal grossPay;
    private String payLevel;
    private String employmentStatus;

    public Employee(int employeeId, String name, String department,
                     double hoursWorked, double hourlyRate) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(BigDecimal grossPay) {
        this.grossPay = grossPay;
    }

    public String getPayLevel() {
        return payLevel;
    }

    public void setPayLevel(String payLevel) {
        this.payLevel = payLevel;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    /**
     * Formats this (already-transformed) employee as one output CSV row.
     * Only valid to call after PayrollCalculator has populated grossPay,
     * payLevel, and employmentStatus.
     */
    public String toCsvRow() {
        return String.format(
                "%d,%s,%s,%.2f,%.2f,%.2f,%s,%s",
                employeeId, name, department, hoursWorked, hourlyRate,
                grossPay.doubleValue(), payLevel, employmentStatus
        );
    }
}
