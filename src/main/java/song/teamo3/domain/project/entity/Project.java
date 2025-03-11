package song.teamo3.domain.project.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import song.teamo3.domain.common.entity.PostEntity;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @JoinColumn(name = "study_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_image", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "imgage_url")
    private List<String> imgList = new ArrayList<>();

    private String url;

    public static Project create(User user, Study study, String title, String description, List<String> imgList, String url) {
        return new Project(user, study, title, description, imgList, url);
    }

    private Project(User writer, Study study, String title, String description, List<String> imgList, String url) {
        super(title, description);
        this.writer = writer;
        this.study = study;
        this.imgList = imgList;
        this.url = url;
    }
}
