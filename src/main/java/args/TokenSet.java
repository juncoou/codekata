package args;

import java.util.stream.Stream;

public class TokenSet {
    final static public String SEPARATE_CHAR = "[ ]";

    private int currPos = 0;
    private String[] tokens;


    public TokenSet(String string) {
        tokens = Stream.of(string.split(SEPARATE_CHAR)).filter(word -> word.trim().length() > 0).toArray(String[]::new);
    }

    public boolean hasNext() {
        return currPos < tokens.length;
    }

    public String next() {
        return tokens[currPos++];
    }
}
