package com.pub.format.adapter.formatter.right;

import com.pub.format.adapter.formatter.common.NewLineRemover;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RightNewLineRemoverTest {

    @Test
    public void testNullInput() {
        NewLineRemover remover = new RightNewLineRemover();
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> remover.remove(null));
        assertEquals("Trying to remove newline. String builder is null.", thrown.getMessage());
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of("", "", "The empty input"),
                Arguments.of("foobar", "foobar", "Nothing to remove"),
                Arguments.of("foobar\n", "foobar\n", "Nothing to remove"),
                Arguments.of("\nfoobar", "foobar", "Remove newline at the beginning of line"));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void testRightNewLineRemover(String input, String expected) {
        StringBuilder sbInput = new StringBuilder(input);
        StringBuilder sbExpected = new StringBuilder(expected);
        NewLineRemover remover = new RightNewLineRemover();
        remover.remove(sbInput);
        assert sbInput.compareTo(sbExpected) == 0;
    }
}