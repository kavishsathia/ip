package patrick.gui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import patrick.Storage;
import patrick.task.Deadline;
import patrick.task.Event;
import patrick.task.Task;
import patrick.task.TaskList;
import patrick.task.Todo;
import patrick.ui.Ui;

/**
 * Graphical user interface implementation using JavaFX.
 */
public class Gui implements Ui {
    private VBox dialogContainer;
    private VBox taskListContainer;
    private TextField userInput;
    private String lastCommand;
    private final Object commandLock = new Object();
    private boolean commandReady = false;
    private TaskList currentTasks;
    private Storage storage;

    /**
     * Initializes the GUI components.
     *
     * @param stage The primary stage for this application.
     */
    public void initialize(Stage stage) {
        // Left panel - Chat
        ScrollPane chatScrollPane = new ScrollPane();
        dialogContainer = new VBox();
        chatScrollPane.setContent(dialogContainer);

        userInput = new TextField();
        userInput.setPromptText("Type a command...");
        Button sendButton = new Button("Send");

        HBox inputBox = new HBox(5);
        inputBox.getChildren().addAll(userInput, sendButton);
        HBox.setHgrow(userInput, Priority.ALWAYS);

        VBox leftPanel = new VBox(5);
        leftPanel.getChildren().addAll(chatScrollPane, inputBox);
        leftPanel.setPadding(new Insets(10));
        VBox.setVgrow(chatScrollPane, Priority.ALWAYS);

        // Right panel - Task List
        Label taskListHeader = new Label("Patrick's To-Do Rock");
        taskListHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #880E4F;");

        taskListContainer = new VBox(5);
        taskListContainer.setPadding(new Insets(5));

        ScrollPane taskScrollPane = new ScrollPane();
        taskScrollPane.setContent(taskListContainer);
        taskScrollPane.setFitToWidth(true);
        taskScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox rightPanel = new VBox(10);
        rightPanel.getChildren().addAll(taskListHeader, taskScrollPane);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setStyle("-fx-background-color: #FFF3E0;");
        VBox.setVgrow(taskScrollPane, Priority.ALWAYS);

        // Main layout - two panels side by side
        HBox mainLayout = new HBox(10);
        mainLayout.getChildren().addAll(leftPanel, rightPanel);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);

        // Configure sizes (65/35 ratio)
        leftPanel.setPrefWidth(455);
        leftPanel.setMinWidth(350);
        rightPanel.setPrefWidth(245);
        rightPanel.setMinWidth(180);

        chatScrollPane.setPrefHeight(500);
        chatScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        chatScrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.setSpacing(10);
        dialogContainer.setPadding(new Insets(10));

        sendButton.setPrefWidth(60);

        Scene scene = new Scene(mainLayout, 700, 600);
        stage.setScene(scene);
        stage.setTitle("Patrick");
        stage.setMinHeight(400);
        stage.setMinWidth(550);
        stage.show();

        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        dialogContainer.heightProperty().addListener((observable) -> chatScrollPane.setVvalue(1.0));
    }

    /**
     * Sets the task list reference for the right panel display.
     *
     * @param tasks The task list to display.
     */
    public void setTaskList(TaskList tasks) {
        this.currentTasks = tasks;
        refreshTaskPanel();
    }

    /**
     * Sets the storage reference for saving task changes.
     *
     * @param storage The storage instance.
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    private String getTaskColor(Task task) {
        if (task instanceof Deadline) {
            return "#FFCDD2"; // Light red for deadlines
        } else if (task instanceof Event) {
            return "#C8E6C9"; // Light green for events
        } else if (task instanceof Todo) {
            return "#FFF9C4"; // Light yellow for todos
        }
        return "#FFFFFF"; // White default
    }

    private String getTaskDescription(Task task) {
        String str = task.toString();
        // Task.toString() format: [T][ ] description — strip the "[T][ ] " prefix
        int prefixLength = "[T][ ] ".length();
        if (str.length() > prefixLength) {
            return str.substring(prefixLength);
        }
        return str;
    }

    private void refreshTaskPanel() {
        Platform.runLater(() -> {
            taskListContainer.getChildren().clear();
            if (currentTasks == null || currentTasks.size() == 0) {
                Label emptyLabel = new Label("No tasks yet!");
                emptyLabel.setStyle("-fx-text-fill: #888;");
                taskListContainer.getChildren().add(emptyLabel);
                return;
            }
            for (int i = 0; i < currentTasks.size(); i++) {
                Task task = currentTasks.get(i);
                final int index = i;

                CheckBox checkBox = new CheckBox((i + 1) + ". " + getTaskDescription(task));
                checkBox.setSelected(task.isDone());
                checkBox.setWrapText(true);
                checkBox.setMaxWidth(210);
                checkBox.setPadding(new Insets(8));

                String bgColor = getTaskColor(task);
                String strikethrough = task.isDone() ? " -fx-strikethrough: true; -fx-text-fill: #888;" : "";
                checkBox.setStyle("-fx-background-color: " + bgColor
                        + "; -fx-background-radius: 5;" + strikethrough);

                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        task.markAsDone();
                        showTaskMarked(task);
                    } else {
                        task.markAsUndone();
                        showTaskUnmarked(task);
                    }
                    if (storage != null) {
                        storage.writeFile(currentTasks);
                    }
                });

                taskListContainer.getChildren().add(checkBox);
            }
        });
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
        String welcomeText = "Hi! I'm Patrick Star!\nIs mayonnaise an instrument? "
                + "No? Then what can I do for you?";
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
        addPatrickMessage("Uhh... I gotta go. My house is calling me. Bye!");
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
        addPatrickMessage("Uhhh... let me think... oh yeah! Here's your stuff:\n"
                + formatNumberedList(tasks.getTasks()));
        refreshTaskPanel();
    }

    @Override
    public void showTaskAdded(Task task) {
        addPatrickMessage("Oooh! I remembered something! I added \"" + task + "\"");
        refreshTaskPanel();
    }

    @Override
    public void showTaskDeleted(Task task) {
        addPatrickMessage("It's gone! I made it disappear:\n  " + task);
        refreshTaskPanel();
    }

    @Override
    public void showTaskMarked(Task task) {
        addPatrickMessage("I did it! I marked this thing as done:\n  " + task);
        refreshTaskPanel();
    }

    @Override
    public void showTaskUnmarked(Task task) {
        addPatrickMessage("Wait... it's NOT done? Okay, unmarked:\n  " + task);
        refreshTaskPanel();
    }

    @Override
    public void showFindResults(ArrayList<Task> matchingTasks) {
        addPatrickMessage("I found some stuff! Look look look:\n"
                + formatNumberedList(matchingTasks));
    }

    private String formatNumberedList(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public void showError(String message) {
        String cleanMessage = message
                .replace("Assistant: ", "")
                .replace("\nUser: ", "")
                .trim();
        addPatrickMessage(cleanMessage);
    }

    @Override
    public void showLoadingError() {
        addPatrickMessage("Uhh... I forgot where I put your stuff. Starting fresh!");
    }
}
