package chatter.ui;

import chatter.exception.ChatterException;
import chatter.parser.Parser;
import chatter.storage.Storage;
import chatter.task.TaskList;

/**
 * Main class that drives the Chatter application.
 * Handles initialization of the {@link Storage}, {@link TaskList}, and {@link Ui},
 * and runs the main interaction loop with the user.
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
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
    }

    /**
     * Starts the main application loop.
     * Continuously reads user commands via {@link Ui#readCommand()} and
     * executes them using {@link Parser#parse(String, TaskList, Ui, Storage)}.
     * The loop continues until the user issues the {@code bye} command.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                isExit = Parser.parse(input, tasks, ui, storage);
            } catch (ChatterException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * The main entry point for the Chatter application.
     * Initializes a {@code Chatter} instance with the default storage path
     * and starts the application loop.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Chatter("data/tasks.txt").run();
    }
}
