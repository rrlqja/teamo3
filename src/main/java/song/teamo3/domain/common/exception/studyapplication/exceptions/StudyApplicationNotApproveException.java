package song.teamo3.domain.common.exception.studyapplication.exceptions;

import song.teamo3.domain.common.exception.studyapplication.StudyApplicationException;

public class StudyApplicationNotApproveException extends StudyApplicationException {
    public StudyApplicationNotApproveException() {
        super("Study Application Not Approve Exception");
    }

    public StudyApplicationNotApproveException(String message) {
        super(message);
    }
}
