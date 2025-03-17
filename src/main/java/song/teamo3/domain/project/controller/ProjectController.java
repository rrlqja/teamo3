package song.teamo3.domain.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo3.domain.project.dto.CreateProjectDto;
import song.teamo3.domain.project.dto.ModifyProjectDto;
import song.teamo3.domain.project.dto.ProjectDto;
import song.teamo3.domain.project.service.ProjectService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

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
        ProjectDto project = projectService.getProject(projectId);

        model.addAttribute("project", project);

        return "project/project";
    }

    @GetMapping("/modify/{projectId}")
    public String getModifyProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable("projectId") Long projectId,
                                   Model model) {
        ModifyProjectDto project = projectService.getModifyProject(userDetails.getUser(), projectId);

        model.addAttribute("project", project);

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
}
