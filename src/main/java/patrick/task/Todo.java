package patrick.task;

/**
 * Represents a todo task with no date/time attached.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo with the given description.
     *
     * @param description The todo description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toFileString() {
        return "T | " + super.toFileString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
