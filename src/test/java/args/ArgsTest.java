package args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgsTest {
    @Test
    public void initParser() {
        ArgumentParser parser = ArgumentParser.buildWithSchema("l|Boolean|False;p|Integer|0;d|String| ");

        ArgumentGroup args = parser.getDefaultArgs();
        assertEquals(Boolean.FALSE, ((BooleanArgument) args.get("l")).getValue());
        assertEquals(Integer.valueOf(0), ((IntegerArgument) args.get("p")).getValue());
        assertEquals(" ", ((StringArgument) args.get("d")).getValue());
    }

    @Test
    public void parseEmptyString() {
        String arg = "";
        ArgumentParser parser = ArgumentParser.buildWithSchema("l|Boolean|False;p|Integer|0;d|String| ");

        ArgumentGroup args = parser.parse(arg);
        assertEquals(Boolean.FALSE, ((BooleanArgument) args.get("l")).getValue());
        assertEquals(Integer.valueOf(0), ((IntegerArgument) args.get("p")).getValue());
        assertEquals(" ", ((StringArgument) args.get("d")).getValue());
    }

    @Test
    public void basicTest() {
        String arg = "-l  -p  8080  -d  /usr/logs -t -100";
        ArgumentParser parser = ArgumentParser.buildWithSchema("l|Boolean|False;p|Integer|0;d|String| ;t|Integer|-1");

        ArgumentGroup args = parser.parse(arg);
        assertEquals(Boolean.TRUE, ((BooleanArgument) args.get("l")).getValue());
        assertEquals(Integer.valueOf(8080), ((IntegerArgument) args.get("p")).getValue());
        assertEquals("/usr/logs", ((StringArgument) args.get("d")).getValue());
        assertEquals(Integer.valueOf(-100), ((IntegerArgument) args.get("t")).getValue());
    }

    @Test
    public void initWithInvalidSchema() {
        assertThrows(IllegalArgumentException.class, () -> ArgumentParser.buildWithSchema("l|Boolean|False;p|Integer|0;d|String|"));
    }

    @Test
    public void initWithInvalidFlag() {
        ArgumentParser parser = ArgumentParser.buildWithSchema("l|Boolean|False;p|Integer|0;d|String| ");

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("-e");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("-p -l");
        });
    }

    @Test
    public void initWithEmptySchema() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentParser.buildWithSchema("");
        });
    }

    @Test
    public void buildArgumentGroup() {
        ArgumentGroup args = new ArgumentGroup();

        args.put(new BooleanArgument("l", Boolean.TRUE));
        args.put(new IntegerArgument("p", 8080));
        args.put(new StringArgument("d", "/usr/logs"));

        assertTrue(args.get("l") instanceof BooleanArgument);
        assertTrue(args.get("p") instanceof IntegerArgument);
        assertTrue(args.get("d") instanceof StringArgument);

        assertEquals(3, args.getAllArgument().size());
    }

    @Test
    public void buildBooleanArgument() {
        BooleanArgument booleanArg = new BooleanArgument("l", Boolean.TRUE);

        assertEquals(Boolean.TRUE, booleanArg.getValue());
        assertEquals("l", booleanArg.getName());
        assertEquals(booleanArg, new BooleanArgument("l", Boolean.TRUE));
    }

    @Test
    public void buildIntegerArgument() {
        int value = 100;
        IntegerArgument argument = new IntegerArgument("p", value);

        assertEquals(Integer.valueOf(value), argument.getValue());
        assertEquals("p", argument.getName());
        assertEquals(argument, new IntegerArgument("p", value));
    }

    @Test
    public void buildStringArgument() {
        String name = "d";
        String value = "/usr/logs";
        StringArgument argument = new StringArgument(name, value);

        assertEquals(value, argument.getValue());
        assertEquals(name, argument.getName());
        assertEquals(argument, new StringArgument(name, value));
    }

}
