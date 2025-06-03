package ir.ac.kntu;

import java.time.Instant;

public class TimeFilter {

    private boolean isFilter;
    private Instant start;
    private Instant end;

    public TimeFilter(boolean isFilter, Instant start, Instant end) {
        this.isFilter = isFilter;
        this.start = start;
        this.end = end;
    }

    public boolean isFilter() {
        return isFilter;
    }

    public void setFilter(boolean isFilter) {
        this.isFilter = isFilter;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

}