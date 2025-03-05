package song.teamo3.domain.study.entity;

public enum StudyStatus {
    RECRUITING, CLOSED;

    public StudyStatus changeStatus() {
        return this == RECRUITING ? CLOSED : RECRUITING;
    }
}
