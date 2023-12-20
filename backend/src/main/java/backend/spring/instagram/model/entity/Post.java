package backend.spring.instagram.model.entity;

import backend.spring.instagram.model.dto.request.PostUploadRequest;
import backend.spring.member.model.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
public class Post extends BaseTimeEntity {

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "post_like_user",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private Set<Member> likeUserSet = new HashSet<>();

    @Builder
    private Post(Member author, String photoUrl, String caption, String location) {
        this.author = author;
        this.photoUrl = photoUrl;
        this.caption = caption;
        this.location = location;
    }

    public static Post create(Member author, String photoUrl, PostUploadRequest uploadParam){
        return Post.builder()
                .author(author)
                .photoUrl(photoUrl)
                .caption(uploadParam.caption())
                .location(uploadParam.location())
                .build();
    }

    public boolean isLikeUser(Long userId) {
        return likeUserSet.stream()
                .anyMatch(member -> member.getUserId().equals(userId));
    }

}