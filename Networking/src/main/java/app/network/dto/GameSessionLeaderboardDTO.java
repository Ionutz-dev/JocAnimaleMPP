package app.network.dto;

import java.time.LocalDateTime;

public class GameSessionLeaderboardDTO {
    private Long id;
    private String nickname;
    private LocalDateTime endTime;
    private int attempts;
    private boolean won;

    public GameSessionLeaderboardDTO(Long id, String nickname, LocalDateTime endTime, int attempts, boolean won) {
        this.id = id;
        this.nickname = nickname;
        this.endTime = endTime;
        this.attempts = attempts;
        this.won = won;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getAttempts() {
        return attempts;
    }

    public boolean isWon() {
        return won;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setWon(boolean won) {
        this.won = won;
    }
}
