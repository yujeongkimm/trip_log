package ex.travel.repository;

import ex.travel.domain.Comment;
import ex.travel.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public List<Comment> findCommentsWithPost(Post post) {
        return em.createQuery("select c from Comment c where c.post = :post", Comment.class)
                .setParameter("post", post)
                .getResultList();
    }
}
