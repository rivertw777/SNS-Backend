package backend.spring.instagram.service.impl;

import backend.spring.instagram.model.dto.request.CommentWriteRequest;
import backend.spring.instagram.model.entity.Comment;
import backend.spring.instagram.model.entity.Post;
import backend.spring.instagram.repository.CommentRepository;
import backend.spring.instagram.repository.PostRepository;
import backend.spring.instagram.service.CommentService;
import backend.spring.member.model.entity.Member;
import backend.spring.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentServiceimpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    @Override
    public void writeComment(String username, Long postId, CommentWriteRequest wirteParam) {
        // 게시물과 작성자 반환
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 없습니다."));
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 이름을 가진 회원이 없습니다."));

        Comment comment = Comment.create(member, post, wirteParam);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Long postId) {
        // 게시물이 있는지 확인
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 없습니다."));

        List<Comment> comments = commentRepository.findByPostPostId(postId);
        return comments;
    }

}
