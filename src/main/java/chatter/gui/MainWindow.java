package chatter.gui;

import chatter.ui.Chatter;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    /** Scroll pane that wraps the dialog container. */
    @FXML
    private ScrollPane scrollPane;

    /** Container holding all dialog boxes. */
    @FXML
    private VBox dialogContainer;

    /** Text field for user input. */
    @FXML
    private TextField userInput;

    /** Button to send user input. */
    @FXML
    private Button sendButton;

    /** Chatter instance for generating responses. */
    private Chatter chatter;

    /** User avatar image. */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));

    /** Chatter avatar image. */
    private Image chatterImage = new Image(this.getClass().getResourceAsStream("/images/DaChatter.png"));

    /**
     * Initializes the main window after the FXML is loaded.
     * <p>
     * Binds the vertical scroll value of the scroll pane to the height of
     * the dialog container so the view automatically scrolls down when new
     * dialog boxes are added.
     * </p>
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Chatter instance */
    public void setChatter(Chatter c) {
        chatter = c;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Chatter's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = chatter.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getChatterDialog(response, chatterImage)
        );
        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
        userInput.clear();
    }
}

