package com.pub.adapter.formatter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class RightAlignFormatterTest {

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of(0, "", "", "Add no spaces to an empty input"),
                Arguments.of(1, "", "", "Again, add no spaces to an empty input"),
                Arguments.of(0, "f fo bar foobar","f\nfo\nbar\nfoobar","No spaces added"),
                Arguments.of(1, "f fo bar foobar","f\nfo\nbar\nfoobar","No spaces added"),
                Arguments.of(2, "f fo bar foobar"," f\nfo\nbar\nfoobar","Space added"),
                Arguments.of(3, "f fo bar foobar","  f\n fo\nbar\nfoobar","Spaces added"),
                Arguments.of(4, "f fo bar foobar","f fo\n bar\nfoobar","Spaces added and fo is encapsulated"),
                Arguments.of(5, "f fo bar foobar"," f fo\n  bar\nfoobar","Spaces added"),
                Arguments.of(6, "f fo bar foobar","     f\nfo bar\nfoobar","Spaces added"),
                Arguments.of(7, "f fo bar foobar","      f\n fo bar\n foobar","Space also added to foobar"),
                Arguments.of(8, "f fo bar foobar","f fo bar\n  foobar","Spaces added and bar is encapsulated"));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void rightAlignTest(int width, String input, String expected, String description) {
        RightAlignFormatter formatter = new RightAlignFormatter(width);
        assert expected.equals(formatter.format(input));
    }
}