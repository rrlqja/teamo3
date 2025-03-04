package song.teamo3.domain.common.exception.studyapplication;

import song.teamo3.domain.common.exception.TeamoException;

public class StudyApplicationException extends TeamoException {
    public StudyApplicationException() {
        super("Study Application Exception");
    }

    public StudyApplicationException(String message) {
        super(message);
    }
}
