package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Domain.User;
import kr.hs.dgsw.web02blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web02blog.Protocol.ResponseType;
import kr.hs.dgsw.web02blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService us;

    @GetMapping("/userList")
    public ResponseFormat list(){
        return new ResponseFormat(ResponseType.USER_GET_ALL,this.us.userList());
    }

    @GetMapping("/findUser/{id}")
    public ResponseFormat findUser(@PathVariable Long id){
        return new ResponseFormat(ResponseType.USER_GET,us.findUser(id));
    }

    @PostMapping("/addUser")
    public ResponseFormat addUser(@RequestBody User u) {
        return new ResponseFormat(ResponseType.USER_ADD, us.addNewUser(u));
    }

    @PutMapping("/updateUser/{id}")
    public ResponseFormat updateUser(@PathVariable Long id, @RequestBody User u) {
        return new ResponseFormat(ResponseType.USER_UPDATE, us.updateUser(id, u));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseFormat deleteUser(@PathVariable Long id) {
        return new ResponseFormat(ResponseType.USER_DELETE, us.deleteUser(id));
    }
}
