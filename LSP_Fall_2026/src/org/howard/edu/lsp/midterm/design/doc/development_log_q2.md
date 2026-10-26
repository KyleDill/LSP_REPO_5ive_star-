# Development Log — Question 2 (Evaluating Object-Oriented Design)

## AI Assistance

I used Claude (Anthropic) to help evaluate the design issues in the
provided `CourseRegistration` class against Riel's object-oriented
design heuristics, to propose a redesigned set of CRC cards, and to work
through how that design would accommodate the evolving storage and
notification requirements in Part 3.

Full conversation transcript: https://claude.ai/share/53d57cd2-f212-4293-9a83-666488f9faf5

How it helped: Claude helped identify and articulate specific design
heuristic violations in the provided class (public data members,
single-responsibility/cohesion violations, tight coupling to a concrete
`FileWriter` and hard-coded business constants, and exception handling
that silently swallows failures), and helped structure the proposed CRC
cards so that storage and notification responsibilities are isolated
behind their own collaborator classes.

## Internet Resources

[LIST ANY URLS YOU ACTUALLY USED, OR DELETE THIS SECTION IF NONE]

## Other Resources

Course lecture notes and slides on Arthur Riel's object-oriented design
heuristics and CRC card methodology.