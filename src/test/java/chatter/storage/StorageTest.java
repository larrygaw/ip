package chatter.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import chatter.task.TaskList;
import chatter.task.ToDo;

public class StorageTest {

    @Test
    public void save_validInput_success() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Storage storage = new Storage(tempFile.toString());
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("Testing");
        tasks.add(task);
        storage.save(tasks);
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(task.toSaveFormat(), lines.get(0));
    }
}
