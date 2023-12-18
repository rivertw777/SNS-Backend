package backend.spring.member.model.dto.response.mapper;

import backend.spring.member.model.dto.response.SuggestionResponse;
import backend.spring.member.model.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class SuggestionResponseMapper {
    public static SuggestionResponse toMemberResponse(Member member, Long suggestionUserId) {
        return new SuggestionResponse(
                member.getUsername(),
                member.getAvatarUrl(),
                false
        );
    }
}