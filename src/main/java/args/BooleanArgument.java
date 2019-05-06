package args;

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

    public static class Builder extends ArgumentBuilder {
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
