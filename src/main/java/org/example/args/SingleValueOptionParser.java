package org.example.args;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

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

        List<String> values = values(arguments, index);

        if (values.size() < 1) {
            throw new InsufficientArgumentsException(option.value());
        }

        if (values.size() > 1) {
            throw new TooManyArgumentsException(option.value());
        }
        String value = values.get(0);
        return valueParser.apply(value);
    }

    static List<String> values(List<String> arguments, int index) {
        int followingFlag = IntStream.range(index + 1, arguments.size())
                .filter(it -> arguments.get(it).startsWith("-"))
                .findFirst()
                .orElse(arguments.size());

        return arguments.subList(index + 1, followingFlag);
    }

}
