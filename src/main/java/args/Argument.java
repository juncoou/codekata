package args;

abstract public class Argument {
    private String name;

    public Argument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract public Object getValue();
}
