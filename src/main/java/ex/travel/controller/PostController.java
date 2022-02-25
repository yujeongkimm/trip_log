package ex.travel.controller;

import ex.travel.domain.Post;
import ex.travel.domain.User;
import ex.travel.repository.PostRepository;
import ex.travel.repository.UserRepository;
import ex.travel.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    //목록 보여줌
    //+comments
    @GetMapping("/post")
    public Result findPosts(){
        List<Post> posts = postService.findPosts();
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

    //수정
    @PostMapping(value = "/post/{postId}/edit")
    public Long updatePost(@PathVariable("postId") Long postId, @RequestParam("content") String content) {
        Long findId = postService.updatePost(postId, content, LocalDateTime.now()); //수정
        Post post = postRepository.findById(postId); //조회
        return post.getId();
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
    static class Result<T> {    //제너릭 타입(외부에서 사용자에 의해 지정)
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
