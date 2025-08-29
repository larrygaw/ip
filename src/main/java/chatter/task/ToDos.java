package chatter.task;

public class ToDos extends Task{

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
