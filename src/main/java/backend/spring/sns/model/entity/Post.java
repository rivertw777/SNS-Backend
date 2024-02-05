package backend.spring.sns.model.entity;

import backend.spring.member.model.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "게시물")
@Entity
@Getter
@Setter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "게시물 id")
    private Long postId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @Schema(description = "작성자 id")
    private Member author;

    @Column(name = "photo_url")
    @Schema(description = "사진 경로")
    private String photoUrl;

    @Column(name = "caption", length = 500)
    @Schema(description = "설명")
    private String caption;

    @Column(name = "location", length = 100)
    @Schema(description = "위치")
    private String location;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_like_user",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private Set<Member> likeUserSet = new HashSet<>();

    @Builder
    public Post(Member author, String photoUrl, String caption, String location) {
        this.author = author;
        this.photoUrl = photoUrl;
        this.caption = caption;
        this.location = location;
    }

    public boolean isLikeUser(Long userId) {
        return likeUserSet.stream()
                .anyMatch(member -> member.getMemberId().equals(userId));
    }

}