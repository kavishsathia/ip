package patrick.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks and provides operations to modify it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the specified index.
     *
     * @param index The zero-based index of the task to delete.
     * @return The deleted task.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The zero-based index of the task.
     * @return The task at the given index.
     */
    public Task get(int index) {
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
     * Returns the underlying list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
