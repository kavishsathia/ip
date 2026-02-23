package patrick;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import patrick.command.AddCommand;
import patrick.command.Command;
import patrick.command.DeleteCommand;
import patrick.command.ExitCommand;
import patrick.command.FindCommand;
import patrick.command.ListCommand;
import patrick.command.MarkCommand;
import patrick.command.UnmarkCommand;

public class ParserTest {
    @Test
    public void parse_bye_returnsExitCommand() throws PatrickException {
        Command c = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, c);
        assertTrue(c.isExit());
    }

    @Test
    public void parse_list_returnsListCommand() throws PatrickException {
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
    }

    @Test
    public void parse_validTodo_returnsAddCommand() throws PatrickException {
        assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
    }

    @Test
    public void parse_validDeadline_returnsAddCommand() throws PatrickException {
        assertInstanceOf(AddCommand.class, Parser.parse("deadline submit /by 2024-01-01"));
    }

    @Test
    public void parse_validEvent_returnsAddCommand() throws PatrickException {
        assertInstanceOf(AddCommand.class, Parser.parse("event meeting /from 2024-01-01 /to 2024-01-02"));
    }

    @Test
    public void parse_validMark_returnsMarkCommand() throws PatrickException {
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
    }

    @Test
    public void parse_validUnmark_returnsUnmarkCommand() throws PatrickException {
        assertInstanceOf(UnmarkCommand.class, Parser.parse("unmark 1"));
    }

    @Test
    public void parse_validDelete_returnsDeleteCommand() throws PatrickException {
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1"));
    }

    @Test
    public void parse_validFind_returnsFindCommand() throws PatrickException {
        assertInstanceOf(FindCommand.class, Parser.parse("find book"));
    }

    @Test
    public void parse_emptyFind_throwsException() {
        assertThrows(PatrickException.class, () -> Parser.parse("find"));
    }

    @Test
    public void parse_emptyTodo_throwsException() {
        assertThrows(PatrickException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void parse_invalidMark_throwsException() {
        assertThrows(PatrickException.class, () -> Parser.parse("mark"));
    }

    @Test
    public void parse_deadlineMissingBy_throwsException() {
        assertThrows(PatrickException.class, () -> Parser.parse("deadline task"));
    }

    @Test
    public void parse_invalidDate_throwsException() {
        assertThrows(PatrickException.class, () -> Parser.parse("deadline task /by sunday"));
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        assertThrows(PatrickException.class, () -> Parser.parse("blah"));
    }
}
