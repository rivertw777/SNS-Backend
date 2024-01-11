package backend.spring.sns.service.impl;

import static backend.spring.exception.member.constants.MemberExceptionMessages.MEMBER_ID_NOT_FOUND;
import static backend.spring.exception.sns.constants.SNSExceptionMessages.ALREADY_LIKE;
import static backend.spring.exception.sns.constants.SNSExceptionMessages.ALREADY_UNLIKE;
import static backend.spring.exception.sns.constants.SNSExceptionMessages.POST_ID_NOT_FOUND;

import backend.spring.exception.member.MemberNotFoundException;
import backend.spring.exception.sns.PostLikeException;
import backend.spring.exception.sns.PostNotFoundException;
import backend.spring.sns.dto.request.CommentWriteRequest;
import backend.spring.sns.dto.request.PostSearchCondition;
import backend.spring.sns.dto.request.PostUpdateRequest;
import backend.spring.sns.dto.request.PostUploadRequest;
import backend.spring.sns.dto.response.PostSearchResult;
import backend.spring.sns.model.entity.Comment;
import backend.spring.sns.repository.CommentRepository;
import backend.spring.member.model.entity.Member;
import backend.spring.sns.model.entity.Post;
import backend.spring.sns.repository.PostRepository;
import backend.spring.sns.service.SnsService;
import backend.spring.member.repository.MemberRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public abstract class SnsServiceImpl implements SnsService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final MemberRepository memberRepository;

    // 게시물 등록
    public abstract void registerPost(Long memberId, PostUploadRequest uploadParam) throws IOException;

    // 모든 Post 조회
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 게시물 댓글 작성
    @Override
    public void writeComment(Long memberId, Long postId, CommentWriteRequest wirteParam) {
        // 로그인 중인 회원 조회
        Member member = findMember(memberId);

        // 게시물 조회
        Post post = findPost(postId);

        // 댓글 저장
        Comment comment = Comment.builder()
                .author(member)
                .post(post)
                .message(wirteParam.message())
                .build();
        commentRepository.save(comment);
    }

    // 게시물 댓글 조회
    @Override
    public List<Comment> getComments(Long postId) {
        // 게시물 조회
        Post post = findPost(postId);

        // 게시물 댓글 조회
        List<Comment> comments = commentRepository.findByPostPostId(postId);
        return comments;
    }

    // 게시물 좋아요
    @Override
    public void likePost(Long memberId, Long postId) {
        // 로그인 중인 회원 조회
        Member member = findMember(memberId);

        // 게시물 조회
        Post post = findPost(postId);

        // 이미 좋아요한 경우
        checkLike(member, post);

        // 좋아요 목록에 추가
        post.getLikeUserSet().add(member);
    }

    // 좋아요 확인
    private void checkLike(Member member, Post post) {
        if (post.isLikeUser(member.getMemberId())) {
            throw new PostLikeException(ALREADY_LIKE.getMessage());
        }
    }

    // 게시물 좋아요 취소
    @Override
    public void unlikePost(Long memberId, Long postId) {
        // 로그인 중인 회원 조회
        Member member = findMember(memberId);

        // 게시물 조회
        Post post = findPost(postId);

        // 이미 좋아요 취소한 경우
        checkUnlike(member, post);

        // 좋아요 목록에서 삭제
        post.getLikeUserSet().remove(member);
    }

    // 좋아요 취소 확인
    private void checkUnlike(Member member, Post post) {
        if (!post.isLikeUser(member.getMemberId())) {
            throw new PostLikeException(ALREADY_UNLIKE.getMessage());
        }
    }

    // 해당 회원이 게시물에 좋아요를 눌렀는지
    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        // 게시물 조회
        Post post = findPost(postId);
        return post.isLikeUser(userId);
    }

    // 검색 조건으로 게시물 조회
    @Override
    public List<PostSearchResult> searchByConditions(PostSearchCondition conditionParam) {
        return postRepository.search(conditionParam);
    }

    // 게시물 단일 조회
    @Override
    public Post getPostById(Long postId) {
        // 게시물 조회
        Post post = findPost(postId);
        return post;
    }

    // 게시물 수정
    @Override
    public void modifyPost(Long postId, PostUpdateRequest updateParam) {
        // 게시물 조회
        Post post = findPost(postId);

        // 게시물 수정
        post.setPhotoUrl(updateParam.photoUrl());
        post.setCaption(updateParam.caption());
        post.setLocation(updateParam.location());
        postRepository.save(post);
    }

    // 게시물 삭제
    @Override
    public void removePost(Long postId) {
        // 게시물 조회
        Post post = findPost(postId);

        // 게시물 삭제
        postRepository.deleteById(postId);
    }

    // 게시물 반환
    private Post findPost(Long postId){
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException(POST_ID_NOT_FOUND.getMessage()));
        return post;
    }

    // 회원 반환
    private Member findMember(Long memberId){
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_ID_NOT_FOUND.getMessage()));
        return member;
    }

}
