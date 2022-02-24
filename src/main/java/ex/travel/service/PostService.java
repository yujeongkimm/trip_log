package ex.travel.service;

import ex.travel.domain.Post;
import ex.travel.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    //저장
    @Transactional
    public Long savePost(Post post) {
        postRepository.savePost(post);
        return post.getId();
    }

    //목록 보여줌
    public List<Post> findPosts() {
        return postRepository.findAllWithUser();
    }
}
