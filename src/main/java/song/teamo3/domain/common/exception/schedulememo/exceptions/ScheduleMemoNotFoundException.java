package song.teamo3.domain.common.exception.schedulememo.exceptions;

import song.teamo3.domain.common.exception.schedulememo.ScheduleMemoException;

public class ScheduleMemoNotFoundException extends ScheduleMemoException {
    public ScheduleMemoNotFoundException() {
        super("ScheduleMemo Not Found Exception");
    }

    public ScheduleMemoNotFoundException(String message) {
        super(message);
    }
}
