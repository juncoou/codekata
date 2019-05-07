package args;

import java.util.Objects;

public class BooleanArgument extends Argument {
    private Boolean value;

    public BooleanArgument(String flag, Boolean value) {
        super(flag);
        this.value = value;
    }


    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanArgument that = (BooleanArgument) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    static public class Builder extends ArgumentBuilder {
        private Boolean value = Boolean.TRUE;

        @Override
        public Argument build() {
            return new BooleanArgument(name, value);
        }

        @Override
        public ArgumentBuilder withValue(String value) {
            this.value = Boolean.valueOf(value);
            return this;
        }

        @Override
        public boolean needValue() {
            return false;
        }
    }
}
