package song.teamo3.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo3.domain.comment.dto.CreateCommentDto;
import song.teamo3.domain.comment.dto.ModifyCommentDto;
import song.teamo3.domain.comment.service.CommentService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/modify/{commentId}")
    public String postModifyComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable("commentId") Long commentId,
                                    @RequestBody ModifyCommentDto commentDto,
                                    RedirectAttributes redirectAttributes) {
        Long commentStudyId = commentService.modifyComment(userDetails.getUser(), commentId, commentDto);

        redirectAttributes.addAttribute("studyId", commentStudyId);

        return "redirect:/study/{studyId}";
    }

    @PostMapping("/delete/{commentId}")
    public String postDeleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable("commentId") Long commentId,
                                    RedirectAttributes redirectAttributes) {
        Long commentStudyId = commentService.deleteComment(userDetails.getUser(), commentId);

        redirectAttributes.addAttribute("studyId", commentStudyId);

        return "redirect:/study/{studyId}";
    }
}
