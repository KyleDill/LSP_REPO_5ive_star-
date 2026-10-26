package org.howard.edu.lsp.midterm.inheritance;

/**
 * A contractor, paid a flat 15% premium on all hours worked (no
 * separate overtime rule; the premium applies uniformly).
 *
 * @author Kyle Dillon
 */
public class Contractor extends Employee {

    private static final double CONTRACTOR_PREMIUM_MULTIPLIER = 1.15;

    /**
     * Creates a Contractor with the given hours worked and hourly rate.
     *
     * @param hoursWorked the number of hours worked
     * @param hourlyRate  the base hourly pay rate
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay as hours worked times the hourly rate, with a flat
     * 15% premium applied to all hours.
     *
     * @return the calculated pay, including the contractor premium
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate() * CONTRACTOR_PREMIUM_MULTIPLIER;
    }
}