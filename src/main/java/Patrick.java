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

            else if (input.startsWith("todo")) {
                String description = input.substring(5);
                Todo todo = new Todo(description);
                storage.store(todo);
                System.out.print("Assistant: I've added \"" + todo + "\"\nUser: ");
            }

            else if (input.startsWith("event")) {
                String content = input.substring(6);
                String[] parts = content.split(" /from ");
                String description = parts[0];
                String[] times = parts[1].split(" /to ");
                String start = times[0];
                String end = times[1];

                Event event = new Event(description, "from " + start + " to " + end);
                storage.store(event);
                    System.out.print("Assistant: I've added \"" + event + "\"\nUser: ");
            }

            else if (input.startsWith("deadline")) {
                String content = input.substring(9);
                String[] parts = content.split(" /by ");
                String description = parts[0];
                String by = parts[1];

                Deadline deadline = new Deadline(description, by);
                storage.store(deadline);
                System.out.print("Assistant: I've added \"" + deadline + "\"\nUser: ");
            }

            else {
                System.out.print("Assistant: I'm sorry, I don't understand that command.\nUser: ");
            }
        }
    }
}
