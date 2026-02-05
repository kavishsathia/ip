package patrick.command;

import patrick.Storage;
import patrick.task.Task;
import patrick.ui.Ui;
import patrick.task.TaskList;

/**
 * Represents a command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(index);
        task.markAsUndone();
        storage.writeFile(tasks);
        ui.showTaskUnmarked(task);
    }
}
