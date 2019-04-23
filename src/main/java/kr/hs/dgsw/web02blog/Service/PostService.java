package kr.hs.dgsw.web02blog.Service;

import kr.hs.dgsw.web02blog.Domain.Post;
import kr.hs.dgsw.web02blog.Protocol.PostUserInfoProtocol;

import java.util.List;

public interface PostService {
    List<PostUserInfoProtocol> postList();
    PostUserInfoProtocol findPost(Long userId);
    PostUserInfoProtocol writePost(Post p);
    PostUserInfoProtocol updatePost(Long id, Post p);
    boolean deletePost(Long id);
}
