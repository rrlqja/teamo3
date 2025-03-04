package song.teamo3.domain.common.exception.studyapplication.exceptions;

import song.teamo3.domain.common.exception.studyapplication.StudyApplicationException;

public class StudyApplicationAccessDeniedException extends StudyApplicationException {
    public StudyApplicationAccessDeniedException() {
        super("Study Application Access Denied Exception");
    }

    public StudyApplicationAccessDeniedException(String message) {
        super(message);
    }
}
