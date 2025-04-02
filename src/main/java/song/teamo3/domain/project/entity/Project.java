package song.teamo3.domain.project.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("PROJECT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends Post {
    @JoinColumn(name = "study_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ElementCollection(fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @CollectionTable(name = "project_image", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "imgage_url")
    private List<String> imgList = new ArrayList<>();

    private String subTitle;
    private String url;

    public void modify(String title, String description, List<String> imgList, String url) {
        super.edit(title, description);
        this.imgList = imgList;
        this.url = url;
    }

    public static Project create(User user, Study study, String title, String description, List<String> imgList, String url, String subTitle) {
        return new Project(user, study, title, description, imgList, url, subTitle);
    }

    private Project(User writer, Study study, String title, String description, List<String> imgList, String url, String subTitle) {
        super(writer, title, description);
        this.study = study;
        this.imgList = imgList;
        this.url = url;
        this.subTitle = subTitle;
    }
}
