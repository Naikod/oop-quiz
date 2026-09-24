package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuItemConstructorTest {

    private Object readField(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }

    @Test
    @DisplayName("Constructor runs without exception")
    void constructorRuns() {
        assertDoesNotThrow(() -> new MenuItem("Es Kopi Susu", 18000));
    }

    @Test
    @DisplayName("Constructor stores name into the field")
    void constructorStoresName() throws Exception {
        MenuItem item = new MenuItem("Es Kopi Susu", 18000);
        assertEquals("Es Kopi Susu", readField(item, "name"));
    }

    @Test
    @DisplayName("Constructor stores price into the field")
    void constructorStoresPrice() throws Exception {
        MenuItem item = new MenuItem("Roti Bakar", 12000);
        assertEquals(12000.0, (double) readField(item, "price"), 0.001);
    }
}
