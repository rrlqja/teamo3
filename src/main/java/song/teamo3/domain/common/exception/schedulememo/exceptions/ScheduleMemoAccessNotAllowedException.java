package song.teamo3.domain.common.exception.schedulememo.exceptions;

import song.teamo3.domain.common.exception.schedulememo.ScheduleMemoException;

public class ScheduleMemoAccessNotAllowedException extends ScheduleMemoException {
    public ScheduleMemoAccessNotAllowedException() {
        super("ScheduleMemo Access Not Allowed Exception");
    }

    public ScheduleMemoAccessNotAllowedException(String message) {
        super(message);
    }
}
