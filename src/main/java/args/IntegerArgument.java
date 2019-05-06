package args;

public class IntegerArgument extends Argument {

    private Integer value;

    public IntegerArgument(String flag, Integer value) {
        super(flag);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static IntegerArgument valueOf(String flag, String value) {
        return new IntegerArgument(flag, Integer.valueOf(value));
    }

    public static class Builder extends ArgumentBuilder {
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
