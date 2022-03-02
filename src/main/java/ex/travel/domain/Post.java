package ex.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    private String content;

    private LocalDateTime postDate;

    //연관관계 편의 메서드
    public void setUser(User user){
        user.getPosts().add(this);
        this.user = user;
    }

    //삭제하기
    //@OneToMany 양방향 연관관계 끊기
    public void deletePost(Post post) {
        this.user.getPosts().remove(post);
        post.setUser(null);
    }

    //--비즈니스 로직--//
}
