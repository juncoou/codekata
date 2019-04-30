package marsrover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MarsRoverTest {
    Mars mars;
    Rover rover;

    @BeforeEach
    public void init() {
        mars = new Mars(5, 5);
        rover = new Rover(mars);
    }

    @Test
    @DisplayName("移动到区域外，坠毁")
    public void testRIP() {
        rover.setCommand("33 E", "MMM");
        rover.start();

        assertEquals("5 3 E RIP", rover.getResponse().toString());
    }


    @Test
    @DisplayName("移动到负值边界，坠毁")
    public void testNegPosition() {
        rover.setCommand("10 W", "MMM");
        rover.start();

        assertEquals("0 0 W RIP", rover.getResponse().toString());
    }

    @Test
    @DisplayName("遇到前车放置的灯，安全")
    public void testGuardByAnotherCar() {
        rover.setCommand("33 E", "MMM");
        rover.start();

        assertEquals("5 3 E RIP", rover.getResponse().toString());

        Rover second = new Rover(mars);
        second.setCommand("33 E", "MMMMR");
        second.start();

        assertAll("second rover fail",
                () -> {
                    assertFalse(second.getResponse() instanceof RIPResponse);
                },
                () -> {
                    assertEquals("53 S", second.getResponse().toString());
                });


    }

    @Test
    @DisplayName("验证所有正常指令")
    public void testHappyPath() {
        rover.setCommand("12 N", "LMLRLMLMLMM");
        rover.start();

        assertEquals("13 N", rover.getResponse().toString());
    }

    @Test
    @DisplayName("成功降落")
    public void landSuccessful() {
        rover.setCommand("33 E", "L");
        rover.start();

        assertEquals("33 N", rover.getResponse().toString());
    }

    @Test
    @DisplayName("降落位置不合法")
    public void landFail() {
        rover.setCommand("73 E", "L");
        assertThrows(IllegalArgumentException.class, () -> {
            rover.start();
        });

    }

    @Test
    @DisplayName("输入非法降落指令")
    public void testInvalidLandCommand() {
        assertThrows(IllegalArgumentException.class, () -> {
            rover.setCommand("1024 E", "L");
        });
    }

    @Test
    @DisplayName("输入非法移动指令")
    public void testInvalidMoveCommand() {
        assertThrows(IllegalArgumentException.class, () -> {
            rover.setCommand("10 E", "MLRU");
        });
    }
}
