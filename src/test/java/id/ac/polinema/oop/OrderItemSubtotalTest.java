package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * OrderItem does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class OrderItemSubtotalTest {

    private static Object newOrderItem(MenuItem item, int quantity) throws Exception {
        Class<?> c;
        try {
            c = Class.forName("id.ac.polinema.oop.OrderItem");
        } catch (ClassNotFoundException e) {
            return fail("Class OrderItem not found — create it as described in the README/UML diagram");
        }
        try {
            return c.getConstructor(MenuItem.class, int.class).newInstance(item, quantity);
        } catch (NoSuchMethodException e) {
            return fail("Constructor OrderItem(MenuItem, int) not found");
        }
    }

    private static Object invoke(Object target, String name) throws Throwable {
        Method m;
        try {
            m = target.getClass().getMethod(name);
        } catch (NoSuchMethodException e) {
            return fail("Method " + name + "() not found on OrderItem");
        }
        try {
            return m.invoke(target);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("getSubtotal returns price times quantity")
    void subtotalIsPriceTimesQuantity() throws Throwable {
        MenuItem kopi = new MenuItem("Es Kopi Susu", 18000);
        Object orderItem = newOrderItem(kopi, 3);
        assertEquals(54000.0, (double) invoke(orderItem, "getSubtotal"), 0.001);
    }

    @Test
    @DisplayName("getMenuItem returns the same MenuItem object (association)")
    void getMenuItemReturnsSameObject() throws Throwable {
        MenuItem roti = new MenuItem("Roti Bakar", 12000);
        Object orderItem = newOrderItem(roti, 1);
        assertSame(roti, invoke(orderItem, "getMenuItem"));
    }

    @Test
    @DisplayName("getQuantity returns the stored quantity")
    void getQuantityReturnsQuantity() throws Throwable {
        MenuItem roti = new MenuItem("Roti Bakar", 12000);
        Object orderItem = newOrderItem(roti, 4);
        assertEquals(4, (int) invoke(orderItem, "getQuantity"));
    }
}
