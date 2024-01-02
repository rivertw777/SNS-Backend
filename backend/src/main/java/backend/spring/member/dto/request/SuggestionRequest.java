package backend.spring.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "추천 회원 요청 DTO")
public record SuggestionRequest(String name){
    public SuggestionRequest {
    }
}
