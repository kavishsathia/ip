import java.util.Scanner;
public class Patrick {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String logo = " ____       _        _      _    \n"
                + "|  _ \\ __ _| |_ _ __(_) ___| | __\n"
                + "| |_) / _` | __| '__| |/ __| |/ /\n"
                + "|  __/ (_| | |_| |  | | (__|   < \n"
                + "|_|   \\__,_|\\__|_|  |_|\\___|_|\\_\\\n";
        System.out.println("Assistant: Hello from\n" + logo);
        System.out.print("What can I do for you?\nUser: ");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("Assistant: Bye. Hope to see you again soon!");
                break;
            }
            System.out.print("Assistant: " + input + "\nUser: ");
        }
    }
}
