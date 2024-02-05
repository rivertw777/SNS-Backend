package backend.spring.sns.exception;

import backend.spring.sns.exception.dto.CustomErrorResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class SnsExceptionHandler {

    // 게시물 업로드 실패 예외
    @ExceptionHandler(IOException.class)
    public ResponseEntity<CustomErrorResponse> handleIOException(IOException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(e.getMessage()));
    }

    // 게시물 조회 실패 예외
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handlePostNotFoundExeption(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(e.getMessage()));
    }

    // 게시물 좋아요 예외
    @ExceptionHandler(PostLikeException.class)
    public ResponseEntity<CustomErrorResponse> handlePostLikeExeption(PostLikeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(e.getMessage()));
    }

}
