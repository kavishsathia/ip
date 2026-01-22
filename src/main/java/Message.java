public enum Message {
    LOGO(" ____       _        _      _    \n"
            + "|  _ \\ __ _| |_ _ __(_) ___| | __\n"
            + "| |_) / _` | __| '__| |/ __| |/ /\n"
            + "|  __/ (_| | |_| |  | | (__|   < \n"
            + "|_|   \\__,_|\\__|_|  |_|\\___|_|\\_\\\n"),
    GREETING("Assistant: Hello from\n%s"),
    PROMPT("What can I do for you?\nUser: "),
    USER_PROMPT("User: "),
    BYE("Assistant: Bye. Hope to see you again soon!"),
    LIST_HEADER("Assistant: Here are the tasks in your list:\n"),
    TASK_ADDED("Assistant: I've added \"%s\"\nUser: "),
    TASK_MARKED("Assistant: Nice! I've marked this task as done:\n  %s\nUser: "),
    TASK_UNMARKED("Assistant: OK, I've marked this task as not done yet:\n  %s\nUser: "),
    TASK_DELETED("Assistant: Noted. I've removed this task:\n  %s\nUser: "),
    ERROR_INVALID_MARK("Assistant: Please provide a valid task number. Usage: mark <number>\nUser: "),
    ERROR_INVALID_UNMARK("Assistant: Please provide a valid task number. Usage: unmark <number>\nUser: "),
    ERROR_INVALID_DELETE("Assistant: Please provide a valid task number. Usage: delete <number>\nUser: "),
    ERROR_EMPTY_TODO("Assistant: The description of a todo cannot be empty.\nUser: "),
    ERROR_EMPTY_EVENT("Assistant: The description of an event cannot be empty.\nUser: "),
    ERROR_EMPTY_DEADLINE("Assistant: The description of a deadline cannot be empty.\nUser: "),
    ERROR_MISSING_FROM("Assistant: Missing '/from'. Usage: event <desc> /from <start> /to <end>\nUser: "),
    ERROR_MISSING_TO("Assistant: Missing '/to'. Usage: event <desc> /from <start> /to <end>\nUser: "),
    ERROR_MISSING_BY("Assistant: Missing '/by'. Usage: deadline <desc> /by <when>\nUser: "),
    ERROR_UNKNOWN_COMMAND("Assistant: Unknown command. Try: todo, deadline, event, list, mark, unmark, bye\nUser: "),
    ERROR_STORAGE("Assistant: %s\nUser: ");

    private final String text;

    Message(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public String format(Object... args) {
        return String.format(this.text, args);
    }
}
