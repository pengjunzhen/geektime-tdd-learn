package org.example.args;

import org.example.args.exceptions.InsufficientArgumentsException;
import org.example.args.exceptions.TooManyArgumentsException;

import java.util.List;

class BooleanOptionParser<T> implements OptionParser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return false;
        }

        List<String> values = SingleValueOptionParser.values(arguments, index);

        if (values.size() < 0) {
            throw new InsufficientArgumentsException(option.value());
        }
        if (values.size() > 0) {
            throw new TooManyArgumentsException(option.value());
        }
        return true;
    }
}
