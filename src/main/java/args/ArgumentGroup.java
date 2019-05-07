package args;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class ArgumentGroup {
    private HashMap<String, Argument> args = new HashMap<>();

    public void put(Argument argument) {
        args.put(argument.getName(), argument);
    }

    public Argument get(String name) {
        if (!args.containsKey(name)) {
            throw new IllegalArgumentException("Illegal argument flag " + name);
        }

        return args.get(name);
    }

    public Collection<Argument> getAllArgument() {
        return Collections.unmodifiableCollection(args.values());
    }

    @Override
    public ArgumentGroup clone() {
        ArgumentGroup result = new ArgumentGroup();
        result.args = (HashMap<String, Argument>) args.clone();

        return result;
    }
}
