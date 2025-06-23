package app.server;

import app.model.GameSession;
import app.model.Guess;
import app.network.dto.GameSessionLeaderboardDTO;
import app.network.dto.GuessDTO;
import app.network.dto.GuessResultDTO;
import app.services.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin // Enable CORS for web client
public class GameController {
    private final GameService gameService = new GameService();

    // 1. Start a new game (POST /api/game/start)
    @PostMapping("/start")
    public GameSession startGame(@RequestParam("nickname") String nickname) {
        return gameService.startGame(nickname);
    }

    // 2. Make a guess (POST /api/game/guess)
    @PostMapping("/guess")
    public GuessResultDTO makeGuess(
            @RequestParam("gameSessionId") Long gameSessionId,
            @RequestParam("row") int row,
            @RequestParam("col") int col) {
        return gameService.makeGuessDTO(gameSessionId, row, col);
    }

    // 3. Get leaderboard (GET /api/game/leaderboard)
    @GetMapping("/leaderboard")
    public List<GameSessionLeaderboardDTO> getLeaderboard() {
        return gameService.getLeaderboardDTO();
    }

    // 4. Get all guesses for a session (GET /api/game/{sessionId}/guesses)
    @GetMapping("/{sessionId}/guesses")
    public List<GuessDTO> getGuesses(@PathVariable("sessionId") Long sessionId) {
        return gameService.getGuessesForGameDTO(sessionId);
    }
}
