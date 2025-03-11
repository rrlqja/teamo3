package song.teamo3.domain.common.exception.project.exceptions;

import song.teamo3.domain.common.exception.project.ProjectException;

public class ProjectNotFoundException extends ProjectException {
    public ProjectNotFoundException() {
        super("Project Not Found Exception");
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
