package org.example.args;

import org.example.args.exceptions.InsufficientArgumentsException;
import org.example.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.example.args.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValuedOptionParserTest {

    @Test
    public void should_not_accept_extra_argument_for_single_value_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<Integer>(0, Integer::parseInt).parse(asList("-p", "8080", "8080"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    // sad path
    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    public void should_not_accept_insufficient_argument_for_single_value_option(String arguments) {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
            new SingleValueOptionParser<Integer>(0, Integer::parseInt).parse(asList(arguments.split(" ")), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @Test
    public void should_not_accept_extra_argument_for_string_value_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<>(0, String::valueOf).parse(asList("-d", "/usr/logs", "/usr/vars"), option("d"));
        });

        assertEquals("d", e.getOption());
    }

    // default path
    @Test
    public void should_set_default_value_for_single_value_option() {
        Function<String, Object> whatever = (it) -> null;
        Object defaultValue = new Object();

        assertSame(defaultValue, new SingleValueOptionParser<>(defaultValue, whatever).parse(asList(), option("p")));
    }

    // happy path
    @Test
    public void should_parse_value_if_flag_present() {
        Object parsed = new Object();
        Function<String, Object> parse = (it) -> parsed;
        Object whatever = new Object();

        assertSame(parsed, new SingleValueOptionParser<>(whatever, parse).parse(asList("-p", "8080"), option("p")));
    }
}
