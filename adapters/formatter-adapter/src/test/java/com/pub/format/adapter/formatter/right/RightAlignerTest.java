package com.pub.format.adapter.formatter.right;

import com.pub.format.adapter.formatter.common.Aligner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RightAlignerTest {

    static Stream<Arguments> errantArguments() {
        return Stream.of(
                Arguments.of(null, 3, "Trying to insert spaces. String builder is null"),
                Arguments.of(new StringBuilder(), -1, "Trying to insert spaces. Width is less than 0"));
    }

    @ParameterizedTest
    @MethodSource("errantArguments")
    public void testErrantArguments(StringBuilder sb, int width, String expected) {
        Aligner aligner = new RightAligner();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> aligner.align(sb, width));
        assertEquals(expected, thrown.getMessage());
    }

    static Stream<Arguments> validArguments() {
        return Stream.of(
                Arguments.of("", 0, ""),
                Arguments.of("", 1, ""),
                Arguments.of("foobar", 0, "foobar"),
                Arguments.of("foobar", 1, " foobar"));
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    public void testRightAligner(String input, int width, String expected) {
        Aligner aligner = new RightAligner();
        StringBuilder sbInput = new StringBuilder(input);
        StringBuilder sbExpected = new StringBuilder(expected);
        aligner.align(sbInput, width);
        assert sbInput.compareTo(sbExpected) == 0;
    }
}