package patrick.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void parse_validDate_success() {
        Deadline d = Deadline.parse("submit report", "2019-12-02");
        assertEquals("[D][ ] submit report (by: Dec 02 2019)", d.toString());
    }

    @Test
    public void parse_invalidDate_throwsException() {
        assertThrows(DateTimeParseException.class, () -> Deadline.parse("task", "not-a-date"));
    }

    @Test
    public void parse_trailingSpaces_success() {
        Deadline d = Deadline.parse("task", "  2020-01-15  ");
        assertEquals("[D][ ] task (by: Jan 15 2020)", d.toString());
    }

    @Test
    public void toFileString_unmarked() {
        Deadline d = new Deadline("read book", LocalDate.of(2023, 6, 1));
        assertEquals("D | 0 | read book | 2023-06-01", d.toFileString());
    }

    @Test
    public void toFileString_marked() {
        Deadline d = new Deadline("read book", LocalDate.of(2023, 6, 1));
        d.markAsDone();
        assertEquals("D | 1 | read book | 2023-06-01", d.toFileString());
    }

    @Test
    public void toString_markedDone() {
        Deadline d = Deadline.parse("homework", "2024-03-10");
        d.markAsDone();
        assertEquals("[D][X] homework (by: Mar 10 2024)", d.toString());
    }
}
