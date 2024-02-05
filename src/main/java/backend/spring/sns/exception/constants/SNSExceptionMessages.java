package backend.spring.sns.exception.constants;

public enum SNSExceptionMessages {

    POST_ID_NOT_FOUND("해당하는 게시물이 없습니다."),
    ALREADY_LIKE("이미 좋아요를 눌렀습니다."),
    ALREADY_UNLIKE("이미 좋아요를 취소했습니다.");

    private final String message;

    SNSExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}