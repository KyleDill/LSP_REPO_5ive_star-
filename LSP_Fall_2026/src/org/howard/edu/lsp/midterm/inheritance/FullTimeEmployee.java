package org.howard.edu.lsp.midterm.inheritance;

/**
 * A full-time employee. Hours up to and including 40 are paid at the
 * base hourly rate; any hours beyond 40 are paid at 1.5 times the
 * hourly rate (overtime).
 *
 * @author Kyle Dill
 */
public class FullTimeEmployee extends Employee {

    private static final double OVERTIME_THRESHOLD = 40.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    /**
     * Creates a FullTimeEmployee with the given hours worked and hourly
     * rate.
     *
     * @param hoursWorked the number of hours worked
     * @param hourlyRate  the base hourly pay rate
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay with overtime: the first 40 hours at the base rate,
     * and any hours beyond 40 at 1.5 times the base rate.
     *
     * @return the calculated pay, including overtime if applicable
     */
    @Override
    public double calculatePay() {
        double hours = getHoursWorked();
        double rate = getHourlyRate();

        if (hours <= OVERTIME_THRESHOLD) {
            return hours * rate;
        }
        double regularPay = OVERTIME_THRESHOLD * rate;
        double overtimePay = (hours - OVERTIME_THRESHOLD) * rate * OVERTIME_MULTIPLIER;
        return regularPay + overtimePay;
    }
}