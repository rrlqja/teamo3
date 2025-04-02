package song.teamo3.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.comment.dto.CommentPageDto;
import song.teamo3.domain.comment.dto.CreateCommentDto;
import song.teamo3.domain.comment.dto.ModifyCommentDto;
import song.teamo3.domain.comment.entity.Comment;
import song.teamo3.domain.comment.repository.CommentJpaRepository;
import song.teamo3.domain.common.exception.comment.exceptions.AlreadyDeletedCommentException;
import song.teamo3.domain.common.exception.comment.exceptions.CommentAccessDeniedException;
import song.teamo3.domain.common.exception.comment.exceptions.CommentNotFoundException;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentJpaRepository commentRepository;

    @Transactional
    public Long saveComment(User user, Post post, CreateCommentDto commentDto) {
        Comment comment = commentDto.toEntity(user, post);

        Comment saveComment = commentRepository.save(comment);

        log.info("[Save Comment] id: {}", saveComment.getId());
        return saveComment.getId();
    }

    @Transactional
    public Page<CommentPageDto> getCommentPage(Post post, Pageable pageable) {
        return commentRepository.findCommentsByStudy(post, pageable)
                .map(CommentPageDto::new);
    }

    @Transactional
    public Long modifyComment(User user, Long commentId, ModifyCommentDto commentDto) {
        Comment comment = findCommentById(commentId);

        if (!comment.getWriter().getId().equals(user.getId())) {
            throw new CommentAccessDeniedException("권한이 없습니다.");
        }

        if (comment.isDeleteFlag()) {
            throw new AlreadyDeletedCommentException("이미 삭제된 댓글 입니다.");
        }

        comment.modify(commentDto.getText());

        log.info("[Modify Comment] id: {}", commentId);
        return comment.getPost().getId();
    }

    @Transactional
    public Long deleteComment(User user, Long commentId) {
        Comment comment = findCommentById(commentId);

        if (!comment.getWriter().getId().equals(user.getId())) {
            throw new CommentAccessDeniedException("권한이 없습니다.");
        }

        log.info("[Delete Comment] id: {}", commentId);
        comment.delete();

        return comment.getPost().getId();
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findCommentById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
