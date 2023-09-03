package org.example.args;

import java.util.List;
import java.util.function.Function;

class IntOptionParser implements OptionParser {

    private Function<String, Object> valueParser = Integer::valueOf;

    public IntOptionParser() {
    }

    public IntOptionParser(Function<String, Object> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }

}
