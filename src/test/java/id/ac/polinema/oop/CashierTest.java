package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Cashier and Order do not exist in the starter code, so this test finds
 * them via reflection instead of referencing them directly.
 */
class CashierTest {

    private static Class<?> orderClass() {
        try {
            return Class.forName("id.ac.polinema.oop.Order");
        } catch (ClassNotFoundException e) {
            return fail("Class Order not found — create it as described in the README/UML diagram");
        }
    }

    private static Object newOrderWithTotal48000() throws Throwable {
        Object order;
        try {
            order = orderClass().getConstructor(Customer.class)
                    .newInstance(new Customer("C001", "Budi Santoso"));
        } catch (NoSuchMethodException e) {
            return fail("Constructor Order(Customer) not found");
        }
        try {
            Method addItem = order.getClass().getMethod("addItem", MenuItem.class, int.class);
            addItem.invoke(order, new MenuItem("Es Kopi Susu", 18000), 2); // 36000
            addItem.invoke(order, new MenuItem("Roti Bakar", 12000), 1);   // 12000
        } catch (NoSuchMethodException e) {
            return fail("Method addItem(MenuItem, int) not found on Order");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
        return order;
    }

    private static Object newCashier() throws Exception {
        Class<?> c;
        try {
            c = Class.forName("id.ac.polinema.oop.Cashier");
        } catch (ClassNotFoundException e) {
            return fail("Class Cashier not found — create it as described in the README/UML diagram");
        }
        try {
            return c.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            return fail("Cashier needs a public no-argument constructor");
        }
    }

    private static double calculateChange(Object cashier, Object order, double cash) throws Throwable {
        Method m;
        try {
            m = cashier.getClass().getMethod("calculateChange", orderClass(), double.class);
        } catch (NoSuchMethodException e) {
            return fail("Method calculateChange(Order, double) not found on Cashier — "
                    + "Cashier only DEPENDS on Order (parameter), it must not store it");
        }
        try {
            return (double) m.invoke(cashier, order, cash);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("Class Cashier exists with calculateChange(Order, double)")
    void classAndMethodExist() throws Throwable {
        Object cashier = newCashier();
        Object order = newOrderWithTotal48000();
        calculateChange(cashier, order, 50000);
    }

    @Test
    @DisplayName("calculateChange returns cash minus final total")
    void changeIsCashMinusFinalTotal() throws Throwable {
        Object cashier = newCashier();
        Object order = newOrderWithTotal48000(); // no discount, final total 48000
        assertEquals(2000.0, calculateChange(cashier, order, 50000), 0.001);
    }

    @Test
    @DisplayName("Insufficient cash returns a negative change")
    void insufficientCashReturnsNegative() throws Throwable {
        Object cashier = newCashier();
        Object order = newOrderWithTotal48000();
        assertEquals(-8000.0, calculateChange(cashier, order, 40000), 0.001);
    }
}
