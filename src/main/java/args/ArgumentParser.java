package args;

import java.util.stream.Stream;

public class ArgumentParser {
    final private String SEPARATE_CHAR_OPTIONS = "[;]";
    final private String SEPARATE_CHAR_OPTIONPART = "[|]";
    final private String FLAG_PREFIX = "-";

    private final int PART_FLAG = 0;
    private final int PART_TYPE = 1;
    private final int PART_DEFAULT = 2;

    private ArgumentGroup defaultArgs;

    public ArgumentParser(String schema) {
        initWithSchema(schema);
    }

    public ArgumentGroup parse(String s) {
        ArgumentGroup result = defaultArgs.clone();

        TokenSet ts = new TokenSet(s);

        while (ts.hasNext()) {
            String flag = getFlag(ts.next());

            ArgumentBuilder builder = getBuilder(fullNameOf(defaultArgs.get(flag)));
            builder.withName(flag);
            if (builder.needValue()) {
                if (!ts.hasNext()) {
                    throw new IllegalArgumentException("Invalid command line : " + s);
                }

                builder.withValue(getValue(ts.next()));
            }

            result.put(builder.build());
        }

        return result;
    }

    public ArgumentGroup getDefaultArgs() {
        return defaultArgs.clone();
    }

    private String getFlag(String word) {
        if (word.indexOf(FLAG_PREFIX) < 0) {
            throw new IllegalArgumentException("Invalid Argument flag : " + word);
        }
        return word.substring(word.indexOf(FLAG_PREFIX) + 1).trim();
    }

    private String getValue(String word) {
        return word.trim();
    }

    private ArgumentBuilder getBuilder(String ArgumentClassName) {
        try {
            Class<?> cl = Class.forName(ArgumentClassName + "$Builder");
            return (ArgumentBuilder) cl.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal ArgumentType " + ArgumentClassName);
        }
    }

    private String fullNameOf(String ArgumentType) {
        return getClass().getPackage().getName() + "." + ArgumentType + "Argument";
    }

    private String fullNameOf(Argument argument) {
        return argument.getClass().getName();
    }

    private void initWithSchema(String schema) {
        defaultArgs = new ArgumentGroup();

        Stream.of(schema.split(SEPARATE_CHAR_OPTIONS)).peek(s -> {
            String[] part = s.split(SEPARATE_CHAR_OPTIONPART);
            ArgumentBuilder builder = getBuilder(fullNameOf(part[PART_TYPE]));
            defaultArgs.put(builder.withName(part[PART_FLAG]).withValue(part[PART_DEFAULT]).build());
        }).count();
    }
}
