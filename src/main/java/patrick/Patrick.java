package patrick;

import patrick.command.Command;
import patrick.task.TaskList;
import patrick.ui.Tui;
import patrick.ui.Ui;

/**
 * Main class for the Patrick chatbot application.
 * Initialises the application and starts the command loop.
 */
public class Patrick {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Patrick instance with the specified file path for storage.
     * Uses the text-based UI by default.
     *
     * @param filePath Path to the file used for saving and loading tasks.
     */
    public Patrick(String filePath) {
        this(filePath, new Tui());
    }

    /**
     * Constructs a Patrick instance with the specified file path and UI.
     *
     * @param filePath Path to the file used for saving and loading tasks.
     * @param ui The UI implementation to use.
     */
    public Patrick(String filePath, Ui ui) {
        this.ui = ui;
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Returns the task list.
     *
     * @return The task list.
     */
    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Returns the storage instance.
     *
     * @return The storage.
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Runs the main command loop, reading and executing user commands until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (PatrickException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /** Entry point of the application (text-based UI). */
    public static void main(String[] args) {
        new Patrick("data/patrick.txt").run();
    }
}
