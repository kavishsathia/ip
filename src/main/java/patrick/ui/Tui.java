package patrick.ui;

import java.util.ArrayList;
import java.util.Scanner;

import patrick.Message;
import patrick.task.Task;
import patrick.task.TaskList;

/**
 * Text-based user interface implementation using standard input/output.
 */
public class Tui implements Ui {
    private final Scanner scanner;

    public Tui() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showWelcome() {
        System.out.println(Message.GREETING.format(Message.LOGO));
        System.out.print(Message.PROMPT);
    }

    @Override
    public String readCommand() {
        return scanner.nextLine();
    }

    @Override
    public void showBye() {
        System.out.println(Message.BYE);
    }

    @Override
    public void showPrompt() {
        System.out.print(Message.USER_PROMPT);
    }

    @Override
    public void showTaskList(TaskList tasks) {
        System.out.print(Message.LIST_HEADER);
        System.out.print(formatNumberedList(tasks.getTasks()));
        System.out.print(Message.USER_PROMPT);
    }

    @Override
    public void showTaskAdded(Task task) {
        System.out.print(Message.TASK_ADDED.format(task));
    }

    @Override
    public void showTaskDeleted(Task task) {
        System.out.print(Message.TASK_DELETED.format(task));
    }

    @Override
    public void showTaskMarked(Task task) {
        System.out.print(Message.TASK_MARKED.format(task));
    }

    @Override
    public void showTaskUnmarked(Task task) {
        System.out.print(Message.TASK_UNMARKED.format(task));
    }

    @Override
    public void showFindResults(ArrayList<Task> matchingTasks) {
        System.out.print(Message.FIND_HEADER);
        System.out.print(formatNumberedList(matchingTasks));
        System.out.print(Message.USER_PROMPT);
    }

    private String formatNumberedList(ArrayList<Task> tasks) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            s.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return s.toString();
    }

    @Override
    public void showError(String message) {
        System.out.print(message);
    }

    @Override
    public void showLoadingError() {
        System.out.print(Message.ERROR_STORAGE.format("Error loading tasks from file."));
    }
}
