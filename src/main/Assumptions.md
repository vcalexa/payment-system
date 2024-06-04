### Assumptions
* The list of events is non-null and contains at least one PAYMENT_CREATED event.
* If there is a single CANCELLED event, the state will be CANCELLED
* Events are valid and provided in chronological order
* Payment cannot be overpaid

### Technical details
* using java 17
* created maven project
* used lombok and junit dependencies
* used Value annotation from lombok to make the event immutable

