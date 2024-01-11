package backend.spring.sns.service.impl.prod;

import static backend.spring.exception.member.constants.MemberExceptionMessages.MEMBER_ID_NOT_FOUND;

import backend.spring.exception.member.MemberNotFoundException;
import backend.spring.member.model.entity.Member;
import backend.spring.member.repository.MemberRepository;
import backend.spring.sns.dto.request.PostUploadRequest;
import backend.spring.sns.repository.CommentRepository;
import backend.spring.sns.repository.PostRepository;
import backend.spring.sns.service.impl.SnsServiceImpl;
import java.io.IOException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
public class SnsServiceProd extends SnsServiceImpl {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public SnsServiceProd(PostRepository postRepository,
                          CommentRepository commentRepository,
                          MemberRepository memberRepository) {
        super(postRepository, commentRepository, memberRepository);
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void registerPost(Long memberId, PostUploadRequest uploadParam) throws IOException {

    }

    // 회원 반환
    private Member findMember(Long memberId){
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_ID_NOT_FOUND.getMessage()));
        return member;
    }

}
