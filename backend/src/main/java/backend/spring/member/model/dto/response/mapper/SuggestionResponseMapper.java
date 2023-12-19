package backend.spring.member.model.dto.response.mapper;

import backend.spring.member.model.dto.response.SuggestionResponse;
import backend.spring.member.model.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SuggestionResponseMapper {
    public static List<SuggestionResponse> toSuggestionResponses(List<Member> suggestions) {
        return suggestions.stream()
                .map(suggestion -> SuggestionResponseMapper.toSuggestionResponse(suggestion))
                .collect(Collectors.toList());
    }

    public static SuggestionResponse toSuggestionResponse(Member suggestion) {
        return new SuggestionResponse(
                suggestion.getUsername(),
                suggestion.getAvatarUrl(),
                false
        );
    }
}