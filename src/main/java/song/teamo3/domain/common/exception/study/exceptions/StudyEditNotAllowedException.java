package song.teamo3.domain.common.exception.study.exceptions;

import song.teamo3.domain.common.exception.study.StudyException;

public class StudyEditNotAllowedException extends StudyException {
    public StudyEditNotAllowedException() {
        super("Study Edit Not Allowed Exception");
    }

    public StudyEditNotAllowedException(String message) {
        super(message);
    }
}
