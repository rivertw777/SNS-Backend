package backend.spring.member.dto.response.mapper;

import backend.spring.member.dto.response.SuggestionResponse;
import backend.spring.member.model.entity.Member;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SuggestionResponseMapper {

    public static List<SuggestionResponse> toSuggestionResponses(List<Member> members, Member currentMember) {
        // 로그인 중인 회원 및 팔로우 중인 회원 제외
        return members.stream()
                .filter(suggestion -> !suggestion.getMemberId().equals(currentMember.getMemberId()))
                .filter(suggestion -> !isFollowingMember(suggestion, currentMember))
                .map(suggestion -> SuggestionResponseMapper.toSuggestionResponse(suggestion, currentMember))
                .collect(Collectors.toList());
    }

    public static SuggestionResponse toSuggestionResponse(Member suggestion, Member currentMember) {
        return new SuggestionResponse(
                suggestion.getName(),
                suggestion.getAvatarUrl(),
                false
        );
    }

    // 팔로잉 Set에서 팔로우 여부 확인
    private static boolean isFollowingMember(Member suggestion, Member currentMember) {
        Set<Member> followingSet = currentMember.getFollowingSet();
        // id로 일치하면 true 반환
        return followingSet.stream().anyMatch(member -> member.getMemberId().equals(suggestion.getMemberId()));
    }

}