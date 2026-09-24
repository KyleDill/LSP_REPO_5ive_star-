package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Responsible for one thing: applying the payroll transformation rules
 * to a single Employee (name normalization, overtime pay, IT bonus,
 * rounding, pay level, employment status).
 *
 * Keeping this separate from Employee and EmployeeCsvReader means the
 * payroll *rules* live in exactly one place. If the business rules
 * change (say, the IT bonus percentage), only this class needs to
 * change.
 */
public class PayrollCalculator {

    private static final double OVERTIME_THRESHOLD = 40.00;
    private static final double OVERTIME_MULTIPLIER = 1.5;
    private static final double IT_BONUS_MULTIPLIER = 1.05;
    private static final double PART_TIME_THRESHOLD = 30.00;

    /**
     * Mutates the given Employee in place, populating its derived
     * fields (name normalization, grossPay, payLevel, employmentStatus).
     */
    public void process(Employee employee) {
        employee.setName(employee.getName().toUpperCase());

        double grossPayRaw = calculateBasePay(employee.getHoursWorked(), employee.getHourlyRate());

        if (employee.getDepartment().equals("IT")) {
            grossPayRaw *= IT_BONUS_MULTIPLIER;
        }

        BigDecimal grossPay = BigDecimal.valueOf(grossPayRaw).setScale(2, RoundingMode.HALF_UP);
        employee.setGrossPay(grossPay);

        employee.setPayLevel(determinePayLevel(grossPay.doubleValue()));
        employee.setEmploymentStatus(
                employee.getHoursWorked() < PART_TIME_THRESHOLD ? "Part-Time" : "Full-Time"
        );
    }

    private double calculateBasePay(double hoursWorked, double hourlyRate) {
        if (hoursWorked <= OVERTIME_THRESHOLD) {
            return hoursWorked * hourlyRate;
        }
        double regular = OVERTIME_THRESHOLD * hourlyRate;
        double overtime = (hoursWorked - OVERTIME_THRESHOLD) * hourlyRate * OVERTIME_MULTIPLIER;
        return regular + overtime;
    }

    private String determinePayLevel(double grossPay) {
        if (grossPay < 500.00) {
            return "Low";
        } else if (grossPay < 1000.00) {
            return "Standard";
        } else if (grossPay < 2000.00) {
            return "High";
        } else {
            return "Executive";
        }
    }
}