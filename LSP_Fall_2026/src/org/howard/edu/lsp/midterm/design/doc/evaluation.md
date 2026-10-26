# Design Evaluation — CourseRegistration

## 1. All data should be hidden within its class (encapsulation)
`studentId`, `studentName`, `email`, `courseCode`, and `creditHours` are
all `public` fields with no accessors or invariant checking. Any outside
code can read or overwrite them directly, including setting
`creditHours` to a negative number or changing `studentId` after the
class has already written a registration record to disk. Riel's
encapsulation heuristic requires that an object's data only be reachable
through its own methods, both so the class can enforce its own
invariants and so its internal representation can change without
breaking every caller that currently reaches into these fields directly.

## 2. A class should capture one, and only one, key abstraction (single responsibility / cohesion)
`registerStudent()` is not registration logic — it is five unrelated
responsibilities glued into one method: calculating tuition, printing a
confirmation to the console, writing to a hard-coded file, "sending" a
confirmation email (also via `System.out.println`), and logging a
timestamp. A class and method should model one key abstraction; here,
`CourseRegistration` is simultaneously a data holder, a pricing engine,
a persistence layer, a notification service, and a logger. This is the
textbook "God method" problem Riel warns against: responsibilities that
belong to separate collaborating objects have instead been flattened
into a single procedure, which makes the class hard to test (you cannot
verify tuition math without also triggering file I/O and console output)
and hard to reason about in isolation.

## 3. Minimize coupling to concrete, low-level classes
The method is tightly coupled to `java.io.FileWriter` and a literal
filename (`"registrations.txt"`). If the storage mechanism ever needs to
change — a database, a different file format, a remote service — every
line of this class must be edited, because nothing in the design
separates "what a registration needs to do" from "how it happens to be
persisted right now." The same is true of the "send confirmation email"
step, which is not actually implemented against any email service at
all; it is a placeholder `println`, meaning the class currently violates
its own stated responsibility without any class boundary that would
make that discoverable through types or tests.

## 4. Avoid hard-coded, unencapsulated business rules
The tuition rate (`750.0` per credit hour) and the heavy-load discount
threshold and rate (`creditHours >= 12`, `0.90`) are magic numbers
embedded directly in the method body. Riel's heuristics stress that
business rules like a pricing formula are themselves a abstraction
worth modeling — hiding them inside an unrelated method makes them
invisible to anyone trying to find "the tuition calculation" in the
system, and guarantees the rule will be duplicated or re-derived
incorrectly the next time it's needed elsewhere.

## 5. Exception handling hides failures rather than surfacing them
The `catch (Exception e)` block around the file write catches every
possible exception (including unrelated runtime errors) and only calls
`e.printStackTrace()`, silently continuing execution and still printing
"Sending confirmation email..." even if the save to disk failed. A
well-designed class should let its caller know when an operation didn't
complete, rather than reporting partial success as if everything
succeeded.

## Why these issues matter
Individually, each issue reduces one of correctness, maintainability, or
extensibility: public fields remove the ability to validate or evolve
internal state; the God-method design makes the class impossible to unit
test piece by piece; the hard-coded `FileWriter` and business constants
mean any requirements change (a new storage method, a new discount rule,
a new notification channel — exactly what Question 2 Part 3 asks about)
requires editing this one already-overloaded class rather than adding a
new collaborator beside it.