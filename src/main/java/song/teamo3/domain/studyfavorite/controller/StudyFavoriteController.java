package song.teamo3.domain.studyfavorite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo3.domain.studyfavorite.dto.FavoriteDto;
import song.teamo3.domain.studyfavorite.service.StudyFavoriteService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/studyFavorite")
@RequiredArgsConstructor
public class StudyFavoriteController {
    private final StudyFavoriteService studyFavoriteService;

    @PostMapping("/favorite/{studyId}")
    public ResponseEntity<FavoriteDto> postFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable("studyId") Long studyId) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        FavoriteDto favorite = studyFavoriteService.favorite(userDetails.getUser(), studyId);

        return ResponseEntity.ok(favorite);
    }
}
