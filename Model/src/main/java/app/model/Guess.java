package app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "guesses")
public class Guess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each Guess belongs to a GameSession
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_session_id")
    private GameSession gameSession;

    @Column(nullable = false)
    private int row;

    @Column(nullable = false)
    private int col;

    @Column(nullable = false)
    private int attemptNumber;

    // Hint given after this guess: N, S, E, W, NE, NW, SE, SW, or "HIT"
    @Column(nullable = false)
    private String hintDirection;

    // Constructors
    public Guess() {
    }

    public Guess(GameSession gameSession, int row, int col, int attemptNumber, String hintDirection) {
        this.gameSession = gameSession;
        this.row = row;
        this.col = col;
        this.attemptNumber = attemptNumber;
        this.hintDirection = hintDirection;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public String getHintDirection() {
        return hintDirection;
    }

    public void setHintDirection(String hintDirection) {
        this.hintDirection = hintDirection;
    }
}
