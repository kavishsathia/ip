package patrick;

import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import patrick.command.AddCommand;
import patrick.command.Command;
import patrick.command.DeleteCommand;
import patrick.command.ExitCommand;
import patrick.command.ListCommand;
import patrick.command.MarkCommand;
import patrick.command.UnmarkCommand;
import patrick.task.Deadline;
import patrick.task.Event;
import patrick.task.Todo;

public class Parser {
    private static final Pattern TODO_PATTERN = Pattern.compile("^todo\\s+(.+)$");
    private static final Pattern DEADLINE_PATTERN = Pattern.compile("^deadline\\s+(.+)\\s+/by\\s+(.+)$");
    private static final Pattern EVENT_PATTERN = Pattern.compile("^event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)$");
    private static final Pattern MARK_PATTERN = Pattern.compile("^mark\\s+(\\d+)$");
    private static final Pattern UNMARK_PATTERN = Pattern.compile("^unmark\\s+(\\d+)$");
    private static final Pattern DELETE_PATTERN = Pattern.compile("^delete\\s+(\\d+)$");

    public static Command parse(String input) throws PatrickException {
        if (input.equals("bye")) {
            return new ExitCommand();
        }

        if (input.equals("list")) {
            return new ListCommand();
        }

        Matcher matcher;

        matcher = MARK_PATTERN.matcher(input);
        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1)) - 1;
            return new MarkCommand(index);
        }

        matcher = UNMARK_PATTERN.matcher(input);
        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1)) - 1;
            return new UnmarkCommand(index);
        }

        matcher = DELETE_PATTERN.matcher(input);
        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1)) - 1;
            return new DeleteCommand(index);
        }

        matcher = TODO_PATTERN.matcher(input);
        if (matcher.matches()) {
            return new AddCommand(new Todo(matcher.group(1)));
        }

        matcher = DEADLINE_PATTERN.matcher(input);
        if (matcher.matches()) {
            try {
                Deadline deadline = Deadline.parse(matcher.group(1), matcher.group(2));
                return new AddCommand(deadline);
            } catch (DateTimeParseException e) {
                throw new PatrickException(Message.ERROR_INVALID_DATE.toString());
            }
        }

        matcher = EVENT_PATTERN.matcher(input);
        if (matcher.matches()) {
            String description = matcher.group(1);
            String start = matcher.group(2);
            String end = matcher.group(3);
            return new AddCommand(new Event(description, "from " + start + " to " + end));
        }

        if (input.startsWith("mark") || input.startsWith("unmark")) {
            throw new PatrickException(input.startsWith("mark")
                    ? Message.ERROR_INVALID_MARK.toString() : Message.ERROR_INVALID_UNMARK.toString());
        }
        if (input.startsWith("delete")) {
            throw new PatrickException(Message.ERROR_INVALID_DELETE.toString());
        }
        if (input.startsWith("todo")) {
            throw new PatrickException(Message.ERROR_EMPTY_TODO.toString());
        }
        if (input.startsWith("deadline")) {
            if (!input.contains("/by")) {
                throw new PatrickException(Message.ERROR_MISSING_BY.toString());
            }
            throw new PatrickException(Message.ERROR_EMPTY_DEADLINE.toString());
        }
        if (input.startsWith("event")) {
            if (!input.contains("/from")) {
                throw new PatrickException(Message.ERROR_MISSING_FROM.toString());
            }
            if (!input.contains("/to")) {
                throw new PatrickException(Message.ERROR_MISSING_TO.toString());
            }
            throw new PatrickException(Message.ERROR_EMPTY_EVENT.toString());
        }

        throw new PatrickException(Message.ERROR_UNKNOWN_COMMAND.toString());
    }
}
