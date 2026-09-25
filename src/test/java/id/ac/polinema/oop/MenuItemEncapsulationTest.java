package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuItemEncapsulationTest {

    @Test
    @DisplayName("All fields of MenuItem are private")
    void allFieldsPrivate() {
        for (Field f : MenuItem.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("setPrice updates the price")
    void setPriceUpdatesPrice() {
        MenuItem item = new MenuItem("Es Kopi Susu", 18000);
        item.setPrice(20000);
        assertEquals(20000.0, item.getPrice(), 0.001);
    }

    @Test
    @DisplayName("setPrice ignores a negative price and keeps the old price")
    void setPriceIgnoresNegative() {
        MenuItem item = new MenuItem("Es Kopi Susu", 18000);
        item.setPrice(-1000);
        assertEquals(18000.0, item.getPrice(), 0.001);
    }
}
