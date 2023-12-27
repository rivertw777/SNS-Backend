package backend.spring.SNS.repository.impl;

import static backend.spring.SNS.model.entity.QPost.post;
import static backend.spring.member.model.entity.QMember.member;

import backend.spring.SNS.model.dto.request.PostSearchCondition;
import backend.spring.SNS.model.dto.response.PostSearchResult;
import backend.spring.SNS.model.dto.response.QPostSearchResult;
import backend.spring.SNS.repository.PostRepositoryCustom;
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

    private BooleanExpression memberNameEq(String memberName){
        return memberName == null ? null : member.name.eq(memberName);
    }

    private BooleanExpression locationEq(String location){
        return location == null ? null : post.location.eq(location);
    }

    private BooleanExpression captionContain(String caption){
        return caption == null ? null : post.caption.contains(caption);
    }

    private BooleanExpression betweenDate(LocalDateTime startDate, LocalDateTime endDate){
        return post.createdAt.between(startDate, endDate);
    }

}
