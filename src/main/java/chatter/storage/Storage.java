package chatter.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import chatter.task.Task;
import chatter.task.TaskList;

/**
 * Storage class is responsible for loading tasks from a file and saving tasks to a file.
 * It provides methods to persist and retrieve a {@link TaskList}.
 */
public class Storage {
    /** Path to the file where tasks are stored */
    private final Path filePath;

    /**
     * Constructs a (@link Storage} instance for the specified file path.
     *
     * @param filePath the path of the file used to load and save tasks
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the file.
     * If the file does not exist, creates a new file and returns an empty {@code TaskList}.
     * Invalid lines in the file are skipped with a message printed to standard output.
     *
     * @return a {@code TaskList} containing tasks loaded from the file, or an empty list if the file is missing
     */
    public TaskList load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
                return new TaskList();
            }
            List<String> lines = Files.readAllLines(filePath);
            for (String line: lines) {
                try {
                    tasks.add(Task.fromSaveFormat(line));
                } catch (Exception e) {
                    System.out.println("    Invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("    Error loading file: " + e.getMessage() + ". Generating an empty tracker!");
            return new TaskList();
        }
        return new TaskList(tasks);
    }

    /**
     * Saves the given {@code TaskList} to the file.
     * Each task is converted to its save format before writing.
     * Any I/O errors are reported via standard output.
     *
     * @param tasks the {@code TaskList} to save to the file
     */
    public void save(TaskList tasks) {
        List<String> lines = new ArrayList<>();
        for (Task t: tasks.getAllTasks()) {
            lines.add(t.toSaveFormat());
        }
        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println("    Error saving file: " + e.getMessage());
        }
    }
}
