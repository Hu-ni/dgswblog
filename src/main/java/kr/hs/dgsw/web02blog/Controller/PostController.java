package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Domain.*;
import kr.hs.dgsw.web02blog.Protocol.PostUserInfoProtocol;
import kr.hs.dgsw.web02blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web02blog.Protocol.ResponseType;
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
    public ResponseFormat list(){
        return new ResponseFormat(ResponseType.POST_GET_ALL,ps.postList());
//        return ps.postList();
    }

    @GetMapping("/postListByUserId/{id}")
    public ResponseFormat listByUserId(@PathVariable Long id){
        return new ResponseFormat(ResponseType.POST_GET_ALL,ps.postList(id));
//        return ps.postList();
    }

    @GetMapping("findPostByPostId/{id}")
    public ResponseFormat findPostByPostId(@PathVariable Long id){
        return new ResponseFormat(ResponseType.POST_GET,ps.findPostByPostId(id));
    }

    @GetMapping("/findPostByUserId/{id}")
    public ResponseFormat findPostByUserId(@PathVariable Long id){
        return new ResponseFormat(ResponseType.POST_GET,ps.findPostByUserId(id));
    }

    @PostMapping("/writePost")
    public ResponseFormat writePost(@RequestBody Post p){
        return new ResponseFormat(ResponseType.POST_ADD,ps.writePost(p));
    }

    @PutMapping("/updatePost/{id}")
    public ResponseFormat updatePost(@PathVariable Long id, @RequestBody Post p){
        return new ResponseFormat(ResponseType.POST_UPDATE,ps.updatePost(id, p));
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseFormat deletePost(@PathVariable Long id){
        return new ResponseFormat(ResponseType.POST_DELETE,ps.deletePost(id));
    }
}
