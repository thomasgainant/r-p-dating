package fr.twothirds.rpd.logic;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.twothirds.rpd.data.InteractionQueryBuilder;
import fr.twothirds.rpd.data.PairQueryBuilder;
import fr.twothirds.rpd.data.PairRepository;
import fr.twothirds.rpd.data.UserQueryBuilder;
import fr.twothirds.rpd.entities.Pair;
import fr.twothirds.rpd.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class PairService {
    private static final Logger logger = LoggerFactory.getLogger(PairService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PairRepository pairRepository;

    @Autowired
    private UserService userService;

    public Pair getNextPairForUser(User requestingUser){
        //Get last (active) Pair given to requesting User
        Optional<Pair> activePair = this.pairRepository.findByOwnerOrderByIterationDesc(requestingUser);
        if(!activePair.isEmpty()){
            //Get who was chosen from last Pair, using bound Interaction
            User chosen = this.userService.getChosenFromPair(activePair.get());

            if(chosen != null){
                //Get the score of who was chosen
                Long currentScore = chosen.getScore();

                //Select another User with opposite gender from requesting User, having the same score and not being in a previous Pair
                //If no such User, do the same using a slightly higher score
                User opposite = this.getOppositeUserForNewPair(requestingUser, currentScore);
                if(opposite != null){
                    //Create a new Pair, using who was chosen from the requesting User last, as A and who was selected, as B
                    Pair newPair = new Pair();
                    newPair.setOwner(requestingUser);
                    newPair.setA(chosen);
                    newPair.setB(opposite);
                    newPair.setIteration(activePair.get().getIteration() + 1);
                    newPair.setCreated(Date.from(Instant.now()));
                    this.pairRepository.save(newPair);

                    return newPair;
                }
                else{
                    //Game over, no one to show
                    return null;
                }
            }
            //If no Interaction bound to active Pair yet, return this last Pair
            else{
                return activePair.get();
            }
        }
        //TODO If no last Pair, select two User with opposite gender and the same score as the requesting User
        else{
            //TODO Create a new Pair, using these two Users
            Long score = requestingUser.getScore();
            User a = this.getOppositeUserForNewPair(requestingUser, score);

            Pair newPair = new Pair();
            newPair.setOwner(requestingUser);
            newPair.setA(a);
            newPair.setB(a);
            newPair.setIteration(1);
            newPair.setCreated(Date.from(Instant.now()));
            this.pairRepository.save(newPair);

            User b = this.getOppositeUserForNewPair(requestingUser, score);
            newPair.setB(b);
            this.pairRepository.save(newPair);
            
            return newPair;
        }
    }

    private User getOppositeUserForNewPair(User requestingUser, Long currentScore){
        UserQueryBuilder<Long> userQueryBuilder = new UserQueryBuilder<>(entityManager);
        Long maxScore = userQueryBuilder.getMaxUserScore().build().getSingleResult();

        User opposite = null;
        //Break if max score and still no User, then Game Over, no one to actually select
        while(opposite == null && currentScore <= maxScore){
            opposite = this.userService.getNextUserFor(requestingUser, currentScore);
            currentScore++;
        }
        return opposite;
    }
}
