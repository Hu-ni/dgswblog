package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Domain.*;
import kr.hs.dgsw.web02blog.Protocol.PostUserInfoProtocol;
import kr.hs.dgsw.web02blog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService ps;


    @GetMapping("/postList")
    public List<PostUserInfoProtocol> list(){return ps.postList();}

    @GetMapping("/findPost/{id}")
    public PostUserInfoProtocol findPost(@PathVariable Long id){return ps.findPost(id);}

    @PostMapping("/writePost")
    public PostUserInfoProtocol writePost(@RequestBody Post p){return ps.writePost(p);}

    @PutMapping("/updatePost/{id}")
    public PostUserInfoProtocol updatePost(@PathVariable Long id, @RequestBody Post p){return ps.updatePost(id, p);}

    @DeleteMapping("/deletePost/{id}")
    public boolean deletePost(@PathVariable Long id){return ps.deletePost(id);}
}
