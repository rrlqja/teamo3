package song.teamo3.domain.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo3.domain.common.entity.DateEntity;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends DateEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String text;
    private boolean deleteFlag;

    public void modify(String text) {
        this.text = text;
    }

    public void delete() {
        deleteFlag = true;
    }

    public static Comment create(User user, Post post, String text) {
        return new Comment(user, post, text);
    }

    private Comment(User writer, Post post, String text) {
        this.writer = writer;
        this.post = post;
        this.text = text;
        this.deleteFlag = false;
    }
}
