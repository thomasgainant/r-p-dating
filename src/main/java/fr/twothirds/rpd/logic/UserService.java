package fr.twothirds.rpd.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.twothirds.rpd.data.InteractionQueryBuilder;
import fr.twothirds.rpd.data.PairQueryBuilder;
import fr.twothirds.rpd.data.UserQueryBuilder;
import fr.twothirds.rpd.entities.Interaction;
import fr.twothirds.rpd.entities.Pair;
import fr.twothirds.rpd.entities.User;
import fr.twothirds.rpd.entities.User.Gender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    //TODO unmock, delete this method
    public User getTestUser(){
        UserQueryBuilder<User> userQueryBuilder = new UserQueryBuilder<>(entityManager);
        return userQueryBuilder.build().setFirstResult(0).setMaxResults(1).getSingleResult();
    }

    public User getChosenFromPair(Pair pair){
        InteractionQueryBuilder<Interaction> interactionQueryBuilder = new InteractionQueryBuilder<>(this.entityManager);

        List<Long> ids = new ArrayList<>();
        ids.add(pair.getId());
        TypedQuery interactionsQuery = interactionQueryBuilder.filterByBoundPair(ids).build();

        List<Interaction> interactions = interactionsQuery.getResultList();
        if(!interactions.isEmpty()){
            for(Interaction interaction : interactions){
                if(interaction.getType() == Interaction.Type.ACCEPT){
                    return interaction.getTarget();
                 }
            }
        }
        return null;
    }

    public User getNextUserFor(User requestingUser, Long score){
        PairQueryBuilder<Pair> pairQueryBuilder = new PairQueryBuilder<>(entityManager);
        UserQueryBuilder<User> userQueryBuilder = new UserQueryBuilder<>(entityManager);

        List<Pair> pairs = pairQueryBuilder.filterByOwner(requestingUser).build().getResultList();

        TypedQuery usersQuery = userQueryBuilder
            .isNot(requestingUser)
            .filterByGender(UserService.getOppositeGender(requestingUser.getGender()))
            .filterByScore(score)
        .build();

        List<User> possiblesUsers = usersQuery.getResultList();

        User nextUser = null;
        for(User possibleUser : possiblesUsers){
            nextUser = possibleUser;

            for(Pair pair : pairs){
                if(pair.getA().getId() == possibleUser.getId() || pair.getB().getId() == possibleUser.getId()){
                    nextUser = null;
                    break;
                }
            }

            if(nextUser != null)
                break;
        }

        return nextUser;
    }

    public static Gender getOppositeGender(Gender gender){
        return gender == Gender.M ? Gender.F : Gender.M;
    }
}
