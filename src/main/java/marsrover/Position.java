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

    public static Position parsePosition(String value) {
        if (!value.matches("\\d{2} [NESW]")) {
            throw new IllegalArgumentException("Invalid position value : " + value);
        }


        int x = Integer.parseInt(value.substring(0, 1));
        int y = Integer.parseInt(value.substring(1, 2));
        Direction direction = Direction.valueOf(value.substring(3, 4));

        Position position = new Position(x, y, direction);

        return position;
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
                ((direction == Direction.E) ? 1 : 0) +
                ((direction == Direction.W) ? -1 : 0);//不判断方向，直接计算
        forwardPos.y = y +
                ((direction == Direction.N) ? 1 : 0) +
                ((direction == Direction.S) ? -1 : 0);

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
