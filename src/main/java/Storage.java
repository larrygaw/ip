import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

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
            System.out.println("    Error loading file: " + e.getMessage());
        }
        return new TaskList(tasks);
    }

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
