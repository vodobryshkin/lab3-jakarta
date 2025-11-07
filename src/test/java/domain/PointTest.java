package domain;

import com.example.lab3jakarta.domain.entities.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    @Test
    @DisplayName("Big decimal constructor test")
    public void big_decimal_constructor_test() {
        BigDecimal x = new BigDecimal(1);
        BigDecimal y = new BigDecimal(1);

        new Point(x, y);
    }

    @Test
    @DisplayName("Correct string constructor test")
    public void correct_string_constructor_test() {
        String x = "2";
        String y = "1";

        new Point(x, y);
    }

    @Test
    @DisplayName("Incorrect string constructor test")
    public void incorrect_string_constructor_test() {
        String x = "213ddasf";
        String y = "1";

        assertThrows(IllegalArgumentException.class, () -> new Point(x, y));
    }

    @Test
    @DisplayName("Int and double constructor test")
    public void int_and_double_constructor_test() {
        int x = 1;
        double y = 1;

        new Point(x, y);
    }

    @Test
    @DisplayName("Different numbers with wrapper test")
    public void different_number_with_wrapper_test() {
        Double x = 1.0;
        byte y = 1;

        new Point(x, y);
    }

    @Test
    @DisplayName("Getter test")
    public void getter_test() {
        Point point = new Point("1", "-1");

        assertAll(
                () -> assertEquals(new BigDecimal("1"), point.getX()),
                () -> assertEquals(new BigDecimal("-1"), point.getY())
        );
    }

    @Test
    @DisplayName("toString test")
    public void to_string_test() {
        Point point = new Point("1", "-1");

        assertEquals("Point(x=1, y=-1)", point.toString());
    }

    @Test
    @DisplayName("equals test")
    public void equals_test() {
        Point point = new Point("1", "-1");
        Point point1 = new Point(1, -1);

        assertEquals(point1, point);
    }
}
