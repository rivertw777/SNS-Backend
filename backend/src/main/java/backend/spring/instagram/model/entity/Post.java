package backend.spring.instagram.model.entity;

import backend.spring.instagram.model.dto.PostUploadDto;
import backend.spring.member.model.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "posts")
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Member author;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "caption", length = 500)
    private String caption;

    @Column(name = "location", length = 100)
    private String location;

    @Builder
    private Post(Member author, String photoUrl, String caption, String location) {
        this.author = author;
        this.photoUrl = photoUrl;
        this.caption = caption;
        this.location = location;
    }

    public static Post create(Member user, String photoUrl, PostUploadDto uploadParam){
        return Post.builder()
                .author(user)
                .photoUrl(photoUrl)
                .caption(uploadParam.caption())
                .location(uploadParam.location())
                .build();
    }
}