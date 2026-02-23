package patrick.command;

import patrick.Storage;
import patrick.task.Task;
import patrick.ui.Ui;
import patrick.task.TaskList;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        assert index >= 0 : "Mark index should not be negative";
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(index);
        task.markAsDone();
        storage.writeFile(tasks);
        ui.showTaskMarked(task);
    }
}
