package backend.spring.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 로그인 요청 DTO")
public record MemberLoginRequest(String name, String password) {
    public MemberLoginRequest {
    }

}
