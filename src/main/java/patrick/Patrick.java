package patrick;

import patrick.command.Command;
import patrick.task.TaskList;

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
     *
     * @param filePath Path to the file used for saving and loading tasks.
     */
    public Patrick(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
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

    /** Entry point of the application. */
    public static void main(String[] args) {
        new Patrick("data/patrick.txt").run();
    }
}
