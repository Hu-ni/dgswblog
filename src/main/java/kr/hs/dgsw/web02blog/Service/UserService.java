package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.*;

import java.util.List;

public interface UserService {
    List<User> userList();
    User findUser(Long id);
    User addNewUser(User u);
    User updateUser(Long id, User u);
    boolean deleteUser(Long id);
}
