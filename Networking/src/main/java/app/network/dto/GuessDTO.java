package app.network.dto;

public class GuessDTO {
    private Long id;
    private int row;
    private int col;
    private int attemptNumber;
    private String hintDirection;

    public GuessDTO(Long id, int row, int col, int attemptNumber, String hintDirection) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.attemptNumber = attemptNumber;
        this.hintDirection = hintDirection;
    }

    public Long getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public String getHintDirection() {
        return hintDirection;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public void setHintDirection(String hintDirection) {
        this.hintDirection = hintDirection;
    }
}
