package mock_testing_l8.repository;

import mock_testing_l8.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    AuthUser findByUsername(String username);
}