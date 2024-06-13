package fr.twothirds.rpd.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.twothirds.rpd.data.InteractionQueryBuilder;
import fr.twothirds.rpd.entities.Pair;
import fr.twothirds.rpd.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class InteractionService {
    @PersistenceContext
    private EntityManager entityManager;

    /*public Interaction createInteraction(){
        
    }*/
}
