package fizzbuzz;

public class Game {
    public static final String WORD_FIZZ = "Fizz";
    public static final String WORD_BUZZ = "Buzz";

    private int first;
    private int second;

    public boolean start(int first, int second) {
        if ((first <= 0) || (first >= 10) || (second <= 0) || (second >= 10)) {
            return false;
        }

        this.first = first;
        this.second = second;

        return true;
    }

    public String say(int number) {
        StringBuilder word = new StringBuilder();

        word.append(match(number, first, WORD_FIZZ));
        word.append(match(number, second, WORD_BUZZ));

        if (word.length() == 0) {
            word.append(number);
        }

        return word.toString();
    }

    public String match(int number, int specialNumber, String word) {
        return ((number % specialNumber == 0) || (String.valueOf(number).contains(String.valueOf(specialNumber)))) ? word : "";
    }

    public static void main(String[] args) {
        Game game = new Game();
        int first = 3;
        int second = 5;
        game.start(first, second);

        System.out.printf("(%d, %d)\n", first, second);
        for (int i = 1; i < 100; i++) {
            System.out.printf("%d => %s\n", i, game.say(i));
        }
    }
}
