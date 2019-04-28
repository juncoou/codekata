package marsrover;


public class Position {
    public enum Direction {
        N, E, S, W;

        private static Direction[] allDirection = values();

        public Direction left() {
            return allDirection[(this.ordinal() + 3) % allDirection.length];//避免越界把-1改为+4-1
        }

        public Direction right() {
            return allDirection[(this.ordinal() + 1) % allDirection.length];
        }
    }

    private int x;
    private int y;
    private Direction direction;

    public Position(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Position(Position pos) {
        this.x = pos.x;
        this.y = pos.y;
        this.direction = pos.direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
