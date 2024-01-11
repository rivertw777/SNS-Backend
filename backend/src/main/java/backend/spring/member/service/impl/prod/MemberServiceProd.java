package backend.spring.member.service.impl.prod;

import backend.spring.member.dto.request.MemberSignupRequest;
import backend.spring.member.repository.MemberRepository;
import backend.spring.member.service.filter.MemberFilter;
import backend.spring.member.service.impl.MemberServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
public class MemberServiceProd extends MemberServiceImpl {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceProd(MemberRepository memberRepository,
                             PasswordEncoder passwordEncoder,
                             MemberFilter memberFilter) {
        super(memberRepository, passwordEncoder, memberFilter);
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(MemberSignupRequest signupParam) {

    }
}