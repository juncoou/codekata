ArgumentParser是分析器

通过静态方法buildWithSchema创建，支持的参数通过schema进行描述
每个参数由3段构成，以"|"分隔，多个参数以";"分隔

 {参数名}|{参数类型}|{默认值};{参数名}|{参数类型}|{默认值}

支持参数类型：

Integer，整形

Boolean，布尔型，在参数中出现时为True，否则为默认值

String，字符串

使用ArgumentParser的parse方法对参数字符串进行分析，返回一个ArgumentGroup对象，可根据参数名获得对应的Argument对象实例，并通过getValue方法获得参数值

测试用例在：codekata/src/test/java/args/
