public class Events extends Task{
    protected String start;
    protected String end;

    public Events(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public String toSaveFormat() {
        if (isDone) {
            return "D | 1 | " + description + " | " + start + " | " + end;
        } else {
            return "D | 0 | " + description + " | " + start + " | " + end;
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
