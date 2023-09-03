package org.example.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {
        Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
        try {
            List<String> arguments = Arrays.asList(args);

            Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOption(arguments, it)).toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Object value = null;
        Option option = parameter.getAnnotation(Option.class);
        if (parameter.getType() == boolean.class) {
            value = parseBoolean(arguments, option);
        }
        if (parameter.getType() == int.class) {
            value = parseInt(arguments, option);
        }
        if (parameter.getType() == String.class) {
            value = parseString(arguments, option);
        }
        return value;
    }

    interface OptionParser {
        Object parse(List<String> arguments, Option option);
    }

    private static Object parseString(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return arguments.get(index + 1);
    }

    private static Object parseInt(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return Integer.parseInt(arguments.get(index + 1));
    }

    private static Object parseBoolean(List<String> arguments, Option option) {
        return arguments.contains("-" + option.value());
    }

    static class BooleanOptionParser implements OptionParser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            return parseBoolean(arguments, option);
        }
    }
}
