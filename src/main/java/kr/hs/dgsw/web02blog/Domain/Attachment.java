package kr.hs.dgsw.web02blog.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String filePath;

    private Long postId;
    @Column(unique = true)
    private Long userId;

    public Attachment(String filePath, Long postId){
        this.filePath = filePath;
        this.postId = postId;
    }

}
