package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.TokenAdder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RightTokenAdderTest {

    static Stream<Arguments> errantArguments() {
        return Stream.of(
                Arguments.of(null, "some token", "Trying to prepend token to result. sb is null."),
                Arguments.of(new StringBuilder(), null, "Trying to prepend token to result. token is null."));
    }

    @ParameterizedTest
    @MethodSource("errantArguments")
    public void testErrantArguments(StringBuilder sb, String token, String expected) {
        TokenAdder adder = new RightTokenAdder();
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> adder.add(sb, token));
        assertEquals(expected, thrown.getMessage());
    }

    static Stream<Arguments> validArguments() {
        return Stream.of(
                Arguments.of("", "", "", "Do nothing"),
                Arguments.of("", "foo", "foo", "Prepend foo to empty string"),
                Arguments.of("bar", "foo", "foobar", "Prepend foo to bar"));
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    public void testRightTokenAdder(String result, String token, String expected, String description) {
        StringBuilder sbResult = new StringBuilder(result);
        TokenAdder adder = new RightTokenAdder();
        adder.add(sbResult, token);
        assert sbResult.compareTo(new StringBuilder(expected)) == 0;
    }
}