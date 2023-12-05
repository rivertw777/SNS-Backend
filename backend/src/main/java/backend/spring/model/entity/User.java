package backend.spring.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, name = "user_name", length = 10)
    private String userName;

    @Column(name = "user_password", length = 20)
    private String userPassword;

    private User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public static User create(String userName, String userPassword){
        return new User(userName, userPassword);
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
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

    // 입력값과 사용자 정보 비교
    public boolean isPasswordMatch(String name, String password) {
        return this.userName.equals(name) && this.userPassword.equals(password);
    }

}
