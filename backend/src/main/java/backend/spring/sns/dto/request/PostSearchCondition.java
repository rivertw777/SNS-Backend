package backend.spring.sns.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "게시물 검색 조건 요청 DTO")
public record PostSearchCondition(String memberName, String location, String caption, LocalDateTime startDate,
                                  LocalDateTime endDate) {
    public PostSearchCondition {
    }
}
