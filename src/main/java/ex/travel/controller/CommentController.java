package ex.travel.controller;

import ex.travel.domain.Comment;
import ex.travel.domain.Post;
import ex.travel.dto.CommentDto;
import ex.travel.repository.PostRepository;
import ex.travel.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final PostRepository postRepository;
    private final CommentService commentService;

    @PutMapping("post/{postId}/comment")
    public CommentDto createCommentWithPost(@PathVariable("postId") Long postId, @RequestBody CommentDto commentDto) {
        Post post = postRepository.findById(postId);
        commentDto.setPost(post);
        commentService.save(commentDto);
        return commentDto;
    }

    @GetMapping("post/{postId}/comment")
    public List<Comment> getPostComments(@PathVariable("postId") Long postId) {
        Post post = postRepository.findById(postId);
        return commentService.findCommentsWithPost(post);
    }

}
