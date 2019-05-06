package args;

public abstract class ArgumentBuilder {
    protected String name;
    protected String value;

    abstract public Argument build();

    public ArgumentBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ArgumentBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    abstract public boolean needValue();
}
