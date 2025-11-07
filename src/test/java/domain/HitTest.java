package domain;

import com.example.lab3jakarta.domain.entities.Hit;
import com.example.lab3jakarta.domain.entities.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HitTest {
    @Test
    @DisplayName("Main constructor test")
    public void main_constructor_test() {
        Point point = new Point("1", "1");
        BigDecimal radius = new BigDecimal("1");

        new Hit(point, radius, true);
    }

    @Test
    @DisplayName("Correct string constructor test")
    public void correct_string_constructor_test() {
        String x = "2";
        String y = "1";
        String r = "1";

        new Hit(x, y, r, true);
    }

    @Test
    @DisplayName("Correct generic constructor test")
    public void generic_constructor_test() {
        Integer x = 2;
        short y = 1;
        Byte r = 1;

        new Hit(x, y, r, true);
    }

    @Test
    @DisplayName("Incorrect string constructor test")
    public void incorrect_string_constructor_test() {
        String x = "213ddasf";
        String y = "1";
        String r = "1";

        assertThrows(IllegalArgumentException.class, () -> new Hit(x, y, r, true));
    }

    @Test
    @DisplayName("Getter test")
    public void getter_test() {
        Hit hit = new Hit(1, 2, 3, true);

        assertAll(
                () -> assertEquals(new Point(1, 2), hit.getPoint()),
                () -> assertEquals(new BigDecimal("3"), hit.getRadius()),
                () -> assertTrue(hit.isStatus())
        );
    }

    @Test
    @DisplayName("Getter test")
    public void all_fields_constructor_test() {
        new Hit(UUID.randomUUID(), new BigDecimal(1), new BigDecimal(2), new BigDecimal(3), true);
    }
}
