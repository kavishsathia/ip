package patrick.command;

import patrick.Storage;
import patrick.Ui;
import patrick.task.Task;
import patrick.task.TaskList;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(task);
        storage.writeFile(tasks);
        ui.showTaskAdded(task);
    }
}
