package song.teamo3.domain.common.exception.study;

import song.teamo3.domain.common.exception.TeamoException;

public class StudyException extends TeamoException {
    public StudyException() {
        super("Study Exception");
    }

    public StudyException(String message) {
        super(message);
    }
}
