package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {

    @Test
    @DisplayName("All fields of Customer are private")
    void allFieldsPrivate() {
        for (Field f : Customer.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("Constructor stores customerId and name, getters return them")
    void constructorAndGetters() {
        Customer c = new Customer("C001", "Budi Santoso");
        assertEquals("C001", c.getCustomerId());
        assertEquals("Budi Santoso", c.getName());
    }

    @Test
    @DisplayName("setName updates the name")
    void setNameUpdatesName() {
        Customer c = new Customer("C001", "Budi Santoso");
        c.setName("Budi S.");
        assertEquals("Budi S.", c.getName());
    }

    @Test
    @DisplayName("setName rejects null with IllegalArgumentException")
    void setNameRejectsNull() {
        Customer c = new Customer("C001", "Budi Santoso");
        assertThrows(IllegalArgumentException.class, () -> c.setName(null));
    }

    @Test
    @DisplayName("setName rejects a blank name with IllegalArgumentException")
    void setNameRejectsBlank() {
        Customer c = new Customer("C001", "Budi Santoso");
        assertThrows(IllegalArgumentException.class, () -> c.setName("   "));
    }
}
