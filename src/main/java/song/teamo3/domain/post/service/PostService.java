package song.teamo3.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.comment.dto.CreateCommentDto;
import song.teamo3.domain.comment.service.CommentService;
import song.teamo3.domain.common.exception.post.exceptions.PostNotFoundException;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.post.repository.PostJpaRepository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepository postRepository;
    private final CommentService commentService;

    @Transactional
    public Post createComment(User user, Long postId, CreateCommentDto commentDto) {
        Post post = findPostByPostId(postId);

        Long commentId = commentService.saveComment(user, post, commentDto);

        return post;
    }

    private Post findPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

}
