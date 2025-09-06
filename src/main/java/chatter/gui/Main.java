package chatter.gui;

import java.io.IOException;

import chatter.ui.Chatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Chatter using FXML.
 */
public class Main extends Application {
    /** The core Chatter instance used by the GUI. */
    private Chatter chatter = new Chatter("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setChatter(chatter); // inject the Chatter instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
