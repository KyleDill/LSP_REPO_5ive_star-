Design Discussion — Assignment 3
How was the Assignment #2 solution organized?

Assignment #2 was implemented as a single class, ETLPipeline, with one long main method. That method was responsible for everything at once: opening and reading the file line by line, splitting and validating each field, applying every payroll rule (overtime, IT bonus, rounding, pay level, employment status), formatting the output row, writing the output file, and printing the run summary. There was no separation between "what an employee record is," "how to read one," "how to calculate pay," and "how to write the result" — all of it lived in one procedural block.

What design changes did I make for Assignment #3?

I split that single method into four collaborating classes, each with one responsibility:

Employee — a plain data model representing one employee record, both its raw input fields and its derived output fields (grossPay, payLevel, employmentStatus). It knows how to format itself as an output CSV row, but nothing about file I/O or payroll rules.
EmployeeCsvReader — owns reading employees.csv, validating each row, and turning valid rows into Employee objects. It also tracks the rows-read/rows-skipped counts, since it's the only class that knows why a row was rejected.
PayrollCalculator — owns the payroll business rules: name normalization, overtime pay, the IT department bonus, rounding, pay level thresholds, and employment status. It knows nothing about CSV parsing or file output.
EmployeeCsvWriter — owns writing the header and transformed rows to the output file.
ETLPipeline — no longer contains any parsing or calculation logic. It only creates the three collaborators, calls them in order (Extract → Transform → Load), and prints the run summary.
What classes/abstractions did I introduce and why?

Employee is the main new abstraction: previously an employee was just a String[] array or a set of local variables passed around implicitly. Making it a real object means every field has a name and a type, and the formatting logic for an output row lives next to the data it formats, rather than as a String.format call buried in main.

EmployeeCsvReader, PayrollCalculator, and EmployeeCsvWriter map directly onto the Extract / Transform / Load steps the assignment is named for. Each one is small enough to read, test, and change in isolation — for example, if a future assignment changed the overtime rate or added a new department bonus, only PayrollCalculator would need to change.

How did I divide responsibilities differently?

In Assignment #2, one method knew about file paths, CSV syntax, validation rules, payroll math, and output formatting all at once. In Assignment #3, each of those concerns belongs to exactly one class, and ETLPipeline itself is reduced to orchestration — it wires the pieces together but doesn't implement any of the actual logic.

Why do I believe the Assignment #3 design is an improvement?

The classes are individually easier to understand and test in isolation (for example, PayrollCalculator.process() can be tested against a single Employee object without touching any file I/O). The design also isolates change: a change to validation rules only touches EmployeeCsvReader, and a change to payroll rules only touches PayrollCalculator, whereas in Assignment #2 any change meant editing the same single method that did everything else too.

AI and Internet Resources
https://claude.ai/share/53d57cd2-f212-4293-9a83-666488f9faf5
I used AI to make the code for this project
