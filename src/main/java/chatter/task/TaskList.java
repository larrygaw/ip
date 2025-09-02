package chatter.task;

import java.util.ArrayList;

import chatter.exception.ChatterException;

/**
 * Represents a collection of {@link Task} objects.
 * Provides methods to add, remove, and access tasks.
 */
public class TaskList {
    /** Internal list storing the tasks */
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} with an existing list of tasks.
     *
     * @param tasks {@link ArrayList} of tasks to initialize the {@code TaskList} with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the {@code TaskList}.
     *
     * @param t {@code Task} to add.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Removes the task at the specified index.
     *
     * @param index Index of the task to remove.
     * @throws ChatterException If the index is out of bounds.
     */
    public void remove(int index) throws ChatterException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChatterException("You don't have that many task!");
        }
        tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of the task to retrieve.
     * @return {@code Task} at the given index.
     * @throws ChatterException If the index is out of bounds.
     */
    public Task get(int index) throws ChatterException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChatterException("You don't have that many task!");
        }
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the {@code TaskList}.
     *
     * @return Number of tasks.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns the internal list of all tasks.
     *
     * @return {@code ArrayList} of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Returns a new {@code TaskList} containing all tasks whose descriptions
     * contain the specified keyword.
     *
     * @param keyword the keyword to search for in task descriptions
     * @return a new {@code TaskList} with all matching tasks
     */
    public TaskList findMatching(String keyword) {
        TaskList matching = new TaskList();
        for (Task t : tasks) {
            if (t.getDescription().contains(keyword)) {
                matching.add(t);
            }
        }
        return matching;
    }
}
