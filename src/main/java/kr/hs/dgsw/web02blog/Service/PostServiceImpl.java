package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Protocol.PostUserInfoProtocol;
import kr.hs.dgsw.web02blog.Repository.PostRepository;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<PostUserInfoProtocol> postList() {
        List<PostUserInfoProtocol> pup = new ArrayList<>();
        pr.findAll(new Sort(Sort.Direction.DESC, "id")).forEach(post -> {
            String name = ur.findById(post.getUserId())
                    .map(User::getName)
                    .orElse(null);
            pup.add(new PostUserInfoProtocol(post, name));
        });
        return pup;
    }

    @Override
    public List<PostUserInfoProtocol> postList(Long id) {
        return pr.findAllByUserIdOrderByIdDesc(id)
                .map(posts -> {
                    List<PostUserInfoProtocol> pup = new ArrayList<>();
                    posts.forEach(post -> {
                       String name = ur.findById(post.getUserId())
                               .map(User::getName)
                               .orElse(null);
                       pup.add(new PostUserInfoProtocol(post, name));
                   });
                    return pup;
                })
                .orElse(null);
    }

    @Override
    public PostUserInfoProtocol findPostByPostId(Long postId){
        return pr.findById(postId)
                .map(post ->{
                    String name = ur.findById(post.getUserId())
                            .map(User::getName)
                            .orElse(null);
                    return new PostUserInfoProtocol(post, name);
                })
                .orElse(null);
    }

    @Override
    public PostUserInfoProtocol findPostByUserId(Long userId) {
        return pr.findTopByUserIdOrderByIdDesc(userId)
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
        return pr.findById(id)
                .map(post -> {
                    post.setUserId(Optional.ofNullable(p.getUserId()).orElse(post.getUserId()));
                    post.setTitle(Optional.ofNullable(p.getTitle()).orElse(post.getTitle()));
                    post.setContent(Optional.ofNullable(p.getContent()).orElse(post.getContent()));
                    post.setPictures(Optional.ofNullable(p.getPictures()).orElse(post.getPictures()));
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
