package backend.spring.exception.member.constants;

public enum MemberExceptionMessages {

    DUPLICATE_NAME("이미 존재하는 이름입니다."),
    MEMBER_ID_NOT_FOUND("해당하는 회원이 없습니다."),
    MEMBER_NAME_NOT_FOUND("해당하는 이름을 가진 회원이 없습니다.");

    private final String message;

    MemberExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
