import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class VotingApp {

    private static final Map<String, Integer> candidateVotes = new HashMap<>();

    @PostMapping("/entervote")
    public String enterCandidate(@RequestParam String name) {
        candidateVotes.put(name, 0);
        return "Candidate " + name + " entered with 0 votes.";
    }

    @PostMapping("/castvote")
    public String castVote(@RequestParam String name) {
        if (candidateVotes.containsKey(name)) {
            int currentVotes = candidateVotes.get(name);
            candidateVotes.put(name, currentVotes + 1);
            return "Vote cast for " + name + ". Total votes: " + (currentVotes + 1);
        } else {
            return "Invalid candidate.";
        }
    }

    @GetMapping("/countvote")
    public String countVote(@RequestParam String name) {
        if (candidateVotes.containsKey(name)) {
            return "Vote count for " + name + ": " + candidateVotes.get(name);
        } else {
            return "Invalid candidate.";
        }
    }

    @GetMapping("/listvotes")
    public Map<String, Integer> listVotes() {
        return candidateVotes;
    }

    @GetMapping("/getwinner")
    public String getWinner() {
        String winner = candidateVotes.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No winner yet.");
        return "The winner is: " + winner;
    }

    public static void main(String[] args) {
        SpringApplication.run(VotingApp.class, args);
    }
}
