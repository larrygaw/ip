package chatter.ui;

import chatter.exception.ChatterException;
import chatter.task.Deadline;
import chatter.task.Events;
import chatter.task.Task;
import chatter.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Ui {
    private final Scanner sc;
    private final String line = "   ____________________________________________________________";

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showWelcome() {
        System.out.println(line);
        System.out.println("   Hello! I'm Chatter\n   What can I do for you?");
        System.out.println(line);
    }

    public void showExit() {
        System.out.println(line);
        System.out.println("   Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    public void showList(TaskList tasks) throws ChatterException {
        System.out.println(line);
        System.out.println("   Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println("   " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(line);
    }

    public void showAdded(Task t, int size) {
        System.out.println(line);
        System.out.println("   Got it. I've added this task:");
        System.out.println("     " + t);
        System.out.println("   Now you have " + size + " tasks in the list.");
        System.out.println(line);
    }

    public void showDeleted(Task t, int size) {
        System.out.println(line);
        System.out.println("   Noted. I've removed this task:");
        System.out.println("     " + t);
        System.out.println("   Now you have " + size + " tasks in the list.");
        System.out.println(line);
    }

    public void showMarked(Task t) {
        System.out.println(line);
        System.out.println("   Nice! I've marked this task as done:");
        System.out.println("     " + t);
        System.out.println(line);
    }

    public void showUnmarked(Task t) {
        System.out.println(line);
        System.out.println("   OK, I've unmarked this task:");
        System.out.println("     " + t);
        System.out.println(line);
    }

    public void showError(String msg) {
        System.out.println(line);
        System.out.println("   " + msg);
        System.out.println(line);
    }

    public void showTasksOnDate(LocalDate date, TaskList tasks) {
        System.out.println(line);
        System.out.println("   Tasks occurring on "
                + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        boolean found = false;
        for (Task task : tasks.getAllTasks()) {
            if (task instanceof Deadline) {
                Deadline d = (Deadline) task;
                if (d.getDateTime().toLocalDate().equals(date)) {
                    System.out.println("   " + d);
                    found = true;
                }
            } else if (task instanceof Events) {
                Events e = (Events) task;
                if (!date.isBefore(e.getFrom().toLocalDate()) &&
                        !date.isAfter(e.getTo().toLocalDate())) {
                    System.out.println("   " + e);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("   No tasks on this date.");
        }
        System.out.println(line);
    }
}
