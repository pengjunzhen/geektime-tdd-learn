package org.example.args;

import org.example.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanOptionParserTest {

    // sad path
    @Test
    public void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> OptionParsers.bool()
                        .parse(asList("-l", "t"), option("l")));
        assertEquals("l", e.getOption());
    }

    // default path
    @Test
    public void should_set_default_value_to_false_if_option_not_present() {
        assertFalse(OptionParsers.bool().parse(asList(), option("l")));
    }

    // happy path
    @Test
    public void should_set_default_value_to_true_if_option_present() {
        assertTrue(OptionParsers.bool().parse(asList("-l"), option("l")));
    }

    static Option option(String value) {
        return new Option() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}
