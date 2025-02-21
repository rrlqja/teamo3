package song.teamo3.domain.common.exception.studymember;

import song.teamo3.domain.common.exception.TeamoException;

public class StudyMemberException extends TeamoException {
    public StudyMemberException() {
        super("Study Member Exception");
    }

    public StudyMemberException(String message) {
        super(message);
    }
}
