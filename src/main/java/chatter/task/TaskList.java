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

    public int getSize() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}
