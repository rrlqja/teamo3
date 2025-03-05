package song.teamo3.domain.common.exception.study.exceptions;

import song.teamo3.domain.common.exception.study.StudyException;

public class ClosedStudyException extends StudyException {
    public ClosedStudyException() {
        super("Closed Study Exception");
    }

    public ClosedStudyException(String message) {
        super(message);
    }
}
