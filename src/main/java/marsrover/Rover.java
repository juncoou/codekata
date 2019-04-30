package marsrover;

import java.util.LinkedList;
import java.util.Queue;

public class Rover {
    public enum CommandToken {L, R, M}

    public enum RoverStatus {UNLANDING, OK, RIP}

    private Mars mars;
    private RoverStatus status = RoverStatus.UNLANDING;
    private Position landPosition;
    private Position currPosition;

    private Queue<CommandToken> commands = new LinkedList<>();

    private Response response;


    public Rover(Mars mars) {
        this.mars = mars;
    }

    public void setCommand(String landCmd, String moveCmd) {
        parseLandingCommand(landCmd);
        parseMoveCommand(moveCmd);
    }

    public void start() {
        land();

        while (isHealth() && hasMoreCommand()) {
            handle(nextCommand());
        }

        setResponse();
    }

    private boolean isHealth() {
        return status == RoverStatus.OK;
    }

    private CommandToken nextCommand() {
        return commands.poll();
    }

    private boolean hasMoreCommand() {
        return commands.peek() != null;
    }

    public Response getResponse() {
        return response;
    }

    private void parseLandingCommand(String commandString) {
        if (!commandString.matches("\\d{2} [NESW]")) {
            throw new IllegalArgumentException("Invalid land command : " + commandString);
        }

        landPosition = Position.parsePosition(commandString);
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
        mars.land(landPosition.getX(), landPosition.getY());
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
        if (!mars.hasLamp(currPosition)) {//前方畅通
            Position next = currPosition.forward();
            if (mars.moveTo(next)) {//成功移动
                currPosition = next;
            } else {//挂了，放灯
                status = RoverStatus.RIP;
                mars.setLamp(currPosition); //在当前姿态向前移动时出问题
            }
        }
    }

    private void turnLeft() {
        currPosition = currPosition.leftRotation();
    }

    private void turnRight() {
        currPosition = currPosition.rightRotation();
    }
}
