package patrick.command;

import patrick.Storage;
import patrick.Ui;
import patrick.task.Task;
import patrick.task.TaskList;

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
