package backend.spring.member.model.dto;

public record MemberLoginRequest(String username, String password) {
    public MemberLoginRequest {
    }

}
