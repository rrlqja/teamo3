package song.teamo3.domain.common.exception.project.exceptions;

import song.teamo3.domain.common.exception.project.ProjectException;

public class AlreadyDeletedProjectException extends ProjectException {
    public AlreadyDeletedProjectException() {
        super("Already Deleted Project Exception");
    }

    public AlreadyDeletedProjectException(String message) {
        super(message);
    }
}
