package marsrover;

public class Response {
    private Position lastPosition;

    public Response(Position lastPosition) {
        this.lastPosition = lastPosition;
    }

    public Position getLastPosition() {
        return lastPosition;
    }

    @Override
    public String toString() {
        return String.format("%d%d %s", lastPosition.getX(), lastPosition.getY(), lastPosition.getDirection());
    }
}
