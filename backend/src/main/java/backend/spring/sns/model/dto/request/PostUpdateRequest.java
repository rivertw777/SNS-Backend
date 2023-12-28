package backend.spring.sns.model.dto.request;

public record PostUpdateRequest(String photoUrl, String caption, String location) {
    public PostUpdateRequest{
    }
}
