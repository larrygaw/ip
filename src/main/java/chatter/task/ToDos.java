package chatter.task;

/**
 * Represents a simple to-do task.
 * A {@code ToDos} object has a description.
 * Inherits from {@link Task}.
 */
public class ToDos extends Task{

    /**
     * Creates a new {@code ToDos} task with the given description.
     *
     * @param description the description of the to-do task
     */
    public ToDos(String description) {
        super(description);
    }

    @Override
    public String toSaveFormat() {
        if (isDone) {
            return "T | 1 | " + description;
        } else {
            return "T | 0 | " + description;
        }
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
