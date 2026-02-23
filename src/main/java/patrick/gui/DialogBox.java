package patrick.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents a dialog box in the chat interface.
 */
public class DialogBox extends HBox {
    private Label text;
    private Label avatar;

    private DialogBox(String message, boolean isUser) {
        text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(280);
        text.setPadding(new Insets(10));
        text.setFont(Font.font("System", 13));

        avatar = new Label(isUser ? "U" : "P");
        avatar.setFont(Font.font("System", FontWeight.BOLD, 14));
        avatar.setMinSize(35, 35);
        avatar.setMaxSize(35, 35);
        avatar.setAlignment(Pos.CENTER);

        Circle circle = new Circle(17.5);
        avatar.setShape(circle);

        if (isUser) {
            text.setStyle("-fx-background-color: #BBDEFB; -fx-background-radius: 10;");
            avatar.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white;");
            this.setAlignment(Pos.CENTER_RIGHT);
            this.getChildren().addAll(text, avatar);
        } else {
            text.setStyle("-fx-background-color: #FCE4EC; -fx-background-radius: 10;");
            avatar.setStyle("-fx-background-color: #F48FB1; -fx-text-fill: white;");
            this.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().addAll(avatar, text);
        }

        this.setSpacing(10);
        this.setPadding(new Insets(5, 10, 5, 10));
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param message The user's message.
     * @return A DialogBox styled for user messages.
     */
    public static DialogBox getUserDialog(String message) {
        return new DialogBox(message, true);
    }

    /**
     * Creates a dialog box for Patrick's messages.
     *
     * @param message Patrick's message.
     * @return A DialogBox styled for Patrick's messages.
     */
    public static DialogBox getPatrickDialog(String message) {
        return new DialogBox(message, false);
    }
}
