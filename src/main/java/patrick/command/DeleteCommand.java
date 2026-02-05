package patrick.command;

import patrick.Storage;
import patrick.task.Task;
import patrick.ui.Ui;
import patrick.task.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.delete(index);
        storage.writeFile(tasks);
        ui.showTaskDeleted(task);
    }
}
