package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuAggregationTest {

    @Test
    @DisplayName("All fields of Menu are private")
    void allFieldsPrivate() {
        for (Field f : Menu.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("A new menu is empty")
    void newMenuIsEmpty() {
        assertEquals(0, new Menu().getItemCount());
    }

    @Test
    @DisplayName("addMenuItem increases the item count")
    void addIncreasesCount() {
        Menu menu = new Menu();
        menu.addMenuItem(new MenuItem("Es Kopi Susu", 18000));
        menu.addMenuItem(new MenuItem("Roti Bakar", 12000));
        assertEquals(2, menu.getItemCount());
    }

    @Test
    @DisplayName("findItem returns the same object that was added (aggregation)")
    void findReturnsSameObject() {
        Menu menu = new Menu();
        MenuItem kopi = new MenuItem("Es Kopi Susu", 18000);
        menu.addMenuItem(kopi);
        assertSame(kopi, menu.findItem("Es Kopi Susu"));
    }

    @Test
    @DisplayName("findItem returns null when the name is not on the menu")
    void findUnknownReturnsNull() {
        Menu menu = new Menu();
        menu.addMenuItem(new MenuItem("Es Kopi Susu", 18000));
        assertNull(menu.findItem("Nasi Goreng"));
    }

    @Test
    @DisplayName("A full menu (10 items) ignores further additions")
    void fullMenuIgnoresExtraItems() {
        Menu menu = new Menu();
        for (int i = 0; i < 12; i++) {
            menu.addMenuItem(new MenuItem("Item " + i, 10000));
        }
        assertEquals(10, menu.getItemCount());
    }
}
