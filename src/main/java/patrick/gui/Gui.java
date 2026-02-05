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
        Label taskListHeader = new Label("Tasks");
        taskListHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        taskListContainer = new VBox(5);
        taskListContainer.setPadding(new Insets(5));

        ScrollPane taskScrollPane = new ScrollPane();
        taskScrollPane.setContent(taskListContainer);
        taskScrollPane.setFitToWidth(true);
        taskScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox rightPanel = new VBox(10);
        rightPanel.getChildren().addAll(taskListHeader, taskScrollPane);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setStyle("-fx-background-color: #f5f5f5;");
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
        // Remove the [X] or [ ] status and type prefix like [T], [D], [E]
        // Format is like: [T][ ] description or [D][X] description (by: date)
        if (str.length() > 6) {
            return str.substring(6); // Skip [T][ ] or similar
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
                checkBox.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 5;");

                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        task.markAsDone();
                        addPatrickMessage("Nice! I've marked this task as done:\n  " + task);
                    } else {
                        task.markAsUndone();
                        addPatrickMessage("OK, I've marked this task as not done yet:\n  " + task);
                    }
                    if (storage != null) {
                        storage.writeFile(currentTasks);
                    }
                    refreshTaskPanel();
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
        refreshTaskPanel();
    }

    @Override
    public void showTaskAdded(Task task) {
        addPatrickMessage("I've added \"" + task + "\"");
        refreshTaskPanel();
    }

    @Override
    public void showTaskDeleted(Task task) {
        addPatrickMessage("Noted. I've removed this task:\n  " + task);
        refreshTaskPanel();
    }

    @Override
    public void showTaskMarked(Task task) {
        addPatrickMessage("Nice! I've marked this task as done:\n  " + task);
        refreshTaskPanel();
    }

    @Override
    public void showTaskUnmarked(Task task) {
        addPatrickMessage("OK, I've marked this task as not done yet:\n  " + task);
        refreshTaskPanel();
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
