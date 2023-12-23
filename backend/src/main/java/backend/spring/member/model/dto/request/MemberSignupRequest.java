package backend.spring.member.model.dto.request;

public record MemberSignupRequest(String name, String password) {
    public MemberSignupRequest {
    }

}
