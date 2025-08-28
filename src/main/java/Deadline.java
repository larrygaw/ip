public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String toSaveFormat() {
        if (isDone) {
            return "D | 1 | " + description + " | " + by;
        } else {
            return "D | 0 | " + description + " | " + by;
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
