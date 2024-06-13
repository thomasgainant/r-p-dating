package fr.twothirds.rpd.data;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.twothirds.rpd.entities.Interaction;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    
}
