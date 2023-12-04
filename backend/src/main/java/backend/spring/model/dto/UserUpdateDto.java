package backend.spring.model.dto;

public record UserUpdateDto(String userName, String userPassword) {
    public UserUpdateDto {
    }

}