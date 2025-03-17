package song.teamo3.domain.common.exception.project.exceptions;

import song.teamo3.domain.common.exception.project.ProjectException;

public class ProjectModifyNotAllowedException extends ProjectException {
    public ProjectModifyNotAllowedException() {
        super("Project Modify Not Allowed Exception");
    }

    public ProjectModifyNotAllowedException(String message) {
        super(message);
    }
}
