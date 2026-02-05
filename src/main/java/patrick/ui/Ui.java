package patrick.ui;

import java.util.ArrayList;

import patrick.task.Task;
import patrick.task.TaskList;

/**
 * Interface for user interface implementations.
 */
public interface Ui {
    /** Displays the welcome message. */
    void showWelcome();

    /**
     * Reads a line of user input.
     *
     * @return The user input string.
     */
    String readCommand();

    /** Displays the goodbye message. */
    void showBye();

    /** Displays the user input prompt. */
    void showPrompt();

    /**
     * Displays all tasks in the given task list.
     *
     * @param tasks The task list to display.
     */
    void showTaskList(TaskList tasks);

    /**
     * Displays a message confirming the task was added.
     *
     * @param task The task that was added.
     */
    void showTaskAdded(Task task);

    /**
     * Displays a message confirming the task was deleted.
     *
     * @param task The task that was deleted.
     */
    void showTaskDeleted(Task task);

    /**
     * Displays a message confirming the task was marked as done.
     *
     * @param task The task that was marked.
     */
    void showTaskMarked(Task task);

    /**
     * Displays a message confirming the task was marked as not done.
     *
     * @param task The task that was unmarked.
     */
    void showTaskUnmarked(Task task);

    /**
     * Displays the list of tasks matching a search keyword.
     *
     * @param matchingTasks The list of matching tasks.
     */
    void showFindResults(ArrayList<Task> matchingTasks);

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    void showError(String message);

    /** Displays an error message when tasks cannot be loaded from storage. */
    void showLoadingError();
}
