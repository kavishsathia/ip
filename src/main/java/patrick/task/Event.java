package patrick.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that occurs during a time period.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Constructs an Event with the given description and date range.
     *
     * @param description The event description.
     * @param from The start date.
     * @param to The end date.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        assert from != null : "Event start date should not be null";
        assert to != null : "Event end date should not be null";
        this.from = from;
        this.to = to;
    }

    /**
     * Parses an Event from description and date strings in yyyy-mm-dd format.
     *
     * @param description The event description.
     * @param fromString The start date string.
     * @param toString The end date string.
     * @return The parsed Event.
     * @throws DateTimeParseException If either date string is not in a valid format.
     */
    public static Event parse(String description, String fromString, String toString)
            throws DateTimeParseException {
        LocalDate from = LocalDate.parse(fromString.trim());
        LocalDate to = LocalDate.parse(toString.trim());
        return new Event(description, from, to);
    }

    @Override
    public LocalDate getDate() {
        return from;
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.toString() + " (from: " + this.from.format(fmt)
                + " to: " + this.to.format(fmt) + ")";
    }
}
