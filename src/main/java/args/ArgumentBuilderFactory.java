package args;


public class ArgumentBuilderFactory {
    /**
     * 参数构造类都遵循规范，是参数类的内部类，并且命名为Builder
     */
    static private ArgumentBuilder getBuilderImpl(String ArgumentClassName) {
        try {
            Class<?> cl = Class.forName(ArgumentClassName + "$Builder");
            return (ArgumentBuilder) cl.newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Illegal ArgumentType " + ArgumentClassName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static public ArgumentBuilder getBuilder(Class<? extends Argument> argumentType) {
        return getBuilderImpl(argumentType.getName());
    }

    /**
     * 参数类型都遵循命名规范{shortName}Argument，XXX是在Schema中的数据类型 假设所有的参数类都放在当前包下，更理想是遍历Classpath
     *
     * @param shortName Schema中的数据类型
     */
    static public ArgumentBuilder getBuilder(String shortName) {
        return getBuilderImpl((new ArgumentBuilderFactory()).getClass().getPackage().getName() + "." + shortName + "Argument");
    }


}
