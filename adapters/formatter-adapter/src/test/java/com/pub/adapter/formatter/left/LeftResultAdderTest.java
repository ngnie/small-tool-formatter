package com.pub.adapter.formatter.left;

import com.pub.adapter.formatter.common.ResultAdder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LeftResultAdderTest {

    static Stream<Arguments> errantArguments() {
        return Stream.of(
                Arguments.of(null, new StringBuilder(), "Trying to append alignment to result. Alignment is null"),
                Arguments.of(new StringBuilder(), null, "Trying to append alignment to result. Result is null"));
    }

    @ParameterizedTest
    @MethodSource("errantArguments")
    public void testErrantArguments(StringBuilder alignment, StringBuilder result, String expected) {
        ResultAdder adder = new LeftResultAdder();
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> adder.add(alignment, result));
        assertEquals(expected, thrown.getMessage());
    }

    static Stream<Arguments> validArguments() {
        return Stream.of(
                Arguments.of("", "", "", "Alignment is empty. Do nothing."),
                Arguments.of("foo", "", "foo\n", "Alignment not empty. Append to result"),
                Arguments.of("foo", "bar\n", "bar\nfoo\n", "Alignment and result not empty. Append to result"));
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    public void testLeftResultAdder(String alignment, String result, String expected, String ignoredDescription) {
        StringBuilder sbAlignment = new StringBuilder(alignment);
        StringBuilder sbResult = new StringBuilder(result);
        ResultAdder adder = new LeftResultAdder();
        adder.add(sbAlignment, sbResult);
        assert sbResult.compareTo(new StringBuilder(expected)) == 0;
    }
}