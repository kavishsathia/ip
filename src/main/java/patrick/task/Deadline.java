package patrick.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline date.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Constructs a Deadline with the given description and date.
     *
     * @param description The deadline description.
     * @param by The deadline date.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses a deadline from a description and date string in yyyy-mm-dd format.
     *
     * @param description The deadline description.
     * @param byString The deadline date string.
     * @return The parsed Deadline.
     * @throws DateTimeParseException If the date string is not in a valid format.
     */
    public static Deadline parse(String description, String byString) throws DateTimeParseException {
        LocalDate by = LocalDate.parse(byString.trim());
        return new Deadline(description, by);
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
