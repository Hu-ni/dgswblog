package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Protocol.PostUserInfoProtocol;
import kr.hs.dgsw.web02blog.Repository.PostRepository;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.hs.dgsw.web02blog.Domain.*;

import javax.annotation.PostConstruct;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository pr;
    @Autowired
    private UserRepository ur;

    @PostConstruct
    private void init(){
        ur.save(new User("a", "123", "test"));
        pr.save(new Post(1L,"123","123"));
        pr.save(new Post(1L,"123","123"));
        pr.save(new Post(1L,"123","123"));
    }

    @Override
    public List<PostUserInfoProtocol> postList() {
        List<PostUserInfoProtocol> pup = new ArrayList<PostUserInfoProtocol>();
        pr.findAll().forEach(post -> {
            String name = ur.findById(post.getUserId())
                    .map(User::getName)
                    .orElse(null);
            pup.add(new PostUserInfoProtocol(post, name));
        });
        return pup;
    }

    @Override
    public PostUserInfoProtocol findPost(Long id) {
        PostUserInfoProtocol pup = null;
        return pr.findById(id)
                .map(post -> {
                    String name = ur.findById(post.getUserId())
                            .map(User::getName)
                            .orElse(null);
                    return new PostUserInfoProtocol(post, name);
                })
                .orElse(null);
    }

    @Override
    public PostUserInfoProtocol writePost(Post p) {
        return new PostUserInfoProtocol(pr.save(p), ur.findById(p.getUserId())
        .map(User::getName)
        .orElse(null));
    }

    @Override
    public PostUserInfoProtocol updatePost(Long id, Post p) {
        return pr.findById(p.getId())
                .map(post -> {
                    post.setUserId(Optional.ofNullable(p.getUserId()).orElse(post.getUserId()));
                    post.setTitle(Optional.ofNullable(p.getTitle()).orElse(post.getTitle()));
                    post.setContent(Optional.ofNullable(p.getContent()).orElse(post.getContent()));
                    post.setFilePath(Optional.ofNullable(p.getFilePath()).orElse(post.getFilePath()));
                    post.setCreated(Optional.ofNullable(p.getCreated()).orElse(post.getCreated()));
                    post.setModified(Optional.ofNullable(p.getModified()).orElse(post.getModified()));

                    return new PostUserInfoProtocol(pr.save(post), ur.findById(post.getUserId())
                            .map(User::getName)
                            .orElse(null));
                })
                .orElse(null);
    }

    @Override
    public boolean deletePost(Long id) {
        try{
            pr.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
