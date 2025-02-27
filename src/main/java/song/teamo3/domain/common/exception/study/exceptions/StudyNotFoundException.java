package song.teamo3.domain.common.exception.study.exceptions;

import song.teamo3.domain.common.exception.study.StudyException;

public class StudyNotFoundException extends StudyException {
    public StudyNotFoundException() {
        super("Study Not Found Exception");
    }

    public StudyNotFoundException(String message) {
        super(message);
    }
}
