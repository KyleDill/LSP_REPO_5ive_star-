# Proposed Design — CRC Cards

Class: Student
Responsibilities: Store a student's identifying information (studentId, name, email)
Collaborators: None

---

Class: Course
Responsibilities: Store course information (courseCode, creditHours)
Collaborators: None

---

Class: TuitionCalculator
Responsibilities: Calculate tuition for a given number of credit hours, including the heavy-course-load discount rule
Collaborators: Course

---

Class: Registration
Responsibilities: Represent one student's registration in one course; coordinate calculating tuition, persisting the record, and triggering a confirmation, by delegating to its collaborators
Collaborators: Student, Course, TuitionCalculator, RegistrationRepository, NotificationService

---

Class: RegistrationRepository
Responsibilities: Define how a completed registration is saved, without specifying where
Collaborators: Registration

---

Class: NotificationService
Responsibilities: Define how a registration confirmation is delivered to the student, without specifying the channel
Collaborators: Registration, Student

---

Class: RegistrationLogger
Responsibilities: Record that a registration was processed, with a timestamp, independent of how it was stored or confirmed
Collaborators: Registration