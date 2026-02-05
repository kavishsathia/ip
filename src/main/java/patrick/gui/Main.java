package patrick.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import patrick.Patrick;

/**
 * Main entry point for the JavaFX GUI application.
 */
public class Main extends Application {
    private Patrick patrick;

    @Override
    public void start(Stage stage) {
        Gui gui = new Gui();
        gui.initialize(stage);
        patrick = new Patrick("data/patrick.txt", gui);
        gui.setTaskList(patrick.getTasks());
        gui.setStorage(patrick.getStorage());

        Thread patrickThread = new Thread(() -> patrick.run());
        patrickThread.setDaemon(true);
        patrickThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
