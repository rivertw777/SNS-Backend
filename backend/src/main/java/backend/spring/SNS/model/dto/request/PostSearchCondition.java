package backend.spring.SNS.model.dto.request;

import java.time.LocalDateTime;

public record PostSearchCondition(String authorName, String location, String caption, LocalDateTime createdAt) {
    public PostSearchCondition{
    }
}
