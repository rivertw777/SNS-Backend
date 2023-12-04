package backend.spring.model.dto;

public record PostUpdateDto(String author, String location, String caption) {
    public PostUpdateDto {
    }

}
