package org.example.args;

import java.util.List;
import java.util.function.Function;

class SingleValueOptionParser<T> implements OptionParser<T> {

    private T defaultValue;
    private Function<String, T> valueParser;

    public SingleValueOptionParser(T defaultValue, Function<String, T> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return defaultValue;
        }

        // 第一个 if 语句表示的是参数不足的情况，分别为：当前参数到达列表末尾（-p 的情况）；紧紧跟随另一个参数（-p -l 的情况）。
        if (index + 1 == arguments.size()
                || arguments.get(index + 1).startsWith("-")) {
            throw new InsufficientArgumentsException(option.value());
        }
        // 第二个 if 语句则表示，当前参数后至少还存在两个数值，且第二个不是另一个参数（-p 8080 8081，而不是 -p 8080 -l 的情况），那么参数给多了。
        if (index + 2 < arguments.size() &&
                !arguments.get(index + 2).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }
        return valueParser.apply(arguments.get(index + 1));
    }

}
