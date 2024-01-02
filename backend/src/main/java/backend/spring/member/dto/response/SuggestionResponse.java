package backend.spring.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "추천 회원 응답 DTO")
public record SuggestionResponse(String name, String avatarUrl, boolean isFollow) {
    public SuggestionResponse {
    }
}
