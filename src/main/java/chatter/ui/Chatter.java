package chatter.ui;

import chatter.exception.ChatterException;
import chatter.gui.Main;
import chatter.parser.Parser;
import chatter.storage.Storage;
import chatter.task.TaskList;

/**
 * Main class that drives the Chatter application.
 * Handles initialization of the {@link Storage}, {@link TaskList}, and {@link Ui},
 * and provides responses to user input.
 */
public class Chatter {
    /** Handles reading from and writing to the tasks file */
    private final Storage storage;

    /** The list of tasks currently loaded in memory */
    private final TaskList tasks;

    /** Handles user interaction via the console */
    private final Ui ui;

    /**
     * Constructs a new {@code Chatter} instance.
     * Initializes the {@code Ui}, loads tasks from the specified {@code Storage} file,
     * and prepares the {@code TaskList}.
     *
     * @param filePath the path to the file for storing tasks
     */
    public Chatter(String filePath) {
        assert filePath != null : "File path must not be null";
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
    }

    /**
     * Generates a response for the given user input.
     *
     * @param input user input string.
     * @return response message string
     */
    public String getResponse(String input) {
        try {
            return Parser.parse(input, tasks, ui, storage);
        } catch (ChatterException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * The main method for Chatter.
     * Since this application is now GUI-only, this entry point is disabled.
     * Run {@link Main} instead.
     */
    public static void main(String[] args) {
        System.out.println("Chatter no longer supports CLI mode. Please run Main instead.");
    }
}
