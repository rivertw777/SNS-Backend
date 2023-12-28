package backend.spring.member.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 가입 요청 DTO")
public record MemberSignupRequest(String name, String password) {
    public MemberSignupRequest {
    }

}
