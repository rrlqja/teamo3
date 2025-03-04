package song.teamo3.domain.common.exception.studymember.exceptions;

import song.teamo3.domain.common.exception.studymember.StudyMemberException;

public class StudyMemberNotFoundException extends StudyMemberException {
    public StudyMemberNotFoundException() {
        super("Study Member Not Found Exception");
    }

    public StudyMemberNotFoundException(String message) {
        super(message);
    }
}
