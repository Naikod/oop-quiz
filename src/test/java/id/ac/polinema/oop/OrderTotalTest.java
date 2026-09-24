package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Order does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class OrderTotalTest {

    private static Object newOrder(Customer customer) throws Exception {
        Class<?> c;
        try {
            c = Class.forName("id.ac.polinema.oop.Order");
        } catch (ClassNotFoundException e) {
            return fail("Class Order not found — create it as described in the README/UML diagram");
        }
        try {
            return c.getConstructor(Customer.class).newInstance(customer);
        } catch (NoSuchMethodException e) {
            return fail("Constructor Order(Customer) not found");
        }
    }

    private static void addItem(Object order, MenuItem item, int quantity) throws Throwable {
        try {
            order.getClass().getMethod("addItem", MenuItem.class, int.class)
                    .invoke(order, item, quantity);
        } catch (NoSuchMethodException e) {
            fail("Method addItem(MenuItem, int) not found on Order");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static double total(Object order) throws Throwable {
        try {
            return (double) order.getClass().getMethod("getTotal").invoke(order);
        } catch (NoSuchMethodException e) {
            return fail("Method getTotal() not found on Order");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("An empty order has total 0")
    void emptyOrderTotalZero() throws Throwable {
        Object order = newOrder(new Customer("C001", "Budi Santoso"));
        assertEquals(0.0, total(order), 0.001);
    }

    @Test
    @DisplayName("getTotal sums the subtotal of every order item")
    void totalSumsAllSubtotals() throws Throwable {
        Object order = newOrder(new Customer("C001", "Budi Santoso"));
        addItem(order, new MenuItem("Es Kopi Susu", 18000), 2); // 36000
        addItem(order, new MenuItem("Roti Bakar", 12000), 1);   // 12000
        assertEquals(48000.0, total(order), 0.001);
    }
}
