import java.util.Scanner;

public class Patrick {
    public static void main(String[] args) {
        Storage storage = new Storage(100);
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

            if (input.equals("list")) {
                System.out.print("Assistant: Here are the tasks in your list:\n");
                System.out.print(storage.list());
                System.out.print("User: ");
                continue;
            }

            storage.store(input);
            System.out.print("Assistant: I've added \"" + input + "\"\nUser: ");
        }
    }
}
