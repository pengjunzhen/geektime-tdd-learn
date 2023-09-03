package org.example.args;

import java.util.List;
import java.util.function.Function;

class IntOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected Object parseValue(String value) {
        Function<String, Integer> valueParser = Integer::valueOf;
        return valueParser.apply(value);
    }
}
