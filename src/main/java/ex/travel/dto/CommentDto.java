package ex.travel.dto;

import ex.travel.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private String title;
    private String writer;
    private String content;
    private Post post;
}
