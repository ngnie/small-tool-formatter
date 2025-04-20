package com.pub.format.adapter.formatter.right;

import com.pub.format.adapter.formatter.common.LineOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReverseLineOrderTest {

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of("", new ArrayList<>(), "The empty input"),
                Arguments.of(" ", new ArrayList<>(), "Input with space"),
                Arguments.of("foo", List.of("foo"), "One token no spaces"),
                Arguments.of("foo ", List.of("foo"), "One token, one space to the right"),
                Arguments.of(" foo", List.of("foo"), "One token, one space to the left"),
                Arguments.of(" foo ", List.of("foo"), "One token, space on both sides"),
                Arguments.of("foo bar", List.of("bar", "foo"), "Two tokens no spaces"));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void testReverseLineOrder(String input, List<String> expected) {
        LineOrder lineOrder = new ReverseLineOrder();
        List<String> result = lineOrder.order(input);
        assert result.stream().toList().equals(expected.stream().toList());
    }
}