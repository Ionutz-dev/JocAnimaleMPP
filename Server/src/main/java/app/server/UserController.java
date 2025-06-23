package app.server;

import app.model.User;
import app.network.dto.UserDTO;
import app.persistence.UserDAO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserDAO userDAO = new UserDAO();

    @PostMapping("/register")
    public UserDTO registerUser(@RequestParam("nickname") String nickname) {
        if (userDAO.findByNickname(nickname) != null)
            throw new RuntimeException("Nickname already registered!");
        User user = new User(nickname);
        userDAO.save(user);
        return new UserDTO(user.getNickname());
    }

    @GetMapping("/{nickname}")
    public UserDTO getUser(@PathVariable("nickname") String nickname) {
        User user = userDAO.findByNickname(nickname);
        if (user == null)
            throw new RuntimeException("User not found!");
        return new UserDTO(user.getNickname());
    }
}

