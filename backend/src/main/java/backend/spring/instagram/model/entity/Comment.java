package backend.spring.instagram.model.entity;

import backend.spring.instagram.model.dto.request.CommentWriteRequest;
import backend.spring.member.model.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Member author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @Column(name = "message", length = 200)
    private String message;

    @Builder
    private Comment(Member author, Post post, String message) {
        this.author = author;
        this.post = post;
        this.message = message;
    }

    public static Comment create(Member author, Post post, CommentWriteRequest writeParam){
        return Comment.builder()
                .author(author)
                .post(post)
                .message(writeParam.message())
                .build();
    }
}