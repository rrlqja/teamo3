package song.teamo3.domain.common.exception.project;

import song.teamo3.domain.common.exception.TeamoException;

public class ProjectException extends TeamoException {
    public ProjectException() {
        super("Project Exception");
    }

    public ProjectException(String message) {
        super(message);
    }
}
