package com.pub.format.adapter.formatter.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DefaultLineOrderTest {

    static Stream<Arguments> arguments() {
        return Stream.of(
            Arguments.of("", new ArrayList<>(), "The empty input"),
            Arguments.of(" ", new ArrayList<>(), "Input with space"),
            Arguments.of("foo", List.of("foo"), "One token no spaces"),
            Arguments.of("foo ", List.of("foo"), "One token, one space to the right"),
            Arguments.of(" foo", List.of("foo"), "One token, one space to the left"),
            Arguments.of(" foo ", List.of("foo"), "One token, space on both sides"),
            Arguments.of("foo bar", List.of("foo", "bar"), "Two tokes no spaces"));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void testDefaultLineOrder(String input, List<String> expected) {
        LineOrder lineOrder = new DefaultLineOrder();
        List<String> result = lineOrder.order(input);
        assert result.stream().toList().equals(expected.stream().toList());
    }
}
