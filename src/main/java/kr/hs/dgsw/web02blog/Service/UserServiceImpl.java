package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository ur;

    @Override
    public List<User> userList() {
        return ur.findAll();
    }

    @Override
    public User findUser(Long id) {
        return ur.findById(id).orElse(null);
    }

    @Override
    public User addNewUser(User u) {
        if (ur.findByAccount(u.getAccount()).isPresent()) return null;
        else return ur.save(u);
    }

    @Override
    public User updateUser(Long id, User u) {
        return ur.findByAccount(u.getAccount())
                .map(user -> {
                    user.setAccount(Optional.ofNullable(u.getAccount()).orElse(user.getAccount()));
                    user.setPassword(Optional.ofNullable(u.getPassword()).orElse(user.getPassword()));
                    user.setName(Optional.ofNullable(u.getName()).orElse(user.getName()));
                    user.setEmail(Optional.ofNullable(u.getEmail()).orElse(user.getEmail()));
                    user.setPhone(Optional.ofNullable(u.getPhone()).orElse(user.getPhone()));
                    user.setProfile(Optional.ofNullable(u.getProfile()).orElse(user.getProfile()));

                    return ur.save(user);
                }).orElse(null);
    }

    @Override
    public boolean deleteUser(Long id) {
        try{
            ur.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
