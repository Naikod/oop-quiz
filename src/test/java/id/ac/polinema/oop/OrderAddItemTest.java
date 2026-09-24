package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Order does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class OrderAddItemTest {

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
        Method m;
        try {
            m = order.getClass().getMethod("addItem", MenuItem.class, int.class);
        } catch (NoSuchMethodException e) {
            fail("Method addItem(MenuItem, int) not found on Order — "
                    + "Order must create the OrderItem internally (composition)");
            return;
        }
        try {
            m.invoke(order, item, quantity);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static int itemCount(Object order) throws Throwable {
        try {
            return (int) order.getClass().getMethod("getItemCount").invoke(order);
        } catch (NoSuchMethodException e) {
            return fail("Method getItemCount() not found on Order");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("addItem(MenuItem, int) exists — Order builds the OrderItem itself (composition)")
    void addItemSignatureExists() throws Throwable {
        Object order = newOrder(new Customer("C001", "Budi Santoso"));
        addItem(order, new MenuItem("Es Kopi Susu", 18000), 1);
    }

    @Test
    @DisplayName("Each addItem increases the item count by one")
    void addItemIncreasesCount() throws Throwable {
        Object order = newOrder(new Customer("C001", "Budi Santoso"));
        addItem(order, new MenuItem("Es Kopi Susu", 18000), 2);
        assertEquals(1, itemCount(order));
        addItem(order, new MenuItem("Roti Bakar", 12000), 1);
        assertEquals(2, itemCount(order));
    }
}
