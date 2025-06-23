package app.server;

import app.model.GameSession;
import app.model.Guess;
import app.network.dto.GameSessionLeaderboardDTO;
import app.network.dto.GuessDTO;
import app.services.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin // Enable CORS
public class ExamRestController {
    private final GameService gameService = new GameService();

    // REST 1: Get all unfinished games for a player (GET /api/exam/unfinished?nickname=xxx)
    @GetMapping("/unfinished")
    public List<GameSessionLeaderboardDTO> getUnfinishedGames(@RequestParam("nickname") String nickname) {
        return gameService.getUnfinishedGames(nickname).stream()
                .map(s -> new GameSessionLeaderboardDTO(
                        s.getId(),
                        s.getUser().getNickname(),
                        s.getEndTime(),
                        s.getAttempts(),
                        s.isWon()
                )).toList();
    }

    // REST 1b: Get all guesses for a specific unfinished game (GET /api/exam/unfinished/{gameId}/guesses)
    @GetMapping("/unfinished/{gameId}/guesses")
    public List<GuessDTO> getGuessesForUnfinished(@PathVariable("gameId") Long gameId) {
        return gameService.getGuessesForGameDTO(gameId);
    }

    // REST 2: Add a new Config (POST /api/exam/addConfig)
    @PostMapping("/addConfig")
    public String addConfig(@RequestParam("row") int row,
                            @RequestParam("col") int col,
                            @RequestParam("animal") String animal,
                            @RequestParam("imageUrl") String imageUrl) {
        gameService.addConfig(row, col, animal, imageUrl);
        return "Config added!";
    }
}
