package fr.twothirds.rpd.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.twothirds.rpd.entities.Pair;
import fr.twothirds.rpd.entities.User;
import fr.twothirds.rpd.logic.PairService;
import fr.twothirds.rpd.logic.UserService;

@RestController
@RequestMapping("/api/pairs")
public class PairController {
    @Autowired
    private PairService pairService;

    //TODO unmock, delete this service
    @Autowired
    private UserService userService;

    @GetMapping("/next")
    public ResponseEntity<Pair> getNextPair(){
        //TODO unmock
        User testUser = this.userService.getTestUser();

        return ResponseEntity.ok(this.pairService.getNextPairForUser(testUser));
    }
}
