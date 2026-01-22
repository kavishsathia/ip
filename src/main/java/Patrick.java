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

            else if (input.equals("list")) {
                System.out.print("Assistant: Here are the tasks in your list:\n");
                System.out.print(storage.list());
                System.out.print("User: ");
            }

            else if (input.startsWith("mark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                Task task = storage.get(index);
                task.markAsDone();
                System.out.print("Assistant: Nice! I've marked this task as done:\n  " + task + "\nUser: ");
            }

            else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                Task task = storage.get(index);
                task.markAsUndone();
                System.out.print("Assistant: OK, I've marked this task as not done yet:\n  " + task + "\nUser: ");
            }

            else {
                storage.store(input);
                System.out.print("Assistant: I've added \"" + input + "\"\nUser: ");
            }
        }
    }
}
