package args;

import java.util.Objects;

public class StringArgument extends Argument {
    private final String value;

    public StringArgument(String flag, String value) {
        super(flag);
        this.value = value;
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringArgument argument = (StringArgument) o;
        return value.equals(argument.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    static public class Builder extends ArgumentBuilder {

        @Override
        public Argument build() {
            return new StringArgument(name, value);
        }

        @Override
        public boolean needValue() {
            return true;
        }


    }
}
