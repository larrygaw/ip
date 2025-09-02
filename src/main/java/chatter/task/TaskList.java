package chatter.task;

import java.util.ArrayList;

import chatter.exception.ChatterException;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public void remove(int index) throws ChatterException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChatterException("You don't have that many task!");
        }
        tasks.remove(index);
    }

    public Task get(int index) throws ChatterException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChatterException("You don't have that many task!");
        }
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

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
