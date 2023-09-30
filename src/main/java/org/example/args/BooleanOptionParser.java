package org.example.args;

import java.util.List;

class BooleanOptionParser<T> implements OptionParser<Boolean> {

    private BooleanOptionParser() {
    }

    public static <T> OptionParser<Boolean> createBooleanOptionParser() {
        return (arguments, option) -> SingleValueOptionParser
                .values(arguments, option, 0)
                .map(it -> true)
                .orElse(false);
    }

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return SingleValueOptionParser
                .values(arguments, option, 0)
                .map(it -> true)
                .orElse(false);
    }
}
