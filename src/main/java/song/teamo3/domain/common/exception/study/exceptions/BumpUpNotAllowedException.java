package song.teamo3.domain.common.exception.study.exceptions;

import song.teamo3.domain.common.exception.study.StudyException;

public class BumpUpNotAllowedException extends StudyException {
    public BumpUpNotAllowedException() {
        super("Bump Up Not Allowed Exception");
    }

    public BumpUpNotAllowedException(String message) {
        super(message);
    }
}
