package kr.hs.dgsw.web02blog.Protocol;

import kr.hs.dgsw.web02blog.Domain.Post;
import lombok.Data;

@Data
public class PostUserInfoProtocol extends Post {
    private String name;

    public PostUserInfoProtocol(Post p, String name) {
        super(p);
        this.name = name;
    }
}
