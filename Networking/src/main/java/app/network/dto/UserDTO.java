package app.network.dto;

public class UserDTO {
    private String nickname;

    public UserDTO() {
    }

    public UserDTO(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
