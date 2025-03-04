package song.teamo3.domain.common.exception.studyapplication.exceptions;

import song.teamo3.domain.common.exception.studyapplication.StudyApplicationException;

public class DuplicateStudyApplicationException extends StudyApplicationException {
    public DuplicateStudyApplicationException() {
        super("Duplicate Study Application Exception");
    }

    public DuplicateStudyApplicationException(String message) {
        super(message);
    }
}
