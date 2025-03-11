package song.teamo3.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import song.teamo3.domain.studymember.dto.StudyMemberListDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectMemberListDto {
    private Long memberId;
    private String memberName;

    public CreateProjectMemberListDto(StudyMemberListDto studyMemberListDto) {
        this.memberId = studyMemberListDto.getId();
        this.memberName = studyMemberListDto.getMemberName();
    }
}
