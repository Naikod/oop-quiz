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
class OrderDiscountTest {

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

    private static double finalTotal(Object order) throws Throwable {
        try {
            return (double) order.getClass().getMethod("getFinalTotal").invoke(order);
        } catch (NoSuchMethodException e) {
            return fail("Method getFinalTotal() not found on Order");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("Total below 100000 gets no discount")
    void belowThresholdNoDiscount() throws Throwable {
        Object order = newOrder(new Customer("C001", "Budi Santoso"));
        addItem(order, new MenuItem("Es Kopi Susu", 18000), 2); // 36000
        assertEquals(36000.0, finalTotal(order), 0.001);
    }

    @Test
    @DisplayName("Total exactly 100000 gets the 10% discount")
    void exactThresholdGetsDiscount() throws Throwable {
        Object order = newOrder(new Customer("C001", "Budi Santoso"));
        addItem(order, new MenuItem("Paket Meeting", 100000), 1);
        assertEquals(90000.0, finalTotal(order), 0.001);
    }

    @Test
    @DisplayName("Total above 100000 gets the 10% discount")
    void aboveThresholdGetsDiscount() throws Throwable {
        Object order = newOrder(new Customer("C001", "Budi Santoso"));
        addItem(order, new MenuItem("Es Kopi Susu", 18000), 5); // 90000
        addItem(order, new MenuItem("Roti Bakar", 12000), 5);   // 60000 -> 150000
        assertEquals(135000.0, finalTotal(order), 0.001);
    }
}
