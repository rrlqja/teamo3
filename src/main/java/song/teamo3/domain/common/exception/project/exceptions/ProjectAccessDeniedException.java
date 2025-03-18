package song.teamo3.domain.common.exception.project.exceptions;

import song.teamo3.domain.common.exception.project.ProjectException;

public class ProjectAccessDeniedException extends ProjectException {
    public ProjectAccessDeniedException() {
        super("Project Access Denied Exception");
    }

    public ProjectAccessDeniedException(String message) {
        super(message);
    }
}
