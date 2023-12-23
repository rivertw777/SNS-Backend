package backend.spring.member.model.dto.response;

public record SuggestionResponse(String name, String avatarUrl, boolean isFollow) {
    public SuggestionResponse {
    }
}
