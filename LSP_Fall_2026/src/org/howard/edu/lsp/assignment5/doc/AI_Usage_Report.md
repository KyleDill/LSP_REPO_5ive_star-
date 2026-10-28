# AI Usage Report — Assignment 5

I used Claude (Anthropic) to help implement `IntegerSet.java`.

Full conversation transcript: https://claude.ai/share/53d57cd2-f212-4293-9a83-666488f9faf5

How it helped: Claude implemented `IntegerSet.java` backed internally by
a `java.util.TreeSet<Integer>`, which handles de-duplication and
ascending ordering automatically and keeps `toString()`,
`largest()`/`smallest()`, and the set-algebra operations
(`union`, `intersect`, `diff`, `complement`, `symmetricDiff`) concise.
It also implemented the assignment-specific `complement(b)` definition
(elements in `b` not in the current set, i.e. the reverse of the usual
difference) per the assignment's explicit example, compiled the result,
and ran it against the instructor-provided `Driver.java` without
modification, confirming `FUNCTIONAL SCORE: 92/92` and `TESTS PASSED:
47/47`.

No internet/external website references were used beyond the standard
Java Collections Framework documentation referenced in the assignment's
own implementation hint.