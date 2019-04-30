package marsrover;

import org.junit.Assert;
import org.junit.Test;

public class MarsRoverTest {

    @Test
    public void testRIP() {
        Mars mars = new Mars(5, 5);

        Rover rover = new Rover(mars);
        rover.setCommand("33 E", "MMM");

        rover.start();

        Response resp = rover.getResponse();

        Assert.assertEquals("5 3 E RIP", resp.toString());
    }


    @Test
    public void testNegPosition() {
        Mars mars = new Mars(5, 5);

        Rover rover = new Rover(mars);
        rover.setCommand("10 W", "MMM");

        rover.start();

        Response resp = rover.getResponse();

        Assert.assertEquals("0 0 W RIP", resp.toString());
    }

    @Test
    public void testGuardByAnotherCar() {
        Mars mars = new Mars(5, 5);

        Rover first = new Rover(mars);
        first.setCommand("33 E", "MMM");
        first.start();
        Response resp = first.getResponse();

        Assert.assertEquals("5 3 E RIP", resp.toString());

        Rover second = new Rover(mars);
        second.setCommand("33 E", "MMMMR");
        second.start();
        resp = second.getResponse();

        Assert.assertFalse(resp instanceof RIPResponse);
        Assert.assertEquals("53 S", resp.toString());
    }

    @Test
    public void testHappyPath() {
        Mars mars = new Mars(5, 5);

        Rover rover = new Rover(mars);
        rover.setCommand("12 N", "LMLMLMLMM");

        rover.start();

        Response resp = rover.getResponse();

        Assert.assertEquals("13 N", resp.toString());
    }

    @Test
    public void landSuccessful() {
        Mars mars = new Mars(5, 3);

        Rover rover = new Rover(mars);
        rover.setCommand("33 E", "L");

        rover.start();
        Response resp = rover.getResponse();

        Assert.assertEquals("33 N", resp.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void landFail() {
        Mars mars = new Mars(5, 3);

        Rover rover = new Rover(mars);
        rover.setCommand("73 E", "L");

        rover.start();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidLandCommand() {
        Mars mars = new Mars(5, 3);

        Rover rover = new Rover(mars);
        rover.setCommand("1024 E", "L");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveCommand() {
        Mars mars = new Mars(5, 3);

        Rover rover = new Rover(mars);
        rover.setCommand("10 E", "MLRU");
    }
}
