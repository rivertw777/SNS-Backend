package backend.spring.member.model.dto.request;

public record MemberLoginRequest(String name, String password) {
    public MemberLoginRequest {
    }

}
