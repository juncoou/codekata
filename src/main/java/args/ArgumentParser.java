package args;

import java.util.stream.Stream;

public class ArgumentParser {
    final static private String SEPARATE_CHAR_OPTIONS = "[;]";
    final static private String SEPARATE_CHAR_OPTIONPART = "[|]";
    final static private String FLAG_PREFIX = "-";

    final static private int PART_FLAG = 0;
    final static private int PART_TYPE = 1;
    final static private int PART_DEFAULTVALUE = 2;
    final static private int OPTION_PARTS = 3;

    private ArgumentGroup defaultArgs;

    private ArgumentParser() {
        defaultArgs = new ArgumentGroup();
    }

    /**
     * 根据传入的Schema来初始化分析器
     *
     * @param schema 每个参数由3段构成，以"|"分隔，多个参数以";"分隔 {参数名}|{参数类型}|{默认值}
     */
    static public ArgumentParser buildWithSchema(String schema) {
        if (schema.trim().length() == 0) {
            throw new IllegalArgumentException("Illegal schema : " + schema);
        }

        ArgumentParser result = new ArgumentParser();

        Stream.of(schema.split(SEPARATE_CHAR_OPTIONS)).filter(w -> w.trim().length() > 0).forEach(s -> {
            String[] part = s.split(SEPARATE_CHAR_OPTIONPART);

            if (part.length != OPTION_PARTS) {
                throw new IllegalArgumentException("Illegal Argument schema : " + s);
            }

            ArgumentBuilder builder = ArgumentBuilderFactory.getBuilder(part[PART_TYPE]);
            result.defaultArgs.put(builder.withName(part[PART_FLAG]).withValue(part[PART_DEFAULTVALUE]).build());
        });

        return result;
    }


    public ArgumentGroup parse(String argumentString) {
        ArgumentGroup result = defaultArgs.clone();

        TokenSet ts = new TokenSet(argumentString);

        while (ts.hasNext()) {
            result.put(buildArgument(ts, argumentString));
        }

        return result;
    }

    private Argument buildArgument(TokenSet ts, String argumentString) {
        String argumentName = getName(ts.next());

        ArgumentBuilder builder = getBuilder(defaultArgs.get(argumentName));
        builder.withName(argumentName);
        if (builder.needValue()) {
            assertHasValue(ts.hasNext(), argumentString);

            builder.withValue(getValue(ts.next()));
        }

        return builder.build();
    }

    private void assertHasValue(boolean hasNext, String argumentString) {
        if (!hasNext) {
            throw new IllegalArgumentException("Invalid argument string : " + argumentString);
        }
    }

    public ArgumentGroup getDefaultArguments() {
        return defaultArgs.clone();
    }

    private String getName(String word) {
        if (word.indexOf(FLAG_PREFIX) < 0) {
            throw new IllegalArgumentException("Illegal Argument flag : " + word);
        }
        return word.substring(word.indexOf(FLAG_PREFIX) + 1).trim();
    }

    private String getValue(String word) {
        return word.trim();
    }

    private ArgumentBuilder getBuilder(Argument argument) {
        return ArgumentBuilderFactory.getBuilder(argument.getClass());
    }

}
