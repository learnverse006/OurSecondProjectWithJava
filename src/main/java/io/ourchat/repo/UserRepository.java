package io.ourchat.repo;

import io.ourchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//free curd
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
