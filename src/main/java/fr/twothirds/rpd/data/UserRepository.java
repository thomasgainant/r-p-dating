package fr.twothirds.rpd.data;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.twothirds.rpd.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
