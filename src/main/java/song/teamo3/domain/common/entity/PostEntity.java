package song.teamo3.domain.common.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class PostEntity extends DateEntity {
    private String title;
    private String description;
    private Long views;

    public PostEntity(String title, String description) {
        this.title = title;
        this.description = description;
        this.views = 0L;
    }

    public void editPost(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void incrementViews() {
        this.views++;
    }
}
