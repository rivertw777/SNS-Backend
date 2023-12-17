package backend.spring.member.model.dto;

public record MemberSignupRequest(String username, String password) {
    public MemberSignupRequest {
    }

}
