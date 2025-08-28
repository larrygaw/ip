import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Chatter {

    public static void main(String[] args) {
        String line = "   ____________________________________________________________";
        String defaultMessage = "   Hello! I'm Chatter\n   What can I do for you?";
        String exitMessage = "   Bye. Hope to see you again soon!";
        String listMessage = "   Here are the tasks in your list:";
        String marking = "   Nice! I've marked this task as done:";
        String unmarking = "   OK, I've marked this task as not done yet:";
        String addingTask = "   Got it. I've added this task:";
        String deletingTask = "   Noted. I've removed this task:";

        Storage storage = new Storage("data/chatter.txt");
        ArrayList<Task> taskList = storage.load();

        System.out.println(line);
        System.out.println(defaultMessage);
        System.out.println(line);

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                //read what user types on keyboard
                String input = sc.nextLine();

                if (input.equals("bye")) {
                    System.out.println(line);
                    System.out.println(exitMessage);
                    System.out.println(line);
                    break;
                } else if (input.equals("list")) {
                    System.out.println(line);
                    System.out.println(listMessage);
                    //print every task in taskList
                    for (int i = 0; i < taskList.size(); i++) {
                        System.out.println("   " + (i + 1) + "." + taskList.get(i));
                    }
                    System.out.println(line);
                } else if (input.startsWith("todo")) {
                    String[] parts = input.split(" ", 2);
                    //throw error if there is not description
                    if (parts.length < 2 || parts[1].isBlank()) {
                        throw new ChatterException("todoTask must have a description!");
                    }
                    ToDos t = new ToDos(parts[1].trim());
                    taskList.add(t);
                    storage.save(taskList);
                    System.out.println(line);
                    System.out.println(addingTask);
                    System.out.println("     " + t);
                    System.out.println("   Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("deadline")) {
                    String[] parts = input.split(" ", 2);
                    //throw error if there is no description and deadline
                    if (parts.length < 2 || !parts[1].contains("/by")) {
                        throw new ChatterException("deadlineTask must have description and /by!");
                    }
                    String[] details = parts[1].split(" /by ", 2);
                    if (details.length == 1) {
                        throw new ChatterException("/by must be followed by deadline in yyyy-MM-dd HHmm format!");
                    }
                    Deadline t = new Deadline(details[0].trim(), details[1].trim());
                    taskList.add(t);
                    storage.save(taskList);
                    System.out.println(line);
                    System.out.println(addingTask);
                    System.out.println("     " + t);
                    System.out.println("   Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("event")) {
                    String[] parts = input.split(" ", 2);
                    //throw error is there is no description, start and end time
                    if (parts.length < 2 || !parts[1].contains("/from") || !parts[1].contains("/to")) {
                        throw new ChatterException("eventTask must have description, /from and /to!");
                    }
                    String[] getDetails = parts[1].split(" /from ", 2);
                    if (getDetails.length == 1) {
                        throw new ChatterException("eventTask must have description and "
                                + "/from must be followed by event start time in yyyy-MM-dd HHmm format!");
                    }
                    String[] getTiming = getDetails[1].split(" /to ");
                    if (getTiming.length == 1) {
                        throw new ChatterException("/from and /to must be followed by "
                                + "event start and end time in yyyy-MM-dd HHmm format respectively!");
                    }
                    Events t = new Events(getDetails[0].trim(), getTiming[0].trim(), getTiming[1].trim());
                    taskList.add(t);
                    storage.save(taskList);
                    System.out.println(line);
                    System.out.println(addingTask);
                    System.out.println("     " + t);
                    System.out.println("   Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("delete")) {
                    String[] parts = input.split(" ");
                    //throw error if there is no index of task to delete
                    if (parts.length < 2) {
                        throw new ChatterException("Provide index!");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = taskList.get(index);
                    taskList.remove(index);
                    storage.save(taskList);
                    System.out.println(line);
                    System.out.println(deletingTask);
                    System.out.println("     " + t);
                    System.out.println("   Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                    String[] parts = input.split(" ");
                    //throw error if there is no index of task to mark/unmark
                    if (parts.length < 2) {
                        throw new ChatterException("Provide index!");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = taskList.get(index);
                    if (parts[0].equals("mark")) {
                        t.markAsDone();
                        System.out.println(line);
                        System.out.println(marking);
                        System.out.println("     " + t);
                        System.out.println(line);
                    } else {
                        t.unmark();
                        System.out.println(line);
                        System.out.println(unmarking);
                        System.out.println("     " + t);
                        System.out.println(line);
                    }
                    storage.save(taskList);
                } else if (input.startsWith("on ")) {
                    String[] parts = input.split(" ");
                    if (parts.length < 2 || parts[1].isBlank()) {
                        throw new ChatterException("Please provide a date in yyyy-MM-dd format!");
                    }
                    try {
                        LocalDate date = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        System.out.println(line);
                        System.out.println("    Tasks occurring on "
                                + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
                        boolean found = false;
                        for (Task task : taskList) {
                            if (task instanceof Deadline) {
                                Deadline d = (Deadline) task;
                                if (d.getDateTime().toLocalDate().equals(date)) {
                                    System.out.println("    " + d);
                                    found = true;
                                }
                            } else if (task instanceof Events) {
                                Events e = (Events) task;
                                if (!date.isBefore(e.getFrom().toLocalDate()) &&
                                        !date.isAfter(e.getTo().toLocalDate())) {
                                    System.out.println("    " + e);
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            System.out.println("    No tasks on this date.");
                        }
                        System.out.println(line);
                    } catch (DateTimeParseException e) {
                        throw new ChatterException("Invalid date format! Please use yyyy-MM-dd!");
                    }
                } else {
                    throw new ChatterException("SORRY! I am not qualified to do this!");
                }
            } catch (ChatterException e){
                System.out.println(line);
                System.out.println("   " + e.getMessage());
                System.out.println(line);
            } catch (NumberFormatException e){
                System.out.println(line);
                System.out.println("   Task number must be an integer!");
                System.out.println(line);
            } catch (IndexOutOfBoundsException e){
                System.out.println(line);
                System.out.println("   You don't have that many tasks!");
                System.out.println(line);
            } catch (NullPointerException e){
                System.out.println(line);
                System.out.println("   check");
                System.out.println(line);
            }
        }
        sc.close();
    }
}
