package song.teamo3.domain.studymember.dto;

import lombok.Data;
import song.teamo3.domain.studymember.entity.StudyMember;

import java.time.format.DateTimeFormatter;

@Data
public class StudyMemberPageDto {
    private Long id;

    private Long studyId;
    private String studyTitle;
    private String studyDescription;

    private String studyWriterName;
    private String studyCreateDate;

    public StudyMemberPageDto(StudyMember studyMember) {
        this.id = studyMember.getId();

        this.studyId = studyMember.getStudy().getId();
        this.studyTitle = studyMember.getStudy().getTitle();
        this.studyDescription = studyMember.getStudy().getDescription();

        this.studyWriterName = studyMember.getStudy().getWriter().getName();
        this.studyCreateDate = studyMember.getStudy().getCreateDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
    }
}
