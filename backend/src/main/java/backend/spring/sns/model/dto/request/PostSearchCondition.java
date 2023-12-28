package backend.spring.sns.model.dto.request;

import java.time.LocalDateTime;

public record PostSearchCondition(String memberName, String location, String caption, LocalDateTime startDate,
                                  LocalDateTime endDate) {
    public PostSearchCondition {
    }
}
