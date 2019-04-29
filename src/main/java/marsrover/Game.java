package marsrover;

public class Game {

    public void start(String commandString)
    {
        if (commandString == null) {
            throw new IllegalArgumentException("command is null");
        }

        String[] lines = commandString.split("\n");
        Area mars = buildAreaWithString(lines[0]);

        for (int seq = 1; seq < lines.length; seq+=2)
        {
            Rover rover = new Rover(mars);
            rover.setCommand(lines[seq], lines[seq+1]);
            rover.start();

            System.out.println(rover.getResponse().toString());
        }
    }

    private Area buildAreaWithString(String commandString)
    {
        if (!commandString.matches("\\d{2}")){
            throw new IllegalArgumentException("Invalid map command : " + commandString);
        }

        int length = Integer.parseInt(commandString.substring(0, 1));
        int width = Integer.parseInt(commandString.substring(1, 2));

        Area map = new Area(length, width);

        return map;
    }

    public static void main(String[] args) {
        String cmd = "55\n12 N\nLMLMLMLMM\n33 E\nMMM";

        Game game = new Game();
        game.start(cmd);

    }
}