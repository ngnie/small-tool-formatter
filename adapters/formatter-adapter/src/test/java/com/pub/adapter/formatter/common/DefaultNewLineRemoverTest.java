package com.pub.adapter.formatter.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultNewLineRemoverTest {

    @Test
    public void testNullInput() {
        NewLineRemover remover = new DefaultNewLineRemover();
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> remover.remove(null));
        assertEquals("Trying to remove newline. String builder is null", thrown.getMessage());
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
            Arguments.of("", "", "The empty input"),
            Arguments.of("foobar", "foobar", "The empty input"),
            Arguments.of("\nfoobar", "\nfoobar", "The empty input"),
            Arguments.of("foobar\n", "foobar", "The empty input"));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void testDefaultNewLineRemover(String input, String expected) {
        StringBuilder sbInput = new StringBuilder(input);
        StringBuilder sbExpected = new StringBuilder(expected);
        NewLineRemover remover = new DefaultNewLineRemover();
        remover.remove(sbInput);
        assert sbInput.compareTo(sbExpected) == 0;
    }
}