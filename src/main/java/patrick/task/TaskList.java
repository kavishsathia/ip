package patrick.task;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents a list of tasks and provides operations to modify it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list should not be null";
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        assert task != null : "Task to add should not be null";
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the specified index.
     *
     * @param index The zero-based index of the task to delete.
     * @return The deleted task.
     */
    public Task delete(int index) {
        assert index >= 0 : "Delete index should not be negative";
        assert index < tasks.size() : "Delete index should be within task list bounds";
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The zero-based index of the task.
     * @return The task at the given index.
     */
    public Task get(int index) {
        assert index >= 0 : "Get index should not be negative";
        assert index < tasks.size() : "Get index should be within task list bounds";
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of matching tasks.
     */
    public ArrayList<Task> find(String keyword) {
        assert keyword != null : "Search keyword should not be null";
        return tasks.stream()
                .filter(task -> task.toString().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
