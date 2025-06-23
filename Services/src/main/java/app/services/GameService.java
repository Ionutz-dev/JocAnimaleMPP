package app.services;

import app.model.Config;
import app.model.GameSession;
import app.model.Guess;
import app.model.User;
import app.network.dto.GameSessionLeaderboardDTO;
import app.network.dto.GuessDTO;
import app.network.dto.GuessResultDTO;
import app.persistence.ConfigDAO;
import app.persistence.GameSessionDAO;
import app.persistence.GuessDAO;
import app.persistence.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final UserDAO userDAO = new UserDAO();
    private final ConfigDAO configDAO = new ConfigDAO();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();
    private final GuessDAO guessDAO = new GuessDAO();

    private static final int MAX_ATTEMPTS = 3;

    public GameSession startGame(String nickname) {
        User user = userDAO.findByNickname(nickname);
        if (user == null)
            throw new RuntimeException("Nickname not registered!");

        List<Config> configs = configDAO.findAll();
        if (configs.isEmpty()) throw new RuntimeException("No animal configs found in database!");

        Config randomConfig = configs.get(new Random().nextInt(configs.size()));
        GameSession session = new GameSession(user, randomConfig, LocalDateTime.now());
        gameSessionDAO.save(session);

        logger.info("New game started for player: {} with config: {}", nickname, randomConfig.getId());

        return session;
    }

    public GuessResultDTO makeGuessDTO(Long gameSessionId, int row, int col) {
        String result = makeGuess(gameSessionId, row, col);
        GameSession session = gameSessionDAO.findById(gameSessionId);
        String animal = session.getConfig().getAnimal();
        String imageUrl = session.getConfig().getImageUrl();
        return new GuessResultDTO(result, animal, imageUrl);
    }


    public String makeGuess(Long gameSessionId, int row, int col) {
        GameSession session = gameSessionDAO.findById(gameSessionId);
        if (session == null) throw new RuntimeException("GameSession not found!");
        if (session.isWon() || session.getAttempts() >= MAX_ATTEMPTS) {
            return "Game over!";
        }

        Config config = session.getConfig();
        int attemptNum = session.getAttempts() + 1;

        String hint;
        if (row == config.getRow() && col == config.getCol()) {
            hint = "HIT";
            session.setWon(true);
            session.setEndTime(LocalDateTime.now());
        } else {
            hint = computeHint(config.getRow(), config.getCol(), row, col);
            if (attemptNum == MAX_ATTEMPTS) {
                session.setEndTime(LocalDateTime.now());
            }
        }

        session.setAttempts(attemptNum);
        gameSessionDAO.save(session);

        Guess guess = new Guess(session, row, col, attemptNum, hint);
        guessDAO.save(guess);

        logger.info("Guess: sessionId={}, guess=({},{}), result={}", gameSessionId, row, col, hint);

        return hint;
    }

    public List<GameSession> getLeaderboard() {
        return gameSessionDAO.findFinishedGamesOrdered();
    }

    public List<GameSessionLeaderboardDTO> getLeaderboardDTO() {
        List<GameSession> sessions = getLeaderboard();

        return sessions.stream()
                .map(s -> new GameSessionLeaderboardDTO(
                        s.getId(),
                        s.getUser().getNickname(),
                        s.getEndTime(),
                        s.getAttempts(),
                        s.isWon()
                ))
                .toList();
    }

    private String computeHint(int targetRow, int targetCol, int guessRow, int guessCol) {
        int dRow = guessRow - targetRow;
        int dCol = guessCol - targetCol;
        if (dRow == 0 && dCol < 0) return "E";
        if (dRow == 0 && dCol > 0) return "W";
        if (dRow < 0 && dCol == 0) return "S";
        if (dRow > 0 && dCol == 0) return "N";
        if (dRow < 0 && dCol < 0) return "SE";
        if (dRow < 0 && dCol > 0) return "SW";
        if (dRow > 0 && dCol < 0) return "NE";
        if (dRow > 0 && dCol > 0) return "NW";
        return "???";
    }

    public List<GameSession> getUnfinishedGames(String nickname) {
        return gameSessionDAO.findUnfinishedByNickname(nickname);
    }

    public void addConfig(int row, int col, String animal, String imageUrl) {
        configDAO.save(new Config(row, col, animal, imageUrl));
        logger.info("New config added: animal={}, row={}, col={}, imageUrl={}", animal, row, col, imageUrl);
    }

    public List<Guess> getGuessesForGame(Long gameSessionId) {
        return guessDAO.findByGameSessionId(gameSessionId);
    }

    public List<GuessDTO> getGuessesForGameDTO(Long gameSessionId) {
        List<Guess> guesses = getGuessesForGame(gameSessionId);
        return guesses.stream()
                .map(g -> new GuessDTO(
                        g.getId(),
                        g.getRow(),
                        g.getCol(),
                        g.getAttemptNumber(),
                        g.getHintDirection()
                ))
                .toList();
    }
}
