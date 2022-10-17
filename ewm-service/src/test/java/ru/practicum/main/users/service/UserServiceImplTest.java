package ru.practicum.main.users.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.main.users.model.User;
import ru.practicum.main.users.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceImplTest {

    private final UserRepository userRepository;

    private static User user1;

    @BeforeAll
    static void beforeAll() {

        user1 = new User();
        user1.setName("Герда2");
        user1.setEmail("герда2@email.ru");

    }

    @Test
    void getUserNameById() {

        User user = userRepository.save(user1);

        String name = userRepository.findUserNameById(user.getId()).get();

        assertEquals(user.getName(), name);

    }
}