package patrick.task;

/**
 * Represents a task that occurs during a time period.
 */
public class Event extends Task {
    private String at;

    /**
     * Constructs an Event with the given description and time period.
     *
     * @param description The event description.
     * @param at The time period string (e.g. "from Mon to Tue").
     */
    public Event(String description, String at) {
        super(description);
        assert at != null : "Event time period should not be null";
        assert !at.isEmpty() : "Event time period should not be empty";
        this.at = at;
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + this.at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + this.at + ")";
    }
}
