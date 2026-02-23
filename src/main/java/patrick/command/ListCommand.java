package patrick.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import patrick.Storage;
import patrick.task.Task;
import patrick.task.TaskList;
import patrick.ui.Ui;

/**
 * Represents a command to list all tasks, optionally sorted.
 */
public class ListCommand extends Command {
    private final String sortField;
    private final boolean ascending;

    /** Constructs a ListCommand with no sorting. */
    public ListCommand() {
        this.sortField = null;
        this.ascending = true;
    }

    /**
     * Constructs a ListCommand with the given sort field and order.
     *
     * @param sortField The field to sort by ("name" or "time").
     * @param ascending True for ascending order, false for descending.
     */
    public ListCommand(String sortField, boolean ascending) {
        this.sortField = sortField;
        this.ascending = ascending;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (sortField == null) {
            ui.showTaskList(tasks);
            return;
        }

        ArrayList<Task> sorted = new ArrayList<>(tasks.getTasks());
        Comparator<Task> comparator;

        if (sortField.equals("time")) {
            comparator = Comparator.comparing(
                    Task::getDate,
                    Comparator.nullsLast(LocalDate::compareTo));
        } else {
            comparator = Comparator.comparing(Task::getDescription);
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        sorted.sort(comparator);
        ui.showTaskList(new TaskList(sorted));
    }
}
