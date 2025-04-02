package song.teamo3.domain.study.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import song.teamo3.domain.comment.dto.CommentPageDto;
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
    private String bumpUpDate;
    private Long views;
    private String status;

    private List<StudyMemberListDto> studyMemberList = new ArrayList<>();

    private Page<CommentPageDto> commentPage = Page.empty();

    private Long writerId;
    private String writerName;

    private boolean isWriter;
    private boolean isMember;
    private boolean isFavorite;
    private Long favorites;

    public StudyDto(Study study, User user, List<StudyMemberListDto> studymemberList, Page<CommentPageDto> commentPage, boolean isMember, boolean isFavorite, Long favorites) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.description = study.getContent();
        this.bumpUpDate = study.getBumpUpDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
        this.views = study.getViews();
        this.status = study.getStatus().name();

        this.studyMemberList = studymemberList;

        this.commentPage = commentPage;

        this.writerName = study.getWriter().getName();

        this.isWriter = (user != null && user.getId().equals(study.getWriter().getId()));
        this.isMember = isMember;
        this.isFavorite = isFavorite;
        this.favorites = favorites;
    }

    public StudyDto(Study study, List<StudyMemberListDto> studyMemberList, Page<CommentPageDto> commentPage, Long favorites) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.description = study.getContent();
        this.bumpUpDate = study.getBumpUpDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
        this.views = study.getViews();
        this.status = study.getStatus().name();

        this.studyMemberList = studyMemberList;

        this.commentPage = commentPage;

        this.writerName = study.getWriter().getName();

        this.isWriter = false;
        this.isMember = false;
        this.isFavorite = false;
        this.favorites = favorites;
    }
}
