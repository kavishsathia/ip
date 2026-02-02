package patrick.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a Task with the given description, initially not done.
     *
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns "X" if the task is done, or a space otherwise.
     *
     * @return The status icon string.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the file-formatted string representation of this task.
     *
     * @return The string used when saving to file.
     */
    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
