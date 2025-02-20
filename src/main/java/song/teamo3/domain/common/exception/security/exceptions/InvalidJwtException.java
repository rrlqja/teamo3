package song.teamo3.domain.common.exception.security.exceptions;

import song.teamo3.domain.common.exception.security.SecurityException;

public class InvalidJwtException extends SecurityException {
    public InvalidJwtException() {
        super("Invalid Jwt Exception");
    }

    public InvalidJwtException(String message) {
        super(message);
    }
}
