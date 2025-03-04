package song.teamo3.domain.common.exception.studyapplication.exceptions;

import song.teamo3.domain.common.exception.studyapplication.StudyApplicationException;

public class StudyApplicationNotFoundException extends StudyApplicationException {
    public StudyApplicationNotFoundException() {
        super("Study Application Not Found Exception");
    }

    public StudyApplicationNotFoundException(String message) {
        super(message);
    }
}
