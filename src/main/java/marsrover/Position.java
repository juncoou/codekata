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


    public Position forward() {
        Position forwardPos = new Position(this);

        forwardPos.x = x +
                ((direction == Position.Direction.E) ? 1 : 0) +
                ((direction == Position.Direction.W) ? -1 : 0);//不判断方向，直接计算
        forwardPos.y = y +
                ((direction == Position.Direction.N) ? 1 : 0) +
                ((direction == Position.Direction.S) ? -1 : 0);

        return forwardPos;
    }

    public Position leftRotation() {
        Position lrPos = new Position(this);

        lrPos.direction = direction.left();

        return lrPos;
    }

    public Position rightRotation() {
        Position rrPos = new Position(this);

        rrPos.direction = direction.right();

        return rrPos;
    }

}
