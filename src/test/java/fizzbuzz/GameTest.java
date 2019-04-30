package fizzbuzz;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    static final String WORD_FIZZ = "Fizz";
    static final String WORD_BUZZ = "Buzz";
    static final String WORD_FIZZBUZZ = "FizzBuzz";

    @Test
    public void testFizz() {
        Game game = new Game();
        game.start(3, 5);

        assertEquals(WORD_FIZZ, game.say(6));//第一个特殊数的倍数
        assertEquals(WORD_FIZZ, game.say(34));//含有第一个特殊数
        assertEquals(WORD_FIZZ, game.say(33));//含有第一个特殊数，并且是他的倍数
    }

    @Test
    public void testBuzz() {
        Game game = new Game();
        game.start(5, 6);

        assertEquals(WORD_BUZZ, game.say(12));//第二个特殊数的倍数
        assertEquals(WORD_BUZZ, game.say(26));//含有第二个特殊数
        assertEquals(WORD_BUZZ, game.say(36));//含有第二个特殊数，并且是他的倍数
    }

    @Test
    public void testFizzBuzz() {
        Game game = new Game();

        game.start(3, 7);
        assertEquals(WORD_FIZZBUZZ, game.say(37));//有两个特殊数，顺序
        assertEquals(WORD_FIZZBUZZ, game.say(73));//有两个特殊数，逆序
        assertEquals(WORD_FIZZBUZZ, game.say(27));//含有第二个特殊数，又是第一个特殊数的倍数

        game.start(3, 5);
        assertEquals(WORD_FIZZBUZZ, game.say(35));//有两个特殊数，只是第二个特殊数的倍数

        game.start(3, 6);
        assertEquals(WORD_FIZZBUZZ, game.say(12));//两个特殊数的倍数
        assertEquals(WORD_FIZZBUZZ, game.say(30));//两个特殊数的倍数，又含有前者
        assertEquals(WORD_FIZZBUZZ, game.say(60));//两个特殊数的倍数，又含有后者
        assertEquals(WORD_FIZZBUZZ, game.say(36));//两个特殊数的倍数，包含两者
        assertEquals(WORD_FIZZBUZZ, game.say(63));//有两个特殊数，只是第一个数的倍数

    }

    @Test
    public void testOthers() {
        Game game = new Game();
        game.start(3, 5);

        assertEquals("1", game.say(1));
        assertEquals("7", game.say(7));
        assertEquals("61", game.say(61));

        game.start(3, 6);
        assertEquals("41", game.say(41));
    }
}
