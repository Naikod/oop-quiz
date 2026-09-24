package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuItemGetterTest {

    @Test
    @DisplayName("getName returns the stored name")
    void getNameReturnsName() {
        MenuItem item = new MenuItem("Es Kopi Susu", 18000);
        assertEquals("Es Kopi Susu", item.getName());
    }

    @Test
    @DisplayName("getPrice returns the stored price")
    void getPriceReturnsPrice() {
        MenuItem item = new MenuItem("Roti Bakar", 12000);
        assertEquals(12000.0, item.getPrice(), 0.001);
    }
}
