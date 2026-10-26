# Design Rationale — Evolving Requirements

## Accommodating two storage options and two notification channels

The redesign already separates "persisting a registration" and
"notifying a student" into their own collaborator classes
(`RegistrationRepository`, `NotificationService`) rather than folding
them into `Registration` itself. To support a local-file or database
choice selected by deployment environment, `RegistrationRepository`
becomes an abstraction (an interface or abstract class) with two
concrete implementations — `FileRegistrationRepository` and
`DatabaseRegistrationRepository` — that both expose the same save
operation. Which concrete implementation is handed to `Registration` is
decided once, based on the deployment environment, outside of
`Registration` itself (e.g., wherever the application is assembled/
configured). The same change applies to `NotificationService`:
`EmailNotificationService` and `TextNotificationService` become two
implementations of the same abstraction, and `Registration` calls
whichever one it was given.

## Changes to the CRC cards
- `RegistrationRepository`'s card is updated to state explicitly that it
  represents a storage abstraction with interchangeable implementations
  (file-based or database-based), selected by deployment configuration.
- `NotificationService`'s card is updated the same way, for email vs.
  text message delivery.
- No new responsibility is added to `Registration` — its responsibility
  stays "coordinate a single registration by delegating to its
  collaborators," regardless of which concrete storage or notification
  implementation those collaborators happen to be.

## Design decision and justification
The one responsibility worth calling out explicitly: **deciding which
concrete `RegistrationRepository` and `NotificationService` to use
belongs outside `Registration`, not inside it.** `Registration`'s
responsibility (per its CRC card) is to represent one student's
registration and coordinate the steps involved — not to know about
deployment environments or configuration. If `Registration` itself
contained the logic to choose between file/database storage or
email/text notification, every future storage or notification option
would require editing `Registration`'s code directly, re-introducing the
exact problem identified in Part 1: a single class absorbing
responsibilities that belong to other collaborators. By keeping that
choice outside `Registration` and only handing it a
`RegistrationRepository`/`NotificationService` reference to use,
`Registration` stays unchanged no matter how many storage or
notification options the system eventually supports — it depends only
on the shared abstraction, not on any specific implementation. This is
consistent with the Part 2 design, where `Registration`'s collaborators
are already listed as `RegistrationRepository` and `NotificationService`
by name, not by a specific file- or email-based implementation.