import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import TaskClasses.*;
import DateClasses.*;
import TimeClasses.*;

public class DeadLineTest {
    @Test
    void testDeadLineParseAndToString() {
        DeadLine d = DeadLine.parse("01-01-2025 10:15");
        assertEquals("01-01-2025 10:15", d.toString());
        assertThrows(IllegalArgumentException.class, () -> DeadLine.parse("bad-deadline"));
    }
}