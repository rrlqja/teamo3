package song.teamo3.domain.project.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import song.teamo3.domain.comment.dto.CommentPageDto;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.project.entity.ProjectMember;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String writerName;
    private String title;
    private String subTitle;
    private String description;
    private String url;
    private String createDate;
    private List<String> imgList = new ArrayList<>();
    private List<String> memberNameList = new ArrayList<>();

    private Page<CommentPageDto> commentPage = Page.empty();
    private Long commentCount;
    private Long favoriteCount;

    private boolean isWriter = false;

    public ProjectDto(Project project, List<ProjectMember> projectMemberList, Page<CommentPageDto> commentPage, Long favoriteCount) {
        this.id = project.getId();
        this.writerName = project.getWriter().getName();
        this.title = project.getTitle();
        this.subTitle = project.getSubTitle();
        this.description = project.getContent();
        this.url = project.getUrl();
        this.createDate = project.getCreateDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.imgList = project.getImgList();
        this.memberNameList = projectMemberList.stream().map(pm -> pm.getUser().getName()).toList();
        this.commentPage = commentPage;
        this.commentCount = commentPage.getTotalElements();
        this.favoriteCount = favoriteCount;
    }

    public ProjectDto(Project project, List<ProjectMember> projectMemberList, Page<CommentPageDto> commentPage, boolean isWriter, Long favoriteCount) {
        this.id = project.getId();
        this.writerName = project.getWriter().getName();
        this.title = project.getTitle();
        this.subTitle = project.getSubTitle();
        this.description = project.getContent();
        this.url = project.getUrl();
        this.createDate = project.getCreateDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.imgList = project.getImgList();
        this.memberNameList = projectMemberList.stream().map(pm -> pm.getUser().getName()).toList();
        this.commentPage = commentPage;
        this.commentCount = commentPage.getTotalElements();
        this.favoriteCount = favoriteCount;
        this.isWriter = isWriter;
    }
}
