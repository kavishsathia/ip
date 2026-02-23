package patrick;

/**
 * Contains all user-facing message strings used by the application.
 */
public enum Message {
    LOGO(" ____       _        _      _    \n"
            + "|  _ \\ __ _| |_ _ __(_) ___| | __\n"
            + "| |_) / _` | __| '__| |/ __| |/ /\n"
            + "|  __/ (_| | |_| |  | | (__|   < \n"
            + "|_|   \\__,_|\\__|_|  |_|\\___|_|\\_\\\n"),
    GREETING("Assistant: Hello from\n%s"),
    PROMPT("Is mayonnaise an instrument? No? Then what can I do for you?\nUser: "),
    USER_PROMPT("User: "),
    BYE("Assistant: Uhh... I gotta go. My house is calling me. Bye!"),
    LIST_HEADER("Assistant: Uhhh... let me think... oh yeah! Here's your stuff:\n"),
    TASK_ADDED("Assistant: Oooh! I remembered something! I added \"%s\"\nUser: "),
    TASK_MARKED("Assistant: I did it! I marked this thing as done:\n  %s\nUser: "),
    TASK_UNMARKED("Assistant: Wait... it's NOT done? Okay, unmarked:\n  %s\nUser: "),
    TASK_DELETED("Assistant: It's gone! I made it disappear:\n  %s\nUser: "),
    ERROR_INVALID_MARK("Assistant: Uhh... which one? I need a number. Usage: mark <number>\nUser: "),
    ERROR_INVALID_UNMARK("Assistant: Uhh... which one? I need a number. Usage: unmark <number>\nUser: "),
    ERROR_INVALID_DELETE("Assistant: Uhh... which one do I delete? Usage: delete <number>\nUser: "),
    ERROR_EMPTY_TODO("Assistant: The inner machinations of my mind are an enigma... "
            + "but I still need a description for your todo.\nUser: "),
    ERROR_EMPTY_EVENT("Assistant: I can't remember an event with no name! "
            + "Give me a description!\nUser: "),
    ERROR_EMPTY_DEADLINE("Assistant: A deadline with no description? "
            + "That's like a Krabby Patty with no patty!\nUser: "),
    ERROR_MISSING_FROM("Assistant: When does it start? "
            + "Usage: event <desc> /from <start> /to <end>\nUser: "),
    ERROR_MISSING_TO("Assistant: When does it end? "
            + "Usage: event <desc> /from <start> /to <end>\nUser: "),
    ERROR_MISSING_BY("Assistant: By when? I need a '/by'! "
            + "Usage: deadline <desc> /by <when>\nUser: "),
    ERROR_INVALID_DATE("Assistant: That doesn't look like a date to me... "
            + "Use yyyy-mm-dd (e.g., 2019-12-02).\nUser: "),
    FIND_HEADER("Assistant: I found some stuff! Look look look:\n"),
    ERROR_EMPTY_FIND("Assistant: Find what? You gotta tell me what to look for!\nUser: "),
    ERROR_UNKNOWN_COMMAND("Assistant: I don't know what that means. "
            + "Try: todo, deadline, event, list, mark, unmark, find, bye\nUser: "),
    ERROR_STORAGE("Assistant: %s\nUser: ");

    private final String text;

    Message(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    /**
     * Formats this message with the given arguments using {@link String#format}.
     *
     * @param args The arguments to substitute into the message template.
     * @return The formatted message string.
     */
    public String format(Object... args) {
        return String.format(this.text, args);
    }
}
