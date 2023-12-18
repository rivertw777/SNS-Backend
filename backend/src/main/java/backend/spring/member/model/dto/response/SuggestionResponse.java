package backend.spring.member.model.dto.response;

public record SuggestionResponse(String username, String avatarUrl, boolean isFollow) {
    public SuggestionResponse {
    }
}
