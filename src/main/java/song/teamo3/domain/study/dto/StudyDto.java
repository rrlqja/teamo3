package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studymember.dto.StudyMemberListDto;
import song.teamo3.domain.user.entity.User;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class StudyDto {
    private Long id;
    private String title;
    private String description;
    private String createdDate;
    private Long views;
    private String status;

    private List<StudyMemberListDto> studyMemberList = new ArrayList<>();

    private Long writerId;
    private String writerName;

    private boolean isWriter;
    private boolean isMember;

    public StudyDto(Study study, User user, List<StudyMemberListDto> studymemberList, boolean isMember) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.description = study.getDescription();
        this.createdDate = study.getCreateDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
        this.views = study.getViews();
        this.status = study.getStatus().name();

        this.studyMemberList = studymemberList;

        this.writerName = study.getWriter().getUsername();

        this.isWriter = (user != null && user.getId().equals(study.getWriter().getId()));
        this.isMember = isMember;
    }

    public StudyDto(Study study, List<StudyMemberListDto> studyMemberList) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.description = study.getDescription();
        this.createdDate = study.getCreateDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
        this.views = study.getViews();
        this.status = study.getStatus().name();

        this.studyMemberList = studyMemberList;

        this.writerName = study.getWriter().getUsername();

        this.isWriter = false;
        this.isMember = false;
    }
}
