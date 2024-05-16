package mock_testing_l8.repository;

import mock_testing_l8.entity.AuthUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                .username("Simple")
                .email("simple@gmail.com")
                .password("123")
                .build();
        repository.save(authUser);

        AuthUser savedUser = repository.findByUsername(authUser.getUsername());

        assertThat(savedUser).isNotNull();
        assertEquals(authUser.getUsername(), savedUser.getUsername());
        assertThat(savedUser.getEmail()).isEqualTo(authUser.getEmail());
    }

    @Test
    @Order(2)
    void findById() {
        AuthUser authUser = AuthUser.builder()
                .id(1)
                .username("Simple")
                .email("simple@gmail.com")
                .password("123")
                .build();
        repository.save(authUser);
        Optional<AuthUser> savedAuthUser = repository.findById(1);
        assertEquals(1, savedAuthUser.get().getId());
    }

    @Test
    @Order(3)
    void findAll() {
        repository.saveAll(List.of(
                new AuthUser(1, "Panda", "panda@gmail.com", "9779"),
                new AuthUser(2,"Simple","simple@gmail.com","123")));
        List<AuthUser> authUsers = repository.findAll();
        assertEquals(2, authUsers.size());
    }

    @Test
    @Order(4)
    void deleteById() {
        AuthUser authUser = AuthUser.builder()
                .id(1)
                .username("Simple")
                .email("simple@gmail.com")
                .password("123")
                .build();
        repository.save(authUser);
        System.out.println("AAAAA: " + repository.findById(1).get().getUsername());
        repository.deleteById(1);
        assertTrue(repository.findById(1).isEmpty());
    }


}