package backend.spring.sns.service.impl;

import backend.spring.sns.model.dto.request.CommentWriteRequest;
import backend.spring.sns.model.dto.request.PostSearchCondition;
import backend.spring.sns.model.dto.request.PostUpdateRequest;
import backend.spring.sns.model.dto.request.PostUploadRequest;
import backend.spring.sns.model.dto.response.PostSearchResult;
import backend.spring.sns.model.entity.Comment;
import backend.spring.sns.repository.CommentRepository;
import backend.spring.member.model.entity.Member;
import backend.spring.sns.model.entity.Post;
import backend.spring.sns.repository.PostRepository;
import backend.spring.sns.service.SNSService;
import backend.spring.member.repository.MemberRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@RequiredArgsConstructor
public class SNSServiceImpl implements SNSService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final MemberRepository memberRepository;

    @Value("${photo.save.dir}")
    private String uploadPath;
    @Value("${photo.url}")
    private String serverUrl;

    // 게시물 등록
    @Override
    public void registerPost(Long memberId, PostUploadRequest uploadParam) throws IOException {
        // 로그인 중인 회원 조회
        Member member = findMember(memberId);

        // 사진 경로 반환
        String photoUrl = savePhotos(uploadParam.photo());

        // 게시물 저장
        Post post = Post.builder()
                .author(member)
                .photoUrl(photoUrl)
                .caption(uploadParam.caption())
                .location(uploadParam.location())
                .build();
        postRepository.save(post);
    }

    // 사진 저장 및 경로 반환
    private String savePhotos(MultipartFile photo) throws IOException {
        // 사진 이름 생성
        String projectPath = System.getProperty("user.dir") + uploadPath;
        String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
        // 사진 파일 저장
        File saveFile = new File(projectPath, fileName);
        photo.transferTo(saveFile);
        // post 모델에 담길 경로
        String photoUrl = serverUrl + fileName;
        return photoUrl;
    }

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
        if (post.isLikeUser(member.getMemberId())) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        // 좋아요 목록에 추가
        post.getLikeUserSet().add(member);
        postRepository.save(post);
    }

    // 게시물 좋아요 취소
    @Override
    public void unlikePost(Long memberId, Long postId) {
        // 로그인 중인 회원 조회
        Member member = findMember(memberId);

        // 게시물 조회
        Post post = findPost(postId);

        // 이미 좋아요 취소한 경우
        if (!post.isLikeUser(member.getMemberId())) {
            throw new IllegalArgumentException("이미 좋아요를 취소했습니다.");
        }

        // 좋아요 목록에서 삭제
        post.getLikeUserSet().remove(member);
        postRepository.save(post);
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
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 없습니다."));
        return post;
    }

    // 회원 반환
    private Member findMember(Long memberId){
        Member member = (Member) memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));
        return member;
    }

}
