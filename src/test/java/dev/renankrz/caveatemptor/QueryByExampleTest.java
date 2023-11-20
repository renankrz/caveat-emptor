package dev.renankrz.caveatemptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import dev.renankrz.caveatemptor.model.User;

public class QueryByExampleTest extends CaveatEmptorTests {

    @Test
    void testEmailWithQueryByExample() {
        User user = new User();
        user.setEmail("@someotherdomain.com");

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("level", "active")
                .withMatcher("email", match -> match.endsWith());

        Example<User> example = Example.of(user, matcher);
        List<User> users = userRepository.findAll(example);
        assertEquals(4, users.size());
    }

    @Test
    void testUsernameWithQueryByExample() {
        User user = new User();
        user.setUsername("J");

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("level", "active")
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();

        Example<User> example = Example.of(user, matcher);
        List<User> users = userRepository.findAll(example);
        assertEquals(3, users.size());
    }

}
