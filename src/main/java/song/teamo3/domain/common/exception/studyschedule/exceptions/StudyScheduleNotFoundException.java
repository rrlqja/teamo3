package song.teamo3.domain.common.exception.studyschedule.exceptions;

import song.teamo3.domain.common.exception.studyschedule.StudyScheduleException;

public class StudyScheduleNotFoundException extends StudyScheduleException {
    public StudyScheduleNotFoundException() {
        super("Study Schedule Not Found Exception");
    }

    public StudyScheduleNotFoundException(String message) {
        super(message);
    }
}
