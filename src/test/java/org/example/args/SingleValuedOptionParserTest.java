package org.example.args;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.example.args.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValuedOptionParserTest {

    @Test
    public void should_not_accept_extra_argument_for_single_value_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<>(Integer::parseInt).parse(asList("-p", "8080", "8080"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    public void should_not_accept_insufficient_argument_for_single_value_option(String arguments) {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () -> {
            new SingleValueOptionParser<>(Integer::parseInt).parse(asList(arguments.split(" ")), option("p"));
        });

        assertEquals("p", e.getOption());
    }
}
