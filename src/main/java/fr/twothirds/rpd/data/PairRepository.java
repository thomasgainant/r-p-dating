package fr.twothirds.rpd.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.twothirds.rpd.entities.Pair;
import fr.twothirds.rpd.entities.User;

public interface PairRepository extends JpaRepository<Pair, Long> {
    List<Pair> findAllByOwner(User owner);
    Optional<Pair> findByOwnerOrderByIterationDesc(User owner);
}
