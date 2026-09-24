package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * OrderItem does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class OrderItemClassTest {

    private static Class<?> loadClass() {
        try {
            return Class.forName("id.ac.polinema.oop.OrderItem");
        } catch (ClassNotFoundException e) {
            return fail("Class OrderItem not found — create it as described in the README/UML diagram");
        }
    }

    @Test
    @DisplayName("Class OrderItem exists in package id.ac.polinema.oop")
    void classExists() {
        loadClass();
    }

    @Test
    @DisplayName("All fields of OrderItem are private")
    void allFieldsPrivate() {
        for (Field f : loadClass().getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("OrderItem has a constructor (MenuItem, int)")
    void hasConstructor() {
        try {
            loadClass().getConstructor(MenuItem.class, int.class);
        } catch (NoSuchMethodException e) {
            fail("Constructor OrderItem(MenuItem, int) not found");
        }
    }

    @Test
    @DisplayName("OrderItem has getMenuItem(), getQuantity(), getSubtotal() with correct return types")
    void hasGetters() {
        Class<?> c = loadClass();
        try {
            assertEquals(MenuItem.class, c.getMethod("getMenuItem").getReturnType(),
                    "getMenuItem() must return MenuItem");
            assertEquals(int.class, c.getMethod("getQuantity").getReturnType(),
                    "getQuantity() must return int");
            assertEquals(double.class, c.getMethod("getSubtotal").getReturnType(),
                    "getSubtotal() must return double");
        } catch (NoSuchMethodException e) {
            fail("Missing method on OrderItem: " + e.getMessage());
        }
    }
}
