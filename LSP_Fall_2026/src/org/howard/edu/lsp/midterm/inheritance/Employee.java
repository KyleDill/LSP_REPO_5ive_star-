package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class for an employee whose pay is calculated from
 * hours worked and an hourly rate. Subclasses override
 * {@link #calculatePay()} to apply their own pay rule (straight pay,
 * overtime, or a flat premium), which replaces the original
 * conditional-on-employeeType design with polymorphism: the Driver
 * calls {@code calculatePay()} on an {@code Employee} reference without
 * needing to know which concrete subclass it holds.
 *
 * @author Kyle Dill
 */
public abstract class Employee {

    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Creates an Employee with the given hours worked and hourly rate.
     * Both values are assumed to be nonnegative.
     *
     * @param hoursWorked the number of hours worked
     * @param hourlyRate  the base hourly pay rate
     */
    protected Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the number of hours this employee worked.
     *
     * @return hours worked
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns this employee's base hourly pay rate.
     *
     * @return the hourly rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Calculates this employee's pay according to the pay rule for this
     * employee's concrete type.
     *
     * @return the calculated pay
     */
    public abstract double calculatePay();
}