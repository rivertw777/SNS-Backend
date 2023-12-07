package backend.spring.model.user.dto;

public record UserUpdateDto(String username, String password) {
    public UserUpdateDto {
    }

}