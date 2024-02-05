package backend.spring.member.model.entity;

import backend.spring.member.model.Role;
import backend.spring.sns.model.entity.Comment;
import backend.spring.sns.model.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "회원")
@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "회원 id")
    private Long memberId;

    @Column(unique = true, name = "username", length = 10)
    @Schema(description = "이름")
    private String name;

    @Column(name = "password", length = 60)
    @Schema(description = "비밀번호")
    private String password;

    @Column(name = "avatar_url")
    @Schema(description = "아바타 이미지 경로")
    private String avatarUrl;

    @Column(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("author")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "following_set",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<Member> followingSet = new HashSet<>();

    @Builder
    public Member(String name, String password, List<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    // 해당 유저를 팔로잉중인지
    public boolean isFollowingUser(Long suggestionId) {
        return followingSet.stream().anyMatch(member -> member.getMemberId().equals(suggestionId));
    }

}
