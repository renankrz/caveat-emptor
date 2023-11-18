package dev.renankrz.caveatemptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Test;

import dev.renankrz.caveatemptor.model.Projection;
import dev.renankrz.caveatemptor.model.User;

public class ProjectionTest extends CaveatEmptorTests {

    @Test
    void testProjectionUsername() {
        List<Projection.UsernameOnly> users = userRepository.findByEmail("john@somedomain.com");

        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("john", users.get(0).getUsername()));
    }

    @Test
    void testProjectionUserSummary() {
        List<Projection.UserSummary> users = userRepository
                .findByRegistrationDateAfter(LocalDate.of(2021, Month.FEBRUARY, 1));

        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("julius", users.get(0).getUsername()),
                () -> assertEquals("julius julius@someotherdomain.com", users.get(0).getInfo()));
    }

    @Test
    void testDynamicProjection() {
        List<Projection.UsernameOnly> usernames = userRepository
                .findByEmail("mike@somedomain.com", Projection.UsernameOnly.class);
        List<User> users = userRepository
                .findByEmail("mike@somedomain.com", User.class);

        assertAll(
                () -> assertEquals(1, usernames.size()),
                () -> assertEquals("mike", usernames.get(0).getUsername()),
                () -> assertEquals(1, users.size()),
                () -> assertEquals("mike", users.get(0).getUsername()));
    }

}
