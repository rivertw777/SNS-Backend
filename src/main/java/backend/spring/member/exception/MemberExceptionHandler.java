package backend.spring.member.exception;


import backend.spring.member.exception.dto.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class MemberExceptionHandler {

    // 회원 가입 이름 중복 예외
    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<CustomErrorResponse> handleDuplicateNameException(DuplicateNameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(e.getMessage()));
    }

    // 회원 조회 실패 예외
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleMemberNotFoundExeption(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(e.getMessage()));
    }

}