import java.util.Scanner;

public class Chatter {
    public static void main(String[] args) {
        String line = "   ____________________________________________________________";
        String defaultMessage = "   Hello! I'm Chatter\n   What can I do for you?";
        String exitMessage = "   Bye. Hope to see you again soon!";

        //storage for task
        String[] taskList = new String[100];
        int taskCounter = 0;

        System.out.println(line);
        System.out.println(defaultMessage);
        System.out.println(line);

        Scanner sc = new Scanner(System.in);

        while(true) {
            //to read what user types on keyboard
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println(exitMessage);
                System.out.println(line);
                break;
            }
            else if (input.equals("list")) {
                System.out.println(line);
                //print every task in taskList
                for(int i = 0; i < taskCounter; i++) {
                    System.out.println("   " + (i + 1) + ". " + taskList[i]);
                }
                System.out.println(line);
            }
            else {
                System.out.println(line);
                System.out.println("   added: " + input);
                System.out.println(line);
                taskList[taskCounter] = input;
                taskCounter += 1;
            }
        }
        sc.close();
    }
}
