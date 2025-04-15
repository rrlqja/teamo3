package song.teamo3.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.chat.service.ChatRoomService;
import song.teamo3.domain.comment.dto.CommentPageDto;
import song.teamo3.domain.comment.dto.CreateCommentDto;
import song.teamo3.domain.comment.service.CommentService;
import song.teamo3.domain.common.exception.study.exceptions.BumpUpNotAllowedException;
import song.teamo3.domain.common.exception.study.exceptions.ClosedStudyException;
import song.teamo3.domain.common.exception.study.exceptions.StudyAccessDeniedException;
import song.teamo3.domain.common.exception.study.exceptions.AlreadyDeletedStudyException;
import song.teamo3.domain.common.exception.study.exceptions.StudyEditNotAllowedException;
import song.teamo3.domain.common.exception.study.exceptions.StudyNotFoundException;
import song.teamo3.domain.study.dto.BestStudyPageDto;
import song.teamo3.domain.study.dto.CreateStudyApplicationDto;
import song.teamo3.domain.study.dto.CreateStudyDto;
import song.teamo3.domain.study.dto.EditStudyDto;
import song.teamo3.domain.study.dto.StudyDto;
import song.teamo3.domain.study.dto.StudyPageDto;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.entity.StudyStatus;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.studyapplication.dto.StudyApplicationPageDto;
import song.teamo3.domain.studyapplication.service.StudyApplicationService;
import song.teamo3.domain.favorite.service.FavoriteService;
import song.teamo3.domain.studymember.dto.StudyMemberListDto;
import song.teamo3.domain.studymember.entity.StudyMemberRole;
import song.teamo3.domain.studymember.service.StudyMemberService;
import song.teamo3.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyService {
    private final ChatRoomService chatRoomService;
    private final CommentService commentService;
    private final StudyMemberService studyMemberService;
    private final StudyApplicationService studyApplicationService;
    private final StudyJpaRepository studyRepository;
    private final FavoriteService favoriteService;

    @Transactional
    public Page<StudyPageDto> getStudyPage(Pageable pageable) {
        return studyRepository.findStudyPage(pageable)
                .map(StudyPageDto::new);
    }

    @Transactional
    public Page<BestStudyPageDto> getBestStudyPage(Pageable pageable) {
        return studyRepository.findBestStudyPage(LocalDateTime.now().minusDays(7), pageable)
                .map(BestStudyPageDto::new);
    }

    @Transactional
    public Long createStudy(User user, CreateStudyDto createStudyDto) {
        Study study = createStudyDto.toEntity(user);

        Study createStudy = studyRepository.save(study);

        studyMemberService.createStudyMember(user, study, StudyMemberRole.ADMIN);

        log.info("[Create Study] id: {}", createStudy.getId());

        return createStudy.getId();
    }

    @Transactional
    public StudyDto getStudy(Long studyId) {
        Study study = findStudyById(studyId);

        checkDeleted(study);

        List<StudyMemberListDto> studyMemberList = studyMemberService.getStudyMemberList(study);
        Page<CommentPageDto> commentPage = commentService.getCommentPage(study, PageRequest.of(0, 10));

        incrementViews(study);

        Long favorites = favoriteService.getFavorites(study);

        log.info("[Get Study] id: {}", study.getId());
        return new StudyDto(study, studyMemberList, commentPage, favorites);
    }

    @Transactional
    public StudyDto getStudy(User user, Long studyId) {
        Study study = findStudyById(studyId);

        checkDeleted(study);

        List<StudyMemberListDto> studyMemberList = studyMemberService.getStudyMemberList(study);
        Page<CommentPageDto> commentPage = commentService.getCommentPage(study, PageRequest.of(0, 10));

        incrementViews(study);

        boolean isMember = studyMemberService.isMember(user, study);
        boolean isFavorite = favoriteService.isFavorite(user, study);
        Long favorites = favoriteService.getFavorites(study);

        log.info("[Get Study] id: {}", study.getId());
        return new StudyDto(study, user, studyMemberList, commentPage, isMember, isFavorite, favorites);
    }

    @Transactional
    public Long editStudy(User user, Long studyId, EditStudyDto editStudyDto) {
        Study study = findStudyById(studyId);

        if (study.getWriter().getId() != user.getId()) {
            throw new StudyEditNotAllowedException("수정할 수 없습니다.");
        }

        checkDeleted(study);

        study.edit(editStudyDto.getTitle(), editStudyDto.getDescription());
        Study editStudy = studyRepository.save(study);

        log.info("[Edit Study] id: {}", editStudy.getId());
        return editStudy.getId();
    }

    @Transactional
    public void deleteStudy(User user, Long studyId) {
        Study study = findStudyById(studyId);

        if (!study.getWriter().getId().equals(user.getId())) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        studyMemberService.delete(study);
        studyApplicationService.delete(study);
        study.delete();

        chatRoomService.delete(study);
    }

    @Transactional
    public Long applyStudy(User user, Long studyId, CreateStudyApplicationDto applicationDto) {
        Study study = findStudyById(studyId);

        if (study.getStatus() != StudyStatus.RECRUITING) {
            throw new ClosedStudyException("모집중이지 않은 스터디 입니다.");
        }
        checkDeleted(study);

        studyMemberService.checkDuplicateStudyMember(user, study);
        studyApplicationService.createStudyApplication(user, study, applicationDto);

        return study.getId();
    }

    @Transactional
    public void exitStudy(User user, Long studyId) {
        Study study = findStudyById(studyId);

        if (study.getWriter().getId().equals(user.getId())) {
            deleteStudy(user, studyId);
            return;
        }

        studyMemberService.exitStudyMember(user, study);
        chatRoomService.exitChatRoom(user, study);
    }

    @Transactional
    public List<StudyApplicationPageDto> getStudyApplicationPage(User user, Long studyId) {
        Study study = findStudyById(studyId);

        if (study.getWriter().getId() != user.getId()) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        return studyApplicationService.getPendingStudyApplicationPage(study);
    }

    @Transactional
    public Long changeStatus(User user, Long studyId) {
        Study study = findStudyById(studyId);

        if (!study.getWriter().getId().equals(user.getId())) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        StudyStatus studyStatus = study.changeStatus();
        log.info("[Change Study Status] id: {}, status: {}", study.getId(), studyStatus.name());
        return study.getId();
    }

    @Transactional
    public Long bumpUp(User user, Long studyId) {
        Study study = findStudyById(studyId);

        if (!study.getWriter().getId().equals(user.getId())) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        if (!study.getBumpUpDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new BumpUpNotAllowedException("끌어올릴 수 없습니다.");
        }

        study.bumpUp();

        return study.getId();
    }

    @Transactional
    public Long createComment(User user, Long studyId, CreateCommentDto commentDto) {
        Study study = findStudyById(studyId);

        Long commentId = commentService.saveComment(user, study, commentDto);

        return study.getId();
    }

    @Transactional
    public List<StudyMemberListDto> getCreateProjectMember(User user, Long studyId) {
        Study study = findStudyById(studyId);

        if (!study.getWriter().getId().equals(user.getId())) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        return studyMemberService.getStudyMemberList(study);
    }

    private Study findStudyById(Long studyId) {
        return studyRepository.findStudyById(studyId)
                .orElseThrow(StudyNotFoundException::new);
    }

    private void incrementViews(Study study) {
        study.increaseViews();
    }

    private void checkDeleted(Study study) {
        if (study.isDeleteFlag()) {
            throw new AlreadyDeletedStudyException("삭제된 스터디 입니다.");
        }
    }

    @Transactional
    public void createChatRoom(User user, Long studyId) {
        Study study = findStudyById(studyId);

        if (!study.getWriter().getId().equals(user.getId())) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        Long chatRoomId = chatRoomService.createChatRoom(study);
    }

    @Transactional
    public Page<StudyPageDto> getStudyByUser(User user, Pageable pageable) {
        return studyMemberService.getStudyPageByUser(user, pageable)
                .map(StudyPageDto::new);
    }
}
