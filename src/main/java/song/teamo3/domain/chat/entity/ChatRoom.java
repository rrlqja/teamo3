package song.teamo3.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import song.teamo3.domain.study.entity.Study;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "study_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    private String chatRoomName;
    private boolean deleteFlag = false;

    @CreatedDate
    private LocalDateTime lastChatTime;

    public void delete() {
        deleteFlag = true;
    }

    public void updateLastChatTime() {
        lastChatTime = LocalDateTime.now();
    }

    public void modify(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public static ChatRoom create(Study study) {
        return new ChatRoom(study);
    }

    private ChatRoom(Study study) {
        this.study = study;
        this.chatRoomName = study.getStudyName();
    }
}
