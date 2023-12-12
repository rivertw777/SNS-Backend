package backend.spring.member.model.entity;

import backend.spring.instagram.model.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.apache.commons.codec.digest.DigestUtils;


@Getter
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

    @Column(name = "avatar_url")
    private String avatarUrl;

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

    public void generateAvatarUrl() {
        String serverUrl = "http://localhost:8080/users/avatars/";
        avatarUrl = serverUrl + String.valueOf(userId);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 사용자의 권한 목록을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // 사용자 계정의 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 계정의 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자 인증 정보의 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 계정의 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }


}
