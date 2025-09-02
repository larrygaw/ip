package chatter.task;

import chatter.exception.ChatterException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description as a string
     */
    public String getDescription() {
        return description;
    }

    public abstract String toSaveFormat();

    public static Task fromSaveFormat(String line) throws ChatterException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        switch (type) {
        case "T":
            Task tt = new ToDos(parts[2]);
            if (isDone) {
                tt.markAsDone();
            }
            return tt;
        case "D":
            Task td = new Deadline(parts[2], parts[3]);
            if (isDone) {
                td.markAsDone();
            }
            return td;
        case "E":
            Task te = new Events(parts[2], parts[3], parts[4]);
            if (isDone) {
                te.markAsDone();
            }
            return te;
        default:
            throw new IllegalArgumentException("    Invalid task in files");
        }
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
