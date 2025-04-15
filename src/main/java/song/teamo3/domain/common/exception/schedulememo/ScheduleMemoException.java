package song.teamo3.domain.common.exception.schedulememo;

import song.teamo3.domain.common.exception.TeamoException;

public class ScheduleMemoException extends TeamoException {
    public ScheduleMemoException() {
        super("ScheduleMemo Exception");
    }

    public ScheduleMemoException(String message) {
        super(message);
    }
}
