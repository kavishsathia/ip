package patrick.gui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import patrick.Message;
import patrick.task.Task;
import patrick.task.TaskList;
import patrick.ui.Ui;

/**
 * Graphical user interface implementation using JavaFX.
 */
public class Gui implements Ui {
    private VBox dialogContainer;
    private TextField userInput;
    private String lastCommand;
    private final Object commandLock = new Object();
    private boolean commandReady = false;

    private Stage stage;
    private Scene scene;

    /**
     * Initializes the GUI components.
     *
     * @param stage The primary stage for this application.
     */
    public void initialize(Stage stage) {
        this.stage = stage;

        ScrollPane scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        Button sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        stage.setTitle("Patrick");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.setSpacing(10);
        dialogContainer.setPadding(new Insets(10));

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) {
            return;
        }
        addUserMessage(input);
        userInput.clear();

        synchronized (commandLock) {
            lastCommand = input;
            commandReady = true;
            commandLock.notifyAll();
        }
    }

    private void addUserMessage(String text) {
        DialogBox userDialog = DialogBox.getUserDialog(text);
        dialogContainer.getChildren().add(userDialog);
    }

    private void addPatrickMessage(String text) {
        Platform.runLater(() -> {
            DialogBox patrickDialog = DialogBox.getPatrickDialog(text);
            dialogContainer.getChildren().add(patrickDialog);
        });
    }

    @Override
    public void showWelcome() {
        String welcomeText = "Hello from Patrick!\nWhat can I do for you?";
        addPatrickMessage(welcomeText);
    }

    @Override
    public String readCommand() {
        synchronized (commandLock) {
            while (!commandReady) {
                try {
                    commandLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return "";
                }
            }
            commandReady = false;
            return lastCommand;
        }
    }

    @Override
    public void showBye() {
        addPatrickMessage("Bye. Hope to see you again soon!");
        Platform.runLater(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Platform.exit();
        });
    }

    @Override
    public void showPrompt() {
        // No prompt needed in GUI
    }

    @Override
    public void showTaskList(TaskList tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        addPatrickMessage(sb.toString().trim());
    }

    @Override
    public void showTaskAdded(Task task) {
        addPatrickMessage("I've added \"" + task + "\"");
    }

    @Override
    public void showTaskDeleted(Task task) {
        addPatrickMessage("Noted. I've removed this task:\n  " + task);
    }

    @Override
    public void showTaskMarked(Task task) {
        addPatrickMessage("Nice! I've marked this task as done:\n  " + task);
    }

    @Override
    public void showTaskUnmarked(Task task) {
        addPatrickMessage("OK, I've marked this task as not done yet:\n  " + task);
    }

    @Override
    public void showFindResults(ArrayList<Task> matchingTasks) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        addPatrickMessage(sb.toString().trim());
    }

    @Override
    public void showError(String message) {
        // Extract just the message part without "Assistant:" and "User:" prompts
        String cleanMessage = message
                .replace("Assistant: ", "")
                .replace("\nUser: ", "")
                .trim();
        addPatrickMessage(cleanMessage);
    }

    @Override
    public void showLoadingError() {
        addPatrickMessage("Error loading tasks from file.");
    }
}
