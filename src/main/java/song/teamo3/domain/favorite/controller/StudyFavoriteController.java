package song.teamo3.domain.favorite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo3.domain.favorite.dto.FavoriteDto;
import song.teamo3.domain.favorite.service.FavoriteService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class StudyFavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{postId}")
    public ResponseEntity<FavoriteDto> postFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable("postId") Long postId) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        FavoriteDto favorite = favoriteService.favorite(userDetails.getUser(), postId);

        return ResponseEntity.ok(favorite);
    }
}
