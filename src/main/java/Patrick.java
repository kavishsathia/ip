import java.util.Scanner;

public class Patrick {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Scanner scanner = new Scanner(System.in);

        System.out.println(Message.GREETING.format(Message.LOGO));
        System.out.print(Message.PROMPT);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println(Message.BYE);
                break;
            }

            else if (input.equals("list")) {
                System.out.print(Message.LIST_HEADER);
                System.out.print(storage.list());
                System.out.print(Message.USER_PROMPT);
            }

            else if (input.startsWith("mark")) {
                String[] parts = input.split(" ");
                int index = -1;
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.print(Message.ERROR_INVALID_MARK);
                    continue;
                }
                Task task;

                try {
                    task = storage.get(index);
                } catch (StorageRetrievalException e) {
                    System.out.print(Message.ERROR_STORAGE.format(e.getMessage()));
                    continue;
                }

                task.markAsDone();
                System.out.print(Message.TASK_MARKED.format(task));
            }

            else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                int index = -1;
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.print(Message.ERROR_INVALID_UNMARK);
                    continue;
                }

                Task task;

                try {
                    task = storage.get(index);
                } catch (StorageRetrievalException e) {
                    System.out.print(Message.ERROR_STORAGE.format(e.getMessage()));
                    continue;
                }

                task.markAsUndone();
                System.out.print(Message.TASK_UNMARKED.format(task));
            }

            else if (input.startsWith("delete")) {
                String[] parts = input.split(" ");
                int index = -1;
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.print(Message.ERROR_INVALID_DELETE);
                    continue;
                }

                Task task;

                try {
                    task = storage.pop(index);
                } catch (StorageRetrievalException e) {
                    System.out.print(Message.ERROR_STORAGE.format(e.getMessage()));
                    continue;
                }

                System.out.print(Message.TASK_DELETED.format(task));
            }

            else if (input.startsWith("todo")) {
                if (input.length() <= 5) {
                    System.out.print(Message.ERROR_EMPTY_TODO);
                    continue;
                }
                String description = input.substring(5);
                Todo todo = new Todo(description);
                storage.store(todo);
                System.out.print(Message.TASK_ADDED.format(todo));
            }

            else if (input.startsWith("event")) {
                if (input.length() <= 6) {
                    System.out.print(Message.ERROR_EMPTY_EVENT);
                    continue;
                }

                String content = input.substring(6);
                String[] parts = content.split(" /from ");

                if (parts.length < 2) {
                    System.out.print(Message.ERROR_MISSING_FROM);
                    continue;
                }

                String description = parts[0];
                String[] times = parts[1].split(" /to ");

                if (times.length < 2) {
                    System.out.print(Message.ERROR_MISSING_TO);
                    continue;
                }

                String start = times[0];
                String end = times[1];

                Event event = new Event(description, "from " + start + " to " + end);
                storage.store(event);
                System.out.print(Message.TASK_ADDED.format(event));
            }

            else if (input.startsWith("deadline")) {
                if (input.length() <= 9) {
                    System.out.print(Message.ERROR_EMPTY_DEADLINE);
                    continue;
                }

                String content = input.substring(9);
                String[] parts = content.split(" /by ");

                if (parts.length < 2) {
                    System.out.print(Message.ERROR_MISSING_BY);
                    continue;
                }

                String description = parts[0];
                String by = parts[1];

                Deadline deadline = new Deadline(description, by);
                storage.store(deadline);
                System.out.print(Message.TASK_ADDED.format(deadline));
            }

            else {
                System.out.print(Message.ERROR_UNKNOWN_COMMAND);
            }
        }
    }
}
