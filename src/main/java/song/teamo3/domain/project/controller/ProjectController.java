package song.teamo3.domain.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo3.domain.chat.dto.ChatRoomListDto;
import song.teamo3.domain.chat.service.ChatRoomService;
import song.teamo3.domain.project.dto.CreateProjectDto;
import song.teamo3.domain.project.dto.ModifyProjectDto;
import song.teamo3.domain.project.dto.ProjectDto;
import song.teamo3.domain.project.dto.ProjectPageDto;
import song.teamo3.domain.project.service.ProjectService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ChatRoomService chatRoomService;

    @GetMapping("/projectList")
    public String getProjectList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @PageableDefault(value = 10, page = 0) Pageable pageable,
                                 @RequestParam(value = "searchType", required = false) String searchType,
                                 @RequestParam(value = "searchValue", required = false) String searchValue,
                                 Model model) {
        Page<ProjectPageDto> projectPage = Page.empty();

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            projectPage = projectService.getProjectPage(pageable, searchType, searchValue);
        } else {
            projectPage = projectService.getProjectPage(pageable);
        }

        Page<Object> empty = Page.empty();

        model.addAttribute("noticeList", empty);
        if (userDetails != null) {
            Page<ChatRoomListDto> chatRoomList = chatRoomService.getChatRoomList(userDetails.getUser());
            model.addAttribute("chatRoomList", chatRoomList);
        }

        model.addAttribute("projectPage", projectPage);

        return "project/projectList";
    }

    @GetMapping("/create/{studyId}")
    public String getCreateProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable("studyId") Long studyId,
                                   Model model) {
        CreateProjectDto projectDto = projectService.getCreateProject(userDetails.getUser(), studyId);

        model.addAttribute("project", projectDto);

        return "project/createProject";
    }

    @PostMapping("/create")
    public String postCreateProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestBody CreateProjectDto projectDto,
                                    RedirectAttributes redirectAttributes) {
        Long projectId = projectService.create(userDetails.getUser(), projectDto);

        redirectAttributes.addAttribute("projectId", projectId);

        return "redirect:/project/{projectId}";
    }

    @GetMapping("/{projectId}")
    public String getProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @PathVariable("projectId") Long projectId,
                             Model model) {
        ProjectDto project = null;
        if (userDetails == null) {
            project = projectService.getProject(projectId);
        } else{
            project = projectService.getProject(userDetails.getUser(), projectId);
        }

        model.addAttribute("project", project);

        Page<Object> empty = Page.empty();

        model.addAttribute("noticeList", empty);
        if (userDetails != null) {
            Page<ChatRoomListDto> chatRoomList = chatRoomService.getChatRoomList(userDetails.getUser());
            model.addAttribute("chatRoomList", chatRoomList);
        }

        return "project/project";
    }

    @GetMapping("/modify/{projectId}")
    public String getModifyProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable("projectId") Long projectId,
                                   Model model) {
        ModifyProjectDto project = projectService.getModifyProject(userDetails.getUser(), projectId);

        model.addAttribute("project", project);

        model.addAttribute("noticeList", Page.empty());
        Page<ChatRoomListDto> chatRoomList = chatRoomService.getChatRoomList(userDetails.getUser());
        model.addAttribute("chatRoomList", chatRoomList);

        return "project/modifyProject";
    }

    @PostMapping("/modify/{projectId}")
    public String postModifyProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable("projectId") Long projectId,
                                    @RequestBody ModifyProjectDto modifyProjectDto,
                                    RedirectAttributes redirectAttributes) {
        Long modifiedProjectId = projectService.modifyProject(userDetails.getUser(), projectId, modifyProjectDto);

        redirectAttributes.addAttribute("modifiedProjectId", modifiedProjectId);

        return "redirect:/project/{projectId}";
    }

    @PostMapping("/delete/{projectId}")
    public String postDeleteProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable("projectId") Long projectId) {
        projectService.deleteProject(userDetails.getUser(), projectId);

        return "redirect:/project/projectList";
    }
}
