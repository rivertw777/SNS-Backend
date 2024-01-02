package backend.spring.sns.repository;

import backend.spring.sns.dto.request.PostSearchCondition;
import backend.spring.sns.dto.response.PostSearchResult;
import java.util.List;

public interface PostRepositoryCustom {
    List<PostSearchResult> search(PostSearchCondition condition);
}
