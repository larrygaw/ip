package chatter.task;

import chatter.exception.ChatterException;

/**
 * Abstract class representing a generic task.
 * Provides the description and completion status, as well as common methods for marking tasks.
 */
public abstract class Task {
    /** Description of the task */
    protected String description;

    /** Completion status of the task */
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is done, otherwise a space " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
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

    /**
     * Returns the task in a string format suitable for saving to a file.
     *
     * @return Formatted string representing the task for storage.
     */
    public abstract String toSaveFormat();

    /**
     * Parses a line from the storage file and creates the corresponding Task object.
     *
     * @param line Line from the file in save format.
     * @return Task object represented by the line.
     * @throws ChatterException If the line is invalid or cannot be parsed.
     */
    public static Task fromSaveFormat(String line) throws ChatterException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        switch (type) {
        case "T":
            Task tt = new ToDo(parts[2]);
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
            Task te = new Event(parts[2], parts[3], parts[4]);
            if (isDone) {
                te.markAsDone();
            }
            return te;
        default:
            throw new IllegalArgumentException("    Invalid task in files");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        Task newTask = (Task) obj;
        return description.equals(newTask.getDescription());
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
