package backend.spring.sns.repository.impl;

import static backend.spring.sns.model.entity.QPost.post;
import static backend.spring.member.model.entity.QMember.member;
import backend.spring.sns.dto.response.QPostSearchResult;

import backend.spring.sns.dto.request.PostSearchCondition;
import backend.spring.sns.dto.response.PostSearchResult;
import backend.spring.sns.repository.PostRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostSearchResult> search(PostSearchCondition condition) {
        return queryFactory
                .select(new QPostSearchResult(
                        post.postId,
                        post.location,
                        post.caption,
                        post.createdAt,
                        member.memberId,
                        member.name,
                        member.avatarUrl))
                .from(post)
                .leftJoin(post.author, member)
                .where(memberNameEq(condition.memberName()),
                        locationEq(condition.location()),
                        captionContain(condition.caption()),
                        betweenDate(condition.startDate(), condition.endDate())
                )
                .fetch();
    }
    // 작성자 이름 일치 여부
    private BooleanExpression memberNameEq(String memberName){
        return memberName == null ? null : member.name.eq(memberName);
    }
    // 지역 일치 여부
    private BooleanExpression locationEq(String location){
        return location == null ? null : post.location.eq(location);
    }
    // 키워드 포함 여부
    private BooleanExpression captionContain(String caption){
        return caption == null ? null : post.caption.contains(caption);
    }
    // 날짜 해당 여부
    private BooleanExpression betweenDate(LocalDateTime startDate, LocalDateTime endDate){
        return post.createdAt.between(startDate, endDate);
    }

}
