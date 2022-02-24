package ex.travel.controller;

import ex.travel.domain.Post;
import ex.travel.domain.User;
import ex.travel.repository.UserRepository;
import ex.travel.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {
    // User연관관계 가져오기
    // Comment 연관관계 가져오기

    private final PostService postService;
    private final UserRepository userRepository;

    //목록 보여줌
    //+comments
    @GetMapping("/post")
    public Result findPosts(){
        List<Post> posts = postService.findPosts();
        System.out.println(posts);
        List<PostDtoResponse> collect = posts.stream()
                .map(p -> new PostDtoResponse(p.getId(), p.getUser(), p.getContent(), p.getPostDate()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    //저장
    //+Comments
    @PostMapping("/post/new")
    public Long savePost(@RequestBody @Valid PostDtoRequest postDtoRequest) {
        Post post = new Post();
        User user = userRepository.findByName(postDtoRequest.getName());
        post.setUser(user);

        post.setContent(postDtoRequest.getContent());
        post.setPostDate(LocalDateTime.now());

        Long id = postService.savePost(post);
        return id;

    }


    @Data
    @AllArgsConstructor
    static class PostDtoRequest{
        private String content;
        private String name;
        private LocalDateTime localDateTime;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class PostDtoResponse{
        private Long id;
        private User user;
        private String content;
        private LocalDateTime localDateTime;
    }
}
