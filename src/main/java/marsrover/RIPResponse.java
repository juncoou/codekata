package marsrover;

public class RIPResponse extends Response {
    public RIPResponse(Position lastPosition) {
        super(lastPosition);
    }


    @Override
    public String toString() {
        Position last = getLastPosition();
        return String.format("%d %d %s RIP", last.getX(), last.getY(), last.getDirection());
    }
}
