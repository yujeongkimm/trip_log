package ex.travel.service;

import ex.travel.domain.Post;
import ex.travel.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    //수정
    //변경 감지 기능(Dirty Checking)
    //영속성 컨텍스트가 자동 변경
    @Transactional
    public Long updatePost(Long postId, String content, LocalDateTime localDateTime) {
        Post find = postRepository.findById(postId);
        find.setContent(content);
        find.setPostDate(localDateTime);
        return postId;
    }


}
