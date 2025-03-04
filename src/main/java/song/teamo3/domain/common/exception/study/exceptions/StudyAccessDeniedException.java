package song.teamo3.domain.common.exception.study.exceptions;

import song.teamo3.domain.common.exception.study.StudyException;

public class StudyAccessDeniedException extends StudyException {
    public StudyAccessDeniedException() {
        super("Study Access Denied Exception");
    }

    public StudyAccessDeniedException(String message) {
        super(message);
    }
}
