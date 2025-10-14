import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import TimeClasses.*;

public class TimeTest {
    @Test
    void testTimeParseAndToString() {
        Time t = Time.parse("10:15");
        assertEquals("10:15", t.toString());
        assertThrows(IllegalArgumentException.class, () -> Time.parse("bad-time"));
    }
}