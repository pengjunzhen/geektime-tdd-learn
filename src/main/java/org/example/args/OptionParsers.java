package org.example.args;

import org.example.args.exceptions.IllegalValueException;
import org.example.args.exceptions.InsufficientArgumentsException;
import org.example.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

class OptionParsers {

    public static <T> OptionParser<Boolean> bool() {
        return (arguments, option) -> values(arguments, option, 0)
                .map(it -> true)
                .orElse(false);
    }

    public static <T> OptionParser<T> unary(T defaultValue, Function<String, T> valueParser) {
        return (arguments, option) -> values(arguments, option, 1).map(it -> parseValue(option, it.get(0), valueParser)).orElse(defaultValue);
    }

    public static <T> OptionParser<T[]> list(IntFunction<T[]> generator, Function<String, T> valueParser) {
        return (arguments, option) -> values(arguments, option)
                .map(it ->
                        it.stream()
                                .map(value ->
                                        parseValue(option, value, valueParser))
                                .toArray(generator))
                .orElse(generator.apply(0));
    }


    private static Optional<List<String>> values(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return Optional.ofNullable(index == -1 ? null : values(arguments, index));
    }

    private static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return Optional.empty();
        }
        List<String> values = values(arguments, index);

        return Optional.of(values).map(it -> {
            checkSize(option, expectedSize, it);
            return it;
        });
    }

    private static void checkSize(Option option, int expectedSize, List<String> values) {
        if (values.size() < expectedSize) {
            throw new InsufficientArgumentsException(option.value());
        }

        if (values.size() > expectedSize) {
            throw new TooManyArgumentsException(option.value());
        }
    }

    private static <T> T parseValue(Option option, String value, Function<String, T> valueParser1) {
        try {
            return valueParser1.apply(value);
        } catch (Exception e) {
            throw new IllegalValueException(option.value(), value);
        }
    }

    private static List<String> values(List<String> arguments, int index) {
        int followingFlag = IntStream.range(index + 1, arguments.size())
                .filter(it -> arguments.get(it).matches("^-[a-zA-Z]+$"))
                .findFirst()
                .orElse(arguments.size());

        return arguments.subList(index + 1, followingFlag);
    }

}
