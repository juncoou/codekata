package marsrover;

import java.util.HashMap;
import java.util.HashSet;

public class Area {

    private final int length;
    private final int width;

    private HashSet<String> lamps = new HashSet<>();//Key就是[X][Y][方向]拼接起来

    public Area(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void land(int x, int y) {
        if ((x < 0) || (y < 0) || (x > length) || (y > width)) {
            throw new IllegalArgumentException(String.format("Invalid land position (%d, %d)", x, y));
        }
    }

    /**
     * 检查(x, y)的边沿是否有信号灯，(1, 1, E)表示检查在(1,1)的东边是否有信号灯
     */
    public boolean hasLamp(int x, int y, Position.Direction direction) {
        return lamps.contains(genLampCode(x, y, direction));
    }

    /**
     * 在(x, y)位置的边缘放置信号灯，(1, 1, E)表示在(1,1)的东边放置信号灯
     */
    public void setLamp(int x, int y, Position.Direction direction) {
        lamps.add(genLampCode(x, y, direction));
    }

    private String genLampCode(int x, int y, Position.Direction direction) {
        return String.format("%d%d%s", x, y, direction);
    }

    public boolean moveTo(int x, int y) {
        return (x >= 0) && (x <= length) && (y >= 0) && (y <= width);
    }
}
