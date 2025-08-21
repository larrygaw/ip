import java.util.Scanner;

public class Chatter {
    public static void main(String[] args) {
        String line = "   ____________________________________________________________";
        String defaultMessage = "   Hello! I'm Chatter\n   What can I do for you?";
        String exitMessage = "   Bye. Hope to see you again soon!";

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
            else {
                System.out.println(line);
                System.out.println("   " + input);
                System.out.println(line);
            }
        }

        sc.close();
    }
}
