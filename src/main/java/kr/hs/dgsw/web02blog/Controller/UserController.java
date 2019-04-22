package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService us;

    @GetMapping("/userList")
    public List<User> list(){return us.userList();}

    @GetMapping("/findUser/{id}")
    public User findUser(@PathVariable Long id){return us.findUser(id);}

    @PostMapping("/addUser")
    public User addUser(@RequestBody User u) {return us.addNewUser(u);}

    @PutMapping("/updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User u) {return us.updateUser(id, u);}

    @DeleteMapping("/deleteUser/{id}")
    public boolean deleteUser(@PathVariable Long id) {return us.deleteUser(id);}
}
