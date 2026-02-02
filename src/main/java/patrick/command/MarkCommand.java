package patrick.command;

import patrick.Storage;
import patrick.Ui;
import patrick.task.Task;
import patrick.task.TaskList;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
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
