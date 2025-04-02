package song.teamo3.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo3.domain.comment.dto.CreateCommentDto;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.post.service.PostService;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/{postId}/createComment")
    public String postCreateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable("postId") Long postId,
                                    @ModelAttribute CreateCommentDto commentDto,
                                    RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/user/login";
        }
        Post post = postService.createComment(userDetails.getUser(), postId, commentDto);

        redirectAttributes.addAttribute("postId", post.getId());
        if (post instanceof Study) {
            return "redirect:/study/{postId}";
        } else if (post instanceof Project) {
            return "redirect:/project/{postId}";
        } else {
            return "redirect:/";
        }
    }
}
