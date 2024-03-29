package backend.spring.sns.controller;

import backend.spring.sns.dto.request.PostSearchCondition;
import backend.spring.sns.dto.response.CommentResponse;
import backend.spring.sns.dto.response.PostSearchResult;
import backend.spring.sns.dto.response.PostResponse;
import backend.spring.sns.dto.request.CommentWriteRequest;
import backend.spring.sns.dto.request.PostUploadRequest;
import backend.spring.sns.service.SnsService;
import backend.spring.security.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class SnsController {

    @Autowired
    private final SecurityUtil securityUtil;
    @Autowired
    private final SnsService snsService;

    // 게시물 업로드
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 업로드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "리소스 찾을 수 없음.")})
    @Operation(summary = "게시물 업로드")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadPost(@Valid @ModelAttribute PostUploadRequest uploadParam) throws IOException {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 게시물 등록
        snsService.registerPost(memberId, uploadParam);
        return ResponseEntity.ok().build();
    }

    // 게시물 전체 조회
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 전체 조회 성공")})
    @Operation(summary = "게시물 전체 조회")
    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 게시물 응답 DTO 반환
        List<PostResponse> postResponses = snsService.getAllPosts(memberId);
        return ResponseEntity.ok(postResponses);
    }

    // 댓글 작성
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 댓글 작성 성공"),
            @ApiResponse(responseCode = "404", description = "리소스 찾을 수 없음.")})
    @Operation(summary = "게시물 댓글 작성")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> writeComment(
            @Parameter(name = "postId", required = true) @Valid @PathVariable Long postId,
            @Valid @RequestBody CommentWriteRequest writeParam) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 게시물 댓글 등록
        snsService.writeComment(memberId, postId, writeParam);
        return ResponseEntity.ok().build();
    }

    // 댓글 조회
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 댓글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스 찾을 수 없음.")})
    @Operation(summary = "게시물 댓글 조회")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @Parameter(name = "postId", required = true) @Valid @PathVariable Long postId) {
        // 댓글 응답 DTO 반환
        List<CommentResponse> commentResponses = snsService.getComments(postId);
        return ResponseEntity.ok(commentResponses);
    }

    // 게시물 좋아요
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 성공"),
            @ApiResponse(responseCode = "404", description = "리소스 찾을 수 없음."),
            @ApiResponse(responseCode = "409", description = "좋아요 중복 시도")})
    @Operation(summary = "게시물 좋아요")
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(
            @Parameter(name="postId", required = true) @Valid @PathVariable Long postId) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 게시물 좋아요 등록
        snsService.likePost(memberId, postId);
        return ResponseEntity.ok().build();
    }

    // 게시물 좋아요 취소
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 취소 성공"),
            @ApiResponse(responseCode = "404", description = "리소스 찾을 수 없음."),
            @ApiResponse(responseCode = "409", description = "좋아요 취소 중복 시도")})
    @Operation(summary = "게시물 좋아요 취소")
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlikePost(
            @Parameter(name = "postId", required = true) @Valid @PathVariable Long postId) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 게시물 좋아요 취소
        snsService.unlikePost(memberId, postId);
        return ResponseEntity.ok().build();
    }

    // 검색 조건으로 게시물 조회
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 검색 성공")})
    @Operation(summary = "검색 조건으로 게시물 조회")
    @PostMapping("/search")
    public ResponseEntity<List<PostSearchResult>> searchByConditions(@Valid @RequestBody PostSearchCondition searchParam){
        // 검색 정보로 게시물 조회
        List<PostSearchResult> postSearchResults = snsService.searchByConditions(searchParam);
        return ResponseEntity.ok(postSearchResults);
    }

}