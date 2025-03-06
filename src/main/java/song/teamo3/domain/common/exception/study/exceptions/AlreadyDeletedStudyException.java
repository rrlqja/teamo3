package song.teamo3.domain.common.exception.study.exceptions;

import song.teamo3.domain.common.exception.study.StudyException;

public class AlreadyDeletedStudyException extends StudyException {
    public AlreadyDeletedStudyException() {
        super("Already Deleted Study Exception");
    }

    public AlreadyDeletedStudyException(String message) {
        super(message);
    }
}
