package chatter.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an {@link ImageView} to represent the speaker's face
 * and a {@link Label} containing text from the speaker.
 */
public class DialogBox extends HBox {
    /** Label containing the dialog text. */
    @FXML
    private Label dialog;

    /** ImageView showing the speaker's avatar. */
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a {@code DialogBox} with the specified text and image.
     * Loads the FXML layout and assigns the given text and image.
     *
     * @param text The text to display in the dialog.
     * @param img The image representing the speaker's avatar.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the {@code ImageView} appears on the left
     * and the text appears on the right. Used to differentiate between the user
     * and Chatter responses.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a {@code DialogBox} representing the user's dialog.
     * The image is displayed on the right side of the text.
     *
     * @param text The user's message.
     * @param img The user's avatar image.
     * @return A {@code DialogBox} displaying the user's dialog.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a {@code DialogBox} representing Chatter's dialog.
     * The dialog box is flipped so the image appears on the left of the text.
     *
     * @param text Chatter's response message.
     * @param img Chatter's avatar image.
     * @return A {@code DialogBox} displaying Chatter's dialog.
     */
    public static DialogBox getChatterDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}

