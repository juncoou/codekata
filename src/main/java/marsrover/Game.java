package marsrover;

public class Game {

    public void start(String commandString)
    {
        String[] lines = getCmdLines(commandString);

        Mars mars = buildAreaWithString(lines[0]);
        for (int seq = 1; seq < lines.length; seq+=2)
        {
            runEachRover(mars, lines[seq], lines[seq+1]);
        }
    }

    private String[] getCmdLines(String commandString) {
        if (commandString == null) {
            throw new IllegalArgumentException("command is null");
        }

        String[] lines =  commandString.split("\n");

        if ((lines.length < 3) || (lines.length % 2 != 1)) {
            throw new IllegalArgumentException("Invalid command string : " +  commandString);
        }

        return lines;
    }

    private void runEachRover(Mars mars, String landCommand, String moveCommand) {
        Rover rover = new Rover(mars);
        rover.setCommand(landCommand, moveCommand);
        rover.start();

        System.out.println(rover.getResponse().toString());
    }

    private Mars buildAreaWithString(String commandString)
    {
        if (!commandString.matches("\\d{2}")){
            throw new IllegalArgumentException("Invalid map command : " + commandString);
        }

        int length = Integer.parseInt(commandString.substring(0, 1));
        int width = Integer.parseInt(commandString.substring(1, 2));

        Mars map = new Mars(length, width);

        return map;
    }

    public static void main(String[] args) {
        String cmd = "55\n12 N\nLMLMLMLMM\n33 E\nMMM";

        Game game = new Game();
        game.start(cmd);

    }
}
