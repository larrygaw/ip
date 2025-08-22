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

        //storage for task
        Task[] taskList = new Task[100];
        int taskCounter = 0;

        System.out.println(line);
        System.out.println(defaultMessage);
        System.out.println(line);

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                //to read what user types on keyboard
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
                    for (int i = 0; i < taskCounter; i++) {
                        System.out.println("   " + (i + 1) + "." + taskList[i]);
                    }
                    System.out.println(line);
                } else if (input.startsWith("todo")) {
                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2 || parts[1].isBlank()) {
                        throw new ChatterException("todoTask must have a description!");
                    }
                    ToDos t = new ToDos(parts[1]);
                    taskList[taskCounter] = t;
                    taskCounter += 1;
                    System.out.println(line);
                    System.out.println(addingTask);
                    System.out.println("     " + t);
                    System.out.println("   Now you have " + taskCounter + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("deadline")) {
                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2 || !parts[1].contains("/by")) {
                        throw new ChatterException("deadlineTask must have description and /by!");
                    }
                    String[] details = parts[1].split(" /by ", 2);
                    Deadline t = new Deadline(details[0], details[1]);
                    taskList[taskCounter] = t;
                    taskCounter += 1;
                    System.out.println(line);
                    System.out.println(addingTask);
                    System.out.println("     " + t);
                    System.out.println("   Now you have " + taskCounter + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("event")) {
                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2 || !parts[1].contains("/from") || !parts[1].contains("/to")) {
                        throw new ChatterException("eventTask must have description, /from and /to!");
                    }
                    String[] getDetails = parts[1].split(" /from ", 2);
                    String[] getTiming = getDetails[1].split(" /to ");
                    Events t = new Events(getDetails[0], getTiming[0], getTiming[1]);
                    taskList[taskCounter] = t;
                    taskCounter += 1;
                    System.out.println(line);
                    System.out.println(addingTask);
                    System.out.println("     " + t);
                    System.out.println("   Now you have " + taskCounter + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new ChatterException("Provide index!");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = taskList[index];
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
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e){
                System.out.println(line);
                System.out.println("   You don't have that many tasks!");
                System.out.println(line);
            }
        }
        sc.close();
    }
}
