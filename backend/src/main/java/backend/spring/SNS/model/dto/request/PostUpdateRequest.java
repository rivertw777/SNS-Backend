package backend.spring.SNS.model.dto.request;

public record PostUpdateRequest(String photoUrl, String caption, String location) {
    public PostUpdateRequest{
    }
}
