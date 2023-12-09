package backend.spring.post.model.entity;

import backend.spring.post.model.dto.PostUploadDto;
import backend.spring.member.model.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Member author;

    @Column(name = "photo_paths")
    private List<String> photoPaths;

    @Column(name = "caption", length = 500)
    private String caption;

    //@ManyToMany
    //@JoinTable(
    //        name = "post_tag",
    //        joinColumns = @JoinColumn(name = "post_id"),
    //        inverseJoinColumns = @JoinColumn(name = "tag_id")
    //)
    //private List<Tag> tagSet;

    @Column(name = "location", length = 100)
    private String location;

    //@ManyToMany(mappedBy = "likePostSet")
    //prvate Set<User> likeUserSet;i

    @Builder
    private Post(Member author, List<String> photoPaths, String caption, String location) {
        this.author = author;
        this.photoPaths = photoPaths;
        this.caption = caption;
        this.location = location;
    }

    public static Post create(Member user, List<String> photoPaths, PostUploadDto uploadParam){
        return Post.builder()
                .author(user)
                .photoPaths(photoPaths)
                .caption(uploadParam.caption())
                .location(uploadParam.location())
                .build();
    }
}