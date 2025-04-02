package song.teamo3.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo3.domain.common.entity.DateEntity;
import song.teamo3.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class Post extends DateEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String title;
    @Column(length = 10000)
    private String content;
    private Long views;
    private boolean deleteFlag;

    public void delete() {
        this.deleteFlag = true;
    }

    public void increaseViews() {
        this.views++;
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }

    protected Post(User writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = 0L;
        this.deleteFlag = false;
    }
}
