package backend.spring.sns.service.impl;

import static backend.spring.member.exception.constants.MemberExceptionMessages.MEMBER_ID_NOT_FOUND;
import static backend.spring.sns.exception.constants.SnsExceptionMessages.POST_ID_NOT_FOUND;

import backend.spring.member.exception.MemberNotFoundException;
import backend.spring.sns.exception.PostNotFoundException;
import backend.spring.file.service.FileService;
import backend.spring.file.FileServiceFactory;
import backend.spring.sns.dto.request.CommentWriteRequest;
import backend.spring.sns.dto.request.PostSearchCondition;
import backend.spring.sns.dto.request.PostUploadRequest;
import backend.spring.sns.dto.response.CommentResponse;
import backend.spring.sns.dto.response.PostResponse;
import backend.spring.sns.dto.response.PostSearchResult;
import backend.spring.sns.dto.response.mapper.CommentResponseMapper;
import backend.spring.sns.dto.response.mapper.PostResponseMapper;
import backend.spring.sns.model.entity.Comment;
import backend.spring.sns.repository.CommentRepository;
import backend.spring.member.model.entity.Member;
import backend.spring.sns.model.entity.Post;
import backend.spring.sns.repository.PostRepository;
import backend.spring.sns.service.SnsService;
import backend.spring.member.repository.MemberRepository;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SnsServiceImpl implements SnsService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final FileService fileService;
    @Autowired
    private final PostResponseMapper postResponseMapper;
    @Autowired
    private final CommentResponseMapper commentResponseMapper;

    public SnsServiceImpl(PostRepository postRepository, CommentRepository commentRepository,
                          MemberRepository memberRepository, PostResponseMapper postResponseMapper,
                          CommentResponseMapper commentResponseMapper){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.fileService = FileServiceFactory.create(System.getProperty("spring.profiles.active"));
        this.postResponseMapper = postResponseMapper;
        this.commentResponseMapper = commentResponseMapper;
    }

    // 게시물 등록
    @Override
    public void registerPost(Long memberId, PostUploadRequest uploadParam) throws IOException {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        // 사진 경로 반환
        String photoName = UUID.randomUUID() + "_" + uploadParam.photo().getOriginalFilename();
        String photoUrl = fileService.uploadPhoto(uploadParam.photo(), photoName);

        // 게시물 저장
        Post post = Post.builder()
                .author(currentMember)
                .photoUrl(photoUrl)
                .caption(uploadParam.caption())
                .location(uploadParam.location())
                .build();
        postRepository.save(post);
    }

    // 게시물 전체 조회
    @Override
    public List<PostResponse> getAllPosts(Long memberId) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        // 게시물 전체 조회
        List<Post> posts = postRepository.findAll();

        // 게시물 응답 DTO 변환
        List<PostResponse> postResponses = postResponseMapper.toPostResponses(posts, currentMember);
        return postResponses;
    }

    // 게시물 댓글 작성
    @Override
    public void writeComment(Long memberId, Long postId, CommentWriteRequest wirteParam) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        // 게시물 조회
        Post post = findPost(postId);

        // 댓글 저장
        Comment comment = Comment.builder()
                .author(currentMember)
                .post(post)
                .message(wirteParam.message())
                .build();
        commentRepository.save(comment);
    }

    // 게시물 댓글 조회
    @Override
    public List<CommentResponse> getComments(Long postId) {
        // 게시물 조회
        Post post = findPost(postId);

        // 게시물 댓글 조회
        List<Comment> comments = commentRepository.findByPostPostId(postId);

        // 댓글 응답 DTO 변환
        List<CommentResponse> commentResponses = commentResponseMapper.toCommentResponses(comments);
        return commentResponses;
    }

    // 게시물 좋아요
    @Override
    public void likePost(Long memberId, Long postId) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        // 게시물 조회
        Post post = findPost(postId);

        // 좋아요 목록에 추가
        post.getLikeMemberSet().add(currentMember);
    }

    // 게시물 좋아요 취소
    @Override
    public void unlikePost(Long memberId, Long postId) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        // 게시물 조회
        Post post = findPost(postId);

        // 좋아요 목록에서 삭제
        post.getLikeMemberSet().remove(currentMember);
    }

    // 검색 조건으로 게시물 조회
    @Override
    public List<PostSearchResult> searchByConditions(PostSearchCondition conditionParam) {
        return postRepository.search(conditionParam);
    }

    // 게시물 반환
    private Post findPost(Long postId){
        Post findPost = postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException(POST_ID_NOT_FOUND.getMessage()));
        return findPost;
    }

    // 회원 반환
    private Member findMember(Long memberId){
        Member findMember = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_ID_NOT_FOUND.getMessage()));
        return findMember;
    }

}
