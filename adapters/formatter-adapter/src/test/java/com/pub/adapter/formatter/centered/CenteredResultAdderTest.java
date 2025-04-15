package com.pub.adapter.formatter.centered;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class CenteredResultAdderTest {

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of(0, "", "", "", ""),
                Arguments.of(1, "b","","b\n", "No centering"),
                Arguments.of(2, "b ","","b \n", "No centering"),
                Arguments.of(3, "b  ",""," b \n", "Centered"),
                Arguments.of(4, "b   ",""," b  \n", "Centered with adjustment"),
                Arguments.of(2, "ba","","ba\n", "No centering"),
                Arguments.of(3, "ba ","","ba \n", "No centering"),
                Arguments.of(4, "ba  ",""," ba \n", "Centered"),
                Arguments.of(5, "ba   ",""," ba  \n", "Centered with adjustment"),
                Arguments.of(6, "bar   "," foo  \n"," foo  \n bar  \n", "An example with previous alignment in result"));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void centerAlignTest(int width, String alignment, String result, String expected, String ignoredDescription) {
        CenteredResultAdder adder = new CenteredResultAdder(width);
        StringBuilder sbResult = new StringBuilder(result);
        adder.add(new StringBuilder(alignment), sbResult);
        assert new StringBuilder(expected).compareTo(sbResult) == 0;
    }
}