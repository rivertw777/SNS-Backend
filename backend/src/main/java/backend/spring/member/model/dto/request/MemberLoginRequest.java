package backend.spring.member.model.dto.request;

public record MemberLoginRequest(String username, String password) {
    public MemberLoginRequest {
    }

}
