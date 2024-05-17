package mock_testing_l8.repository;

import mock_testing_l8.entity.AuthUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AuthUserRepositoryTest {
    @Autowired
    AuthUserRepository repository;

    @Test
    @Order(1)
    void save() {
        AuthUser authUser = AuthUser.builder()
                .id(1)
                .username("Simple")
                .email("simple@gmail.com")
                .password("123")
                .build();

        AuthUser savedUser = repository.save(authUser);

        assertThat(savedUser).isNotNull();
        assertEquals(authUser.getUsername(), savedUser.getUsername());
        assertThat(savedUser.getEmail()).isEqualTo(authUser.getEmail());
    }

    @Test
    @Order(2)
    void findById() {
        AuthUser authUser = new AuthUser();
        authUser.setUsername("Simple");
        authUser.setEmail("simple@gmail.com");
        authUser.setPassword("123");

        AuthUser savedUser = repository.save(authUser);

        AuthUser foundUser = repository.findById(savedUser.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals(savedUser.getId(), foundUser.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        repository.saveAll(List.of(
                new AuthUser(1, "Panda", "panda@gmail.com", "9779"),
                new AuthUser(2, "Simple", "simple@gmail.com", "123")));

        List<AuthUser> authUsers = repository.findAll();

        assertEquals(2, authUsers.size());
    }

    @Test
    @Order(4)
    void deleteById() {
        AuthUser authUser = AuthUser.builder()
                .username("Simple")
                .email("simple@gmail.com")
                .password("123")
                .build();
        AuthUser savedUser = repository.save(authUser);
        repository.deleteById(savedUser.getId());

        assertTrue(repository.findById(savedUser.getId()).isEmpty());
    }


}