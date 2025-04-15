package song.teamo3.domain.common.exception.studyschedule.exceptions;

import song.teamo3.domain.common.exception.studyschedule.StudyScheduleException;

public class StudyScheduleAccessDeniedException extends StudyScheduleException {
    public StudyScheduleAccessDeniedException() {
        super("Study Schedule Access Denied Exception");
    }

    public StudyScheduleAccessDeniedException(String message) {
        super(message);
    }
}
