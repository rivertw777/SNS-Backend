package backend.spring.member.repository;

import backend.spring.member.model.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByMemberId(Long memberId);
}