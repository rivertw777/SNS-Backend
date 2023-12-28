package backend.spring.sns.repository;

import backend.spring.sns.model.dto.request.PostSearchCondition;
import backend.spring.sns.model.dto.response.PostSearchResult;
import java.util.List;

public interface PostRepositoryCustom {
    List<PostSearchResult> search(PostSearchCondition condition);
}
