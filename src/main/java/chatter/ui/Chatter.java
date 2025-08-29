package chatter.ui;

import chatter.exception.ChatterException;
import chatter.parser.Parser;
import chatter.storage.Storage;
import chatter.task.TaskList;

public class Chatter {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Chatter(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
    }

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

    public static void main(String[] args) {
        new Chatter("data/tasks.txt").run();
    }
}
