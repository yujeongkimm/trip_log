package ex.travel.service;

import ex.travel.domain.Comment;
import ex.travel.domain.Post;
import ex.travel.dto.CommentDto;
import ex.travel.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void save(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setPost(commentDto.getPost());
        comment.setTitle(commentDto.getTitle());
        comment.setWriter(commentDto.getWriter());
        comment.setContent(commentDto.getContent());

        commentRepository.save(comment);
    }

    public List<Comment> findCommentsWithPost(Post post) {
        return commentRepository.findCommentsWithPost(post);
    }

}
