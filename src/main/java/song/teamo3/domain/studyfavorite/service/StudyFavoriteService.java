package song.teamo3.domain.studyfavorite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.studyfavorite.dto.FavoriteDto;
import song.teamo3.domain.studyfavorite.entity.StudyFavorite;
import song.teamo3.domain.studyfavorite.repository.StudyFavoriteJpaRepository;
import song.teamo3.domain.user.entity.User;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyFavoriteService {
    private final StudyFavoriteJpaRepository studyFavoriteRepository;
    private final StudyJpaRepository studyRepository;
    private final StudyFavoriteJpaRepository studyFavoriteJpaRepository;

    @Transactional
    public FavoriteDto favorite(User user, Long studyId) {
        Optional<Study> optionalStudy = studyRepository.findStudyById(studyId);
        if (optionalStudy.isEmpty()) {
            return new FavoriteDto(null);
        }

        Study study = optionalStudy.get();
        Optional<StudyFavorite> optionalStudyFavorite = studyFavoriteRepository.findStudyFavoriteByUserAndStudy(user, study);

        StudyFavorite studyFavorite = null;
        if (optionalStudyFavorite.isPresent()) {
            studyFavorite = optionalStudyFavorite.get();
            if (studyFavorite.isDeleteFlag()) {
                studyFavorite.restore();
            } else {
                studyFavorite.delete();
            }
        } else {
            StudyFavorite createStudyFavorite = StudyFavorite.create(study, user);

            studyFavorite = studyFavoriteRepository.save(createStudyFavorite);
        }

        Long favorites = getFavorites(study);

        return new FavoriteDto(favorites, !studyFavorite.isDeleteFlag());
    }

    @Transactional
    public boolean isFavorite(User user, Study study) {
        Optional<StudyFavorite> optionalStudyFavorite = studyFavoriteRepository.findStudyFavoriteByUserAndStudy(user, study);

        if (optionalStudyFavorite.isPresent()) {
            return true;
        }

        return false;
    }

    @Transactional
    public Long getFavorites(Study study) {
        return studyFavoriteJpaRepository.findStudyFavoriteCountByStudy(study);
    }
}
