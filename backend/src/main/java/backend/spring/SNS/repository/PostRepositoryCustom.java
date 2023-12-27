package backend.spring.SNS.repository;

import backend.spring.SNS.model.dto.request.PostSearchCondition;
import backend.spring.SNS.model.entity.Post;
import java.util.List;

public interface PostRepositoryCustom {
    List<Post> search(PostSearchCondition condition);
}
