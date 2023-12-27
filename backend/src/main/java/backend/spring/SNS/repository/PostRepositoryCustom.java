package backend.spring.SNS.repository;

import backend.spring.SNS.model.dto.request.PostSearchCondition;
import backend.spring.SNS.model.dto.response.PostSearchResult;
import java.util.List;

public interface PostRepositoryCustom {
    List<PostSearchResult> search(PostSearchCondition condition);
}
