import java.util.Scanner;

public class Patrick {
    public static void main(String[] args) {
        Storage storage = new Storage();
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
                int index = -1;
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.print("Assistant: Please provide a valid task number. Usage: mark <number>\nUser: ");
                    continue;
                }
                Task task;

                try {
                    task = storage.get(index);
                } catch (StorageRetrievalException e) {
                    System.out.print("Assistant: " + e.getMessage() + "\nUser: ");
                    continue;
                }

                task.markAsDone();
                System.out.print("Assistant: Nice! I've marked this task as done:\n  " + task + "\nUser: ");
            }

            else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                int index = -1;
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.print("Assistant: Please provide a valid task number. Usage: unmark <number>\nUser: ");
                    continue;
                }

                Task task;

                try {
                    task = storage.get(index);
                } catch (StorageRetrievalException e) {
                    System.out.print("Assistant: " + e.getMessage() + "\nUser: ");
                    continue;
                }

                task.markAsUndone();
                System.out.print("Assistant: OK, I've marked this task as not done yet:\n  " + task + "\nUser: ");
            }

            else if (input.startsWith("delete")) {
                String[] parts = input.split(" ");
                int index = -1;
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.print("Assistant: Please provide a valid task number. Usage: delete <number>\nUser: ");
                    continue;
                }

                Task task;

                try {
                    task = storage.pop(index);
                } catch (StorageRetrievalException e) {
                    System.out.print("Assistant: " + e.getMessage() + "\nUser: ");
                    continue;
                }

                System.out.print("Assistant: Noted. I've removed this task:\n  " + task + "\nUser: ");
            }

            else if (input.startsWith("todo")) {
                if (input.length() <= 5) {
                    System.out.print("Assistant: The description of a todo cannot be empty.\nUser: ");
                    continue;
                }
                String description = input.substring(5);
                Todo todo = new Todo(description);
                storage.store(todo);
                System.out.print("Assistant: I've added \"" + todo + "\"\nUser: ");
            }

            else if (input.startsWith("event")) {
                if (input.length() <= 6) {
                    System.out.print("Assistant: The description of an event cannot be empty.\nUser: ");
                    continue;
                }

                String content = input.substring(6);
                String[] parts = content.split(" /from ");

                if (parts.length < 2) {
                    System.out.print("Assistant: Missing '/from'. Usage: event <desc> /from <start> /to <end>\nUser: ");
                    continue;
                }

                String description = parts[0];
                String[] times = parts[1].split(" /to ");

                if (times.length < 2) {
                    System.out.print("Assistant: Missing '/to'. Usage: event <desc> /from <start> /to <end>\nUser: ");
                    continue;
                }

                String start = times[0];
                String end = times[1];

                Event event = new Event(description, "from " + start + " to " + end);
                storage.store(event);
                    System.out.print("Assistant: I've added \"" + event + "\"\nUser: ");
            }

            else if (input.startsWith("deadline")) {
                if (input.length() <= 9) {
                    System.out.print("Assistant: The description of a deadline cannot be empty.\nUser: ");
                    continue;
                }

                String content = input.substring(9);
                String[] parts = content.split(" /by ");

                if (parts.length < 2) {
                    System.out.print("Assistant: Missing '/by'. Usage: deadline <desc> /by <when>\nUser: ");
                    continue;
                }

                String description = parts[0];
                String by = parts[1];

                Deadline deadline = new Deadline(description, by);
                storage.store(deadline);
                System.out.print("Assistant: I've added \"" + deadline + "\"\nUser: ");
            }

            else {
                System.out.print("Assistant: Unknown command. Try: todo, deadline, event, list, mark, unmark, bye\nUser: ");
            }
        }
    }
}
