package backend.spring.member.model.dto.request;

public record MemberSignupRequest(String username, String password) {
    public MemberSignupRequest {
    }

}
