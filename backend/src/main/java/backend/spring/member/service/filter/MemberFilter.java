package backend.spring.member.service.filter;

import backend.spring.member.model.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MemberFilter {
    public static List<Member> toSuggestions(List<Member> members, Member currentMember) {
        return members.stream()
                .filter(suggestion -> !suggestion.getUserId().equals(currentMember.getUserId())
                        && !currentMember.isFollowingUser(suggestion.getUserId()))
                .collect(Collectors.toList());
    }
}