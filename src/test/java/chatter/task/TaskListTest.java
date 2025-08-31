package chatter.task;

import chatter.exception.ChatterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void get_tooLargeInput_exceptionThrown() {
        TaskList tasks = new TaskList();
        try {
            tasks.get(10);
        } catch (ChatterException e) {
            assertEquals("You don't have that many task!", e.getMessage());
        }
    }
}
