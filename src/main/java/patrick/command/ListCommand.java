package patrick.command;

import patrick.Storage;
import patrick.Ui;
import patrick.task.TaskList;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
