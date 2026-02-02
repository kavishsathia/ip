package patrick;

import java.util.ArrayList;
import java.util.Scanner;

import patrick.task.Task;
import patrick.task.TaskList;

/**
 * Handles all user interface interactions, including reading input and displaying output.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /** Displays the welcome message. */
    public void showWelcome() {
        System.out.println(Message.GREETING.format(Message.LOGO));
        System.out.print(Message.PROMPT);
    }

    /**
     * Reads a line of user input from standard input.
     *
     * @return The user input string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Displays the goodbye message. */
    public void showBye() {
        System.out.println(Message.BYE);
    }

    /** Displays the user input prompt. */
    public void showPrompt() {
        System.out.print(Message.USER_PROMPT);
    }

    /**
     * Displays all tasks in the given task list.
     *
     * @param tasks The task list to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.print(Message.LIST_HEADER);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            s.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        System.out.print(s);
        System.out.print(Message.USER_PROMPT);
    }

    /**
     * Displays a message confirming the task was added.
     *
     * @param task The task that was added.
     */
    public void showTaskAdded(Task task) {
        System.out.print(Message.TASK_ADDED.format(task));
    }

    /**
     * Displays a message confirming the task was deleted.
     *
     * @param task The task that was deleted.
     */
    public void showTaskDeleted(Task task) {
        System.out.print(Message.TASK_DELETED.format(task));
    }

    /**
     * Displays a message confirming the task was marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.print(Message.TASK_MARKED.format(task));
    }

    /**
     * Displays a message confirming the task was marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.print(Message.TASK_UNMARKED.format(task));
    }

    /**
     * Displays the list of tasks matching a search keyword.
     *
     * @param matchingTasks The list of matching tasks.
     */
    public void showFindResults(ArrayList<Task> matchingTasks) {
        System.out.print(Message.FIND_HEADER);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < matchingTasks.size(); i++) {
            s.append(i + 1).append(".").append(matchingTasks.get(i)).append("\n");
        }
        System.out.print(s);
        System.out.print(Message.USER_PROMPT);
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.print(message);
    }

    /** Displays an error message when tasks cannot be loaded from storage. */
    public void showLoadingError() {
        System.out.print(Message.ERROR_STORAGE.format("Error loading tasks from file."));
    }
}
