package marsrover;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Supplier;

public class Rover {
    public enum CommandToken {L, R, M}

    public enum RoverStatus {UNLANDING, OK, RIP}

    private Area area;
    private RoverStatus status = RoverStatus.UNLANDING;
    private Position landPosition;
    private Position currPosition;

    private Queue<CommandToken> commands = new LinkedList<>();

    private Response response;


    public Rover(Area area) {
        this.area = area;
    }

    public void setCommand(String landCmd, String moveCmd) {
        parseLandingCommand(landCmd);
        parseMoveCommand(moveCmd);
    }

    public void start() {
        land();

        while ((status == RoverStatus.OK) && (commands.peek() != null)) {
            handle(commands.poll());
        }

        setResponse();
    }

    public Response getResponse() {
        return response;
    }

    private void parseLandingCommand(String commandString) {
        if (!commandString.matches("\\d{2} [NESW]")) {
            throw new IllegalArgumentException("Invalid land command : " + commandString);
        }


        int x = Integer.parseInt(commandString.substring(0, 1));
        int y = Integer.parseInt(commandString.substring(1, 2));
        Position.Direction direction = Position.Direction.valueOf(commandString.substring(3, 4));

        landPosition = new Position(x, y, direction);
    }

    private void parseMoveCommand(String commandString) {
        if (!commandString.matches("[LRM]+")) {
            throw new IllegalArgumentException("Invalid move command : " + commandString);
        }

        for (int i = 0; i < commandString.length(); i++) {
            commands.add(CommandToken.valueOf(String.valueOf(commandString.charAt(i))));
        }
    }


    private void setResponse() {
        switch (status) {
            case OK:
                response = new Response(currPosition);
                break;
            case RIP:
                response = new RIPResponse(currPosition);
                break;
            default:
                throw new IllegalStateException("Unknown Status : " + status);
        }
    }

    private void land() {
        area.land(landPosition.getX(), landPosition.getY());
        status = RoverStatus.OK;
        currPosition = new Position(landPosition);
    }


    private void handle(CommandToken command) {
        switch (command) {
            case L:
                turnLeft();
                break;
            case R:
                turnRight();
                break;
            case M:
                move();
                break;
            default:
                throw new IllegalStateException("Unknown Command : " + command);
        }
    }


    private void move() {
        if (area.hasLamp(currPosition.getX(), currPosition.getY(), currPosition.getDirection())) {//有灯，就不动
            return;
        }

        Position next = getNextPos();
        if (area.moveTo(next.getX(), next.getY())) {//成功移动
            currPosition = next;
        } else {//挂了，放灯
            status = RoverStatus.RIP;
            area.setLamp(currPosition.getX(), currPosition.getY(), currPosition.getDirection()); //在x，y，向direction移动时出问题
        }
    }

    private Position getNextPos() {
        Position next = new Position(currPosition);

        next.setX(currPosition.getX()
                + ((currPosition.getDirection() == Position.Direction.E) ? 1 : 0)
                + ((currPosition.getDirection() == Position.Direction.W) ? -1 : 0));//不判断方向，直接计算
        next.setY(currPosition.getY()
                + ((currPosition.getDirection() == Position.Direction.N) ? 1 : 0)
                + ((currPosition.getDirection() == Position.Direction.S) ? -1 : 0));

        return next;
    }

    private void turnLeft() {
        currPosition.setDirection(currPosition.getDirection().left());
    }

    private void turnRight() {
        currPosition.setDirection(currPosition.getDirection().right());
    }
}
