package song.teamo3.domain.common.exception.studyschedule;

import song.teamo3.domain.common.exception.TeamoException;

public class StudyScheduleException extends TeamoException {
    public StudyScheduleException() {
        super("Study Schedule Exception");
    }

    public StudyScheduleException(String message) {
        super(message);
    }
}
