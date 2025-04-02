package song.teamo3.domain.favorite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.post.repository.PostJpaRepository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.favorite.dto.FavoriteDto;
import song.teamo3.domain.favorite.entity.Favorite;
import song.teamo3.domain.favorite.repository.FavoriteJpaRepository;
import song.teamo3.domain.user.entity.User;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteJpaRepository favoriteRepository;
    private final PostJpaRepository postRepository;

    @Transactional
    public FavoriteDto favorite(User user, Long postId) {
        Optional<Post> optionalStudy = postRepository.findPostById((postId));
        if (optionalStudy.isEmpty()) {
            return new FavoriteDto(null);
        }

        Post post = optionalStudy.get();
        Optional<Favorite> optionalStudyFavorite = favoriteRepository.findFavoriteByUserAndPost(user, post);

        Favorite favorite = null;
        if (optionalStudyFavorite.isPresent()) {
            favorite = optionalStudyFavorite.get();
            if (favorite.isDeleteFlag()) {
                favorite.restore();
            } else {
                favorite.delete();
            }
        } else {
            Favorite createFavorite = Favorite.create(post, user);

            favorite = favoriteRepository.save(createFavorite);
        }

        Long favorites = getFavorites(post);

        return new FavoriteDto(favorites, !favorite.isDeleteFlag());
    }

    @Transactional
    public boolean isFavorite(User user, Study study) {
        Optional<Favorite> optionalStudyFavorite = favoriteRepository.findFavoriteByUserAndPost(user, study);

        if (optionalStudyFavorite.isPresent()) {
            return true;
        }

        return false;
    }

    @Transactional
    public Long getFavorites(Post post) {
        return favoriteRepository.findFavoriteCountByPost(post);
    }
}
