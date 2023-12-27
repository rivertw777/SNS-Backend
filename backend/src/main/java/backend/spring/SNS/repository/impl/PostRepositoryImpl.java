package backend.spring.SNS.repository.impl;

import backend.spring.SNS.model.dto.request.PostSearchCondition;
import backend.spring.SNS.model.entity.Post;
import backend.spring.SNS.repository.PostRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<Post> search(PostSearchCondition condition) {
        return null;
    }
}
