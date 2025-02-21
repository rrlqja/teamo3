package song.teamo3.domain.common.exception.studymember.exceptions;

import song.teamo3.domain.common.exception.studymember.StudyMemberException;

public class DuplicateStudyMemberException extends StudyMemberException {
    public DuplicateStudyMemberException() {
        super("Duplicate Study Member Exception");
    }

    public DuplicateStudyMemberException(String message) {
        super(message);
    }
}
