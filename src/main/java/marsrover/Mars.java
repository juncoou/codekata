package marsrover;

import java.util.HashMap;
import java.util.HashSet;

public class Mars {

    public static final int MIN_LENGTH = 0;
    public static final int MIN_WIDTH = 0;
    private final int length;
    private final int width;

    private HashSet<String> lamps = new HashSet<>();//Key就是[X][Y][方向]拼接起来

    public Mars(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void land(int x, int y) {
        if (isOutOfRange(x, y)) {
            throw new IllegalArgumentException(String.format("Invalid land position (%d, %d)", x, y));
        }
    }

    /**
     * 检查(x, y)的边沿是否有信号灯，(1, 1, E)表示检查在(1,1)的东边是否有信号灯
     */
    public boolean hasLamp(Position position) {
        return lamps.contains(genLampCode(position));
    }

    /**
     * 在(x, y)位置的边缘放置信号灯，(1, 1, E)表示在(1,1)的东边放置信号灯
     */
    public void setLamp(Position position) {
        lamps.add(genLampCode(position));
    }

    private String genLampCode(Position position) {
        return String.format("%d%d%s", position.getX(), position.getY(), position.getDirection());
    }

    public boolean moveTo(Position position) {
        return !isOutOfRange(position.getX(), position.getY());
    }

    private boolean isOutOfRange(int x, int y) {
        return ((x < MIN_LENGTH) || (y < MIN_WIDTH) || (x > length) || (y > width));
    }
}
