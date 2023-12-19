package backend.spring.member.model.entity;

import backend.spring.instagram.model.entity.Comment;
import backend.spring.instagram.model.entity.Post;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, name = "username", length = 10)
    private String username;

    @Column(name = "password", length = 60)
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "avatar_url")
    private String avatarUrl;

    @ManyToMany
    @JoinTable(
            name = "following_set",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<Member> followingSet = new HashSet<>();

    // 해당 유저를 팔로잉중인지
    public boolean isFollowingUser(Long suggestionId) {
        return followingSet.stream().anyMatch(member -> member.getUserId().equals(suggestionId));
    }

    @Builder
    private Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Member create(String username, String password){
        return builder()
                .username(username)
                .password(password)
                .build();
    }

    // 사용자의 권한 목록을 반환
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // 사용자 계정의 만료 여부
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 계정의 잠김 여부
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자 인증 정보의 만료 여부
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 계정의 활성화 여부
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
