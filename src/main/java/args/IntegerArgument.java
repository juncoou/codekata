package args;

import java.util.Objects;

public class IntegerArgument extends Argument {
    private Integer value;

    public IntegerArgument(String name, Integer value) {
        super(name);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerArgument that = (IntegerArgument) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    static public class Builder extends ArgumentBuilder {
        private Integer value = 0;

        @Override
        public Argument build() {
            return new IntegerArgument(name, Integer.valueOf(value));
        }

        @Override
        public ArgumentBuilder withValue(String value) {
            this.value = Integer.parseInt(value);
            return this;
        }

        @Override
        public boolean needValue() {
            return true;
        }

    }
}
