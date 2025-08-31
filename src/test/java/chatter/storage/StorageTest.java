package chatter.storage;

import chatter.task.TaskList;
import chatter.task.ToDos;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {

    @Test
    public void save_validInput_success() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Storage storage = new Storage(tempFile.toString());
        TaskList tasks = new TaskList();
        ToDos task = new ToDos("Testing");
        tasks.add(task);
        storage.save(tasks);
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(task.toSaveFormat(), lines.get(0));
    }
}
