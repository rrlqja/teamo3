package song.teamo3.domain.studymember.dto;

import lombok.Data;
import song.teamo3.domain.studymember.entity.StudyMember;

@Data
public class StudyMemberListDto {
    private Long id;
    private String username;

    public StudyMemberListDto(StudyMember studyMember) {
        this.id = studyMember.getId();
        this.username = studyMember.getUser().getUsername();
    }
}
