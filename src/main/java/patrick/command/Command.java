package patrick.command;

import patrick.Storage;
import patrick.task.TaskList;
import patrick.ui.Ui;

/**
 * Represents an executable command parsed from user input.
 */
public abstract class Command {
    /**
     * Executes this command.
     *
     * @param tasks The task list to operate on.
     * @param ui The UI for displaying output.
     * @param storage The storage for saving changes.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Returns whether this command should cause the application to exit.
     *
     * @return {@code true} if the application should exit after this command.
     */
    public boolean isExit() {
        return false;
    }
}
