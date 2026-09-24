package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Order does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class OrderClassTest {

    private static Class<?> loadClass() {
        try {
            return Class.forName("id.ac.polinema.oop.Order");
        } catch (ClassNotFoundException e) {
            return fail("Class Order not found — create it as described in the README/UML diagram");
        }
    }

    private static Object newOrder(Customer customer) throws Exception {
        try {
            return loadClass().getConstructor(Customer.class).newInstance(customer);
        } catch (NoSuchMethodException e) {
            return fail("Constructor Order(Customer) not found");
        }
    }

    private static Object invoke(Object target, String name) throws Throwable {
        Method m;
        try {
            m = target.getClass().getMethod(name);
        } catch (NoSuchMethodException e) {
            return fail("Method " + name + "() not found on Order");
        }
        try {
            return m.invoke(target);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("Class Order exists in package id.ac.polinema.oop")
    void classExists() {
        loadClass();
    }

    @Test
    @DisplayName("All fields of Order are private")
    void allFieldsPrivate() {
        for (Field f : loadClass().getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("getCustomer returns the same Customer object (association)")
    void getCustomerReturnsSameObject() throws Throwable {
        Customer budi = new Customer("C001", "Budi Santoso");
        Object order = newOrder(budi);
        assertSame(budi, invoke(order, "getCustomer"));
    }

    @Test
    @DisplayName("A new order has zero items")
    void newOrderIsEmpty() throws Throwable {
        Object order = newOrder(new Customer("C001", "Budi Santoso"));
        assertEquals(0, (int) invoke(order, "getItemCount"));
    }
}
