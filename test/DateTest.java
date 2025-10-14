import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import DateClasses.*;

public class DateTest {
    @Test
    void testDateParseAndToString() {
        Date d = Date.parse("01-01-2025");
        assertEquals("01-01-2025", d.toString());
        assertThrows(IllegalArgumentException.class, () -> Date.parse("bad-date"));
    }
}