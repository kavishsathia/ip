package patrick.command;

import patrick.Storage;
import patrick.Ui;
import patrick.task.TaskList;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}
