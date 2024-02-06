package backend.spring.sns.exception.constants;

public enum SnsExceptionMessages {

    POST_ID_NOT_FOUND("해당하는 게시물이 없습니다.");

    private final String message;

    SnsExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}