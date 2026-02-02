package patrick;

import java.util.ArrayList;
import java.util.Scanner;

import patrick.task.Task;
import patrick.task.TaskList;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(Message.GREETING.format(Message.LOGO));
        System.out.print(Message.PROMPT);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showBye() {
        System.out.println(Message.BYE);
    }

    public void showPrompt() {
        System.out.print(Message.USER_PROMPT);
    }

    public void showTaskList(TaskList tasks) {
        System.out.print(Message.LIST_HEADER);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            s.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        System.out.print(s);
        System.out.print(Message.USER_PROMPT);
    }

    public void showTaskAdded(Task task) {
        System.out.print(Message.TASK_ADDED.format(task));
    }

    public void showTaskDeleted(Task task) {
        System.out.print(Message.TASK_DELETED.format(task));
    }

    public void showTaskMarked(Task task) {
        System.out.print(Message.TASK_MARKED.format(task));
    }

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

    public void showError(String message) {
        System.out.print(message);
    }

    public void showLoadingError() {
        System.out.print(Message.ERROR_STORAGE.format("Error loading tasks from file."));
    }
}
