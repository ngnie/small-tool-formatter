package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.ResultAdder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RightResultAdderTest {

    static Stream<Arguments> errantArguments() {
        return Stream.of(
                Arguments.of(null, new StringBuilder(), "Trying to prepend alignment to result. Alignment is null"),
                Arguments.of(new StringBuilder(), null, "Trying to prepend alignment to result. Result is null"));
    }

    @ParameterizedTest
    @MethodSource("errantArguments")
    public void testErrantArguments(StringBuilder alignment, StringBuilder result, String expected) {
        ResultAdder adder = new RightResultAdder();
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> adder.add(alignment, result));
        assertEquals(expected, thrown.getMessage());
    }

    static Stream<Arguments> validArguments() {
        return Stream.of(
                Arguments.of("", "", "", "Alignment is empty. Do nothing."),
                Arguments.of("foo", "", "\nfoo", "Alignment not empty. Prepend to empty result"),
                Arguments.of("foo", "\nbar", "\nfoo\nbar", "Alignment and result not empty. Prepend to result"));
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    public void testRightResultAdder(String alignment, String result, String expected, String ignoredDescription) {
        StringBuilder sbAlignment = new StringBuilder(alignment);
        StringBuilder sbResult = new StringBuilder(result);
        ResultAdder adder = new RightResultAdder();
        adder.add(sbAlignment, sbResult);
        assert sbResult.compareTo(new StringBuilder(expected)) == 0;
    }
}