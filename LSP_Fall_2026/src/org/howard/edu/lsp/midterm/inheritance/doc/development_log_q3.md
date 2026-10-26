# Development Log — Question 3 (Refactoring with Inheritance and Polymorphism)

## AI Assistance

I used Claude (Anthropic) to help refactor the conditional-based
`EmployeePayCalculator.calculatePay(employeeType, ...)` method into an
`Employee` class hierarchy (`Employee` abstract base class,
`FullTimeEmployee`, `PartTimeEmployee`, `Contractor`) using inheritance,
method overriding, and polymorphism, per the provided Driver's
constructors and method signatures.

Full conversation transcript: https://claude.ai/share/53d57cd2-f212-4293-9a83-666488f9faf5

How it helped: Claude translated the pay rules described in the
assignment (full-time overtime at 1.5x past 40 hours, part-time straight
pay, contractor flat 15% premium on all hours) into the overridden
`calculatePay()` methods, compiled the result, and ran it against the
instructor-provided `Driver.java` without modification, confirming all
12 checks pass with `RESULT: 12 PASSED, 0 FAILED`.

## Internet Resources

[LIST ANY URLS YOU ACTUALLY USED, OR DELETE THIS SECTION IF NONE]

## Other Resources

Course lecture notes on inheritance, method overriding, and
polymorphism.