package com.pub.adapter.formatter;

import com.pub.adapter.formatter.centered.CenteredResultAdder;
import com.pub.adapter.formatter.common.DefaultNewLineRemover;
import com.pub.adapter.formatter.left.LeftAligner;
import com.pub.adapter.formatter.common.DefaultLineOrder;
import com.pub.adapter.formatter.left.LeftResultAdder;
import com.pub.adapter.formatter.left.LeftTokenAdder;
import com.pub.adapter.formatter.right.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class AlignFormatterTest {

    static Stream<Arguments> leftAlignArguments() {
        return Stream.of(
                Arguments.of(0, "", "", "Add no spaces to an empty input"),
                Arguments.of(1, "", "", "Again, add no spaces to an empty input"),
                Arguments.of(0, "f fo bar foobar","f\nfo\nbar\nfoobar","No spaces added"),
                Arguments.of(1, "f fo bar foobar","f\nfo\nbar\nfoobar","No spaces added"),
                Arguments.of(2, "f fo bar foobar","f \nfo\nbar\nfoobar","Space added"),
                Arguments.of(3, "f fo bar foobar","f  \nfo \nbar\nfoobar","Spaces added"),
                Arguments.of(4, "f fo bar foobar","f fo\nbar \nfoobar","Spaces added and fo is encapsulated"),
                Arguments.of(5, "f fo bar foobar","f fo \nbar  \nfoobar","Spaces added"),
                Arguments.of(6, "f fo bar foobar","f fo  \nbar   \nfoobar","Spaces added"),
                Arguments.of(7, "f fo bar foobar","f fo   \nbar    \nfoobar ","Space also added to foobar"),
                Arguments.of(8, "f fo bar foobar","f fo bar\nfoobar  ","Spaces added and bar is encapsulated"));
    }

    @ParameterizedTest
    @MethodSource("leftAlignArguments")
    public void leftAlignTest(int width, String input, String expected, String ignoredDescription) {
        AlignFormatter formatter = new AlignFormatter(
                new DefaultLineOrder(),
                new LeftAligner(),
                new LeftTokenAdder(),
                new LeftResultAdder(),
                new DefaultNewLineRemover(),
                width);
        assert expected.equals(formatter.format(input));
    }

    static Stream<Arguments> rightAlignArguments() {
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
    @MethodSource("rightAlignArguments")
    public void rightAlignTest(int width, String input, String expected, String ignoredDescription) {
        AlignFormatter formatter = new AlignFormatter(
                new ReverseLineOrder(),
                new RightAligner(),
                new RightTokenAdder(),
                new RightResultAdder(),
                new RightNewLineRemover(),
                width);
        assert expected.equals(formatter.format(input));
    }

    static Stream<Arguments> centerAlignArguments() {
        return Stream.of(
                Arguments.of(0, "", "", "Add no spaces to an empty input"),
                Arguments.of(1, "", "", "Again, add no spaces to an empty input"),
                Arguments.of(0, "f fo bar foobar","f\nfo\nbar\nfoobar","No spaces added"),
                Arguments.of(1, "f fo bar foobar","f\nfo\nbar\nfoobar","No spaces added"),
                Arguments.of(2, "f fo bar foobar","f \nfo\nbar\nfoobar","Space added"),
                Arguments.of(3, "f fo bar foobar"," f \nfo \nbar\nfoobar","Spaces added and f centered"),
                Arguments.of(4, "f fo bar foobar","f fo\nbar \nfoobar","Spaces added and fo is encapsulated"),
                Arguments.of(5, "f fo bar foobar","f fo \n bar \nfoobar","Spaces added and bar centered"),
                Arguments.of(6, "f fo bar foobar"," f fo \n bar  \nfoobar","Spaces added - f and fo also centered"),
                Arguments.of(7, "f fo bar foobar"," f fo  \n  bar  \nfoobar ","Space also added to foobar"),
                Arguments.of(8, "f fo bar foobar","f fo bar\n foobar ","Spaces added and bar is encapsulated - and foobar centered"));
    }

    @ParameterizedTest
    @MethodSource("centerAlignArguments")
    public void centerAlignTest(int width, String input, String expected, String ignoredDescription) {
        AlignFormatter formatter = new AlignFormatter(
                new DefaultLineOrder(),
                new LeftAligner(),
                new LeftTokenAdder(),
                new CenteredResultAdder(width),
                new DefaultNewLineRemover(),
                width);
        assert expected.equals(formatter.format(input));
    }
}