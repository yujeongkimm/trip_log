package ex.travel.repository;

import ex.travel.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    //저장
    public void savePost(Post post){
        em.persist(post);
    }

    //리스트 보여줌
    public List<Post> findAllWithUser() {
        return em.createQuery("select p from Post p" +
                        " join fetch p.user u" ,Post.class)
                .getResultList();
    }

}
