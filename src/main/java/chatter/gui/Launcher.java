package chatter.gui;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    /**
     * Entry point of the program.
     * Delegates to {@link Application#launch(Class, String...)} with {@link Main} as the target class.
     *
     * @param args Command line arguments passed to the program.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}

