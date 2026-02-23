package patrick;

import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import patrick.command.AddCommand;
import patrick.command.Command;
import patrick.command.DeleteCommand;
import patrick.command.ExitCommand;
import patrick.command.FindCommand;
import patrick.command.ListCommand;
import patrick.command.MarkCommand;
import patrick.command.UnmarkCommand;
import patrick.task.Deadline;
import patrick.task.Event;
import patrick.task.Todo;

/**
 * Parses user input and returns the corresponding {@link patrick.command.Command}.
 */
public class Parser {
    private static final Pattern TODO_PATTERN = Pattern.compile("^todo\\s+(.+)$");
    private static final Pattern DEADLINE_PATTERN = Pattern.compile("^deadline\\s+(.+)\\s+/by\\s+(.+)$");
    private static final Pattern EVENT_PATTERN = Pattern.compile("^event\\s+(.+)\\s+/from\\s+(.+)\\s+/to\\s+(.+)$");
    private static final Pattern MARK_PATTERN = Pattern.compile("^mark\\s+(\\d+)$");
    private static final Pattern UNMARK_PATTERN = Pattern.compile("^unmark\\s+(\\d+)$");
    private static final Pattern DELETE_PATTERN = Pattern.compile("^delete\\s+(\\d+)$");
    private static final Pattern FIND_PATTERN = Pattern.compile("^find\\s+(.+)$");

    /**
     * Parses the given user input string and returns the appropriate command.
     *
     * @param input The raw user input string.
     * @return The command corresponding to the user input.
     * @throws PatrickException If the input does not match any valid command format.
     */
    public static Command parse(String input) throws PatrickException {
        assert input != null : "Parser input should not be null";
        if (input.equals("bye")) {
            return new ExitCommand();
        }

        if (input.equals("list")) {
            return new ListCommand();
        }

        Matcher markMatcher = MARK_PATTERN.matcher(input);
        if (markMatcher.matches()) {
            int index = Integer.parseInt(markMatcher.group(1)) - 1;
            return new MarkCommand(index);
        }

        Matcher unmarkMatcher = UNMARK_PATTERN.matcher(input);
        if (unmarkMatcher.matches()) {
            int index = Integer.parseInt(unmarkMatcher.group(1)) - 1;
            return new UnmarkCommand(index);
        }

        Matcher deleteMatcher = DELETE_PATTERN.matcher(input);
        if (deleteMatcher.matches()) {
            int index = Integer.parseInt(deleteMatcher.group(1)) - 1;
            return new DeleteCommand(index);
        }

        Matcher findMatcher = FIND_PATTERN.matcher(input);
        if (findMatcher.matches()) {
            return new FindCommand(findMatcher.group(1));
        }

        Matcher todoMatcher = TODO_PATTERN.matcher(input);
        if (todoMatcher.matches()) {
            return new AddCommand(new Todo(todoMatcher.group(1)));
        }

        Matcher deadlineMatcher = DEADLINE_PATTERN.matcher(input);
        if (deadlineMatcher.matches()) {
            try {
                Deadline deadline = Deadline.parse(deadlineMatcher.group(1), deadlineMatcher.group(2));
                return new AddCommand(deadline);
            } catch (DateTimeParseException e) {
                throw new PatrickException(Message.ERROR_INVALID_DATE.toString());
            }
        }

        Matcher eventMatcher = EVENT_PATTERN.matcher(input);
        if (eventMatcher.matches()) {
            String description = eventMatcher.group(1);
            String start = eventMatcher.group(2);
            String end = eventMatcher.group(3);
            return new AddCommand(new Event(description, "from " + start + " to " + end));
        }

        if (input.startsWith("unmark")) {
            throw new PatrickException(Message.ERROR_INVALID_UNMARK.toString());
        }
        if (input.startsWith("mark")) {
            throw new PatrickException(Message.ERROR_INVALID_MARK.toString());
        }
        if (input.startsWith("delete")) {
            throw new PatrickException(Message.ERROR_INVALID_DELETE.toString());
        }
        if (input.startsWith("find")) {
            throw new PatrickException(Message.ERROR_EMPTY_FIND.toString());
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
