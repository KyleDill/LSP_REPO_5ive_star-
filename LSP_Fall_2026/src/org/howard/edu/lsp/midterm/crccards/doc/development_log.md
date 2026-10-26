# Development Log — Question 1 (CRC Cards: Library Lending System)

## AI Assistance

I used Claude (Anthropic) to help implement `Book.java` and
`Library.java` from the CRC card descriptions and provided Driver, and
to draft the `crccards.md` collaboration/limit explanation.

Full conversation transcript: https://claude.ai/share/53d57cd2-f212-4293-9a83-666488f9faf5

How it helped: Claude translated the CRC card responsibilities and
business rules (duplicate-ISBN rejection, the three-book borrowing
limit, delegation of state changes from Library to Book, the exact
`toString()` format) into a working `Book`/`Library` implementation,
compiled and ran it against the instructor-provided `Driver.java`
without modification, and confirmed all 44 checks pass.

## Internet Resources



## Other Resources

Course lecture notes on CRC cards, encapsulation, and the Java
Collections Framework (`LinkedHashMap`, `Comparator`).