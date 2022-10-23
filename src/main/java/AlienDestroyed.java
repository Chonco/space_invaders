import java.awt.*;
import java.util.Objects;

public class AlienDestroyed {
    private final Point point;
    private final int iteration;

    public AlienDestroyed(Point point, int iteration) {
        this.point = point;
        this.iteration = iteration;
    }

    public Point getPoint() {
        return point;
    }

    public int getIteration() {
        return iteration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlienDestroyed that = (AlienDestroyed) o;
        return iteration == that.iteration && Objects.equals(point, that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, iteration);
    }
}
