package song.teamo3.domain.study.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import song.teamo3.domain.common.entity.PostEntity;
import song.teamo3.domain.studyfavorite.entity.StudyFavorite;
import song.teamo3.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyFavorite> studyFavoriteList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StudyStatus status;

    private boolean deleteFlag;
    @CreatedDate
    private LocalDateTime bumpUpDate;

    public StudyStatus changeStatus() {
        this.status = this.status.changeStatus();

        return this.status;
    }

    public void delete() {
        this.deleteFlag = true;
    }

    public void bumpUp() {
        bumpUpDate = LocalDateTime.now();
    }

    public static Study create(User writer, String title, String description) {
        return new Study(writer, title, description, StudyStatus.RECRUITING);
    }

    private Study(User writer, String title, String description, StudyStatus status) {
        super(title, description);
        this.writer = writer;
        this.status = status;
        this.deleteFlag = false;
    }
}
