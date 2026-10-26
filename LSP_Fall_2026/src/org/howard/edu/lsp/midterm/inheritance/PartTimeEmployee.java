package org.howard.edu.lsp.midterm.inheritance;

/**
 * A part-time employee, paid straight hourly pay with no overtime
 * premium regardless of hours worked.
 *
 * @author Kyle Dill
 */
public class PartTimeEmployee extends Employee {

    /**
     * Creates a PartTimeEmployee with the given hours worked and hourly
     * rate.
     *
     * @param hoursWorked the number of hours worked
     * @param hourlyRate  the base hourly pay rate
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay as straight hourly pay: hours worked times the
     * hourly rate, with no overtime premium.
     *
     * @return the calculated pay
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate();
    }
}