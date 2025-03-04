package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studyapplication.entity.StudyApplication;
import song.teamo3.domain.user.entity.User;

@Data
public class CreateStudyApplicationDto {
    private String title;
    private String description;

    public StudyApplication toEntity(User user, Study study) {
        return StudyApplication.create(user, study, title, description);
    }
}
