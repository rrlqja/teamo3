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
import song.teamo3.domain.study.entity.Study;
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

    @JoinColumn(name = "study_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    private String text;
    private boolean deleteFlag;

    public void modify(String text) {
        this.text = text;
    }

    public void delete() {
        deleteFlag = true;
    }

    public static Comment create(User user, Study study, String text) {
        return new Comment(user, study, text);
    }

    private Comment(User writer, Study study, String text) {
        this.writer = writer;
        this.study = study;
        this.text = text;
        this.deleteFlag = false;
    }
}
