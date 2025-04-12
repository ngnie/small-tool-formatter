package com.pub.adapter.argument;

import com.pub.domain.model.Argument;
import com.pub.domain.model.FormatEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Note
 * The SuppressWarnings below is only used to suppress the "description unused" warnings.
 * The description is sent as argument and can be seen in the test overview.
 */
@SuppressWarnings(value = {"unused"})
public class ArgumentParserTest {

    static Stream<Arguments> helpModeArguments() {
        return Stream.of(
            Arguments.of(new String[]{"--help"}, "Called with --help argument"),
            Arguments.of(new String[]{}, "Called without arguments"));
    }

    @ParameterizedTest
    @MethodSource("helpModeArguments")
    public void testHelpMode(String[] args, String description) {
        ArgumentParser parser = new ArgumentParser();
        Argument argument = parser.parse(args);
        assert argument.getHelp();
        assert argument.getFormatEnum() == null;
        assert argument.getWidth() == null;
    }

    static Stream<Arguments> parseModeArguments() {
        return Stream.of(
            Arguments.of(FormatEnum.LEFT_ALIGNED, "10", "Called with \"--type left\" and \"--width 10\""),
            Arguments.of(FormatEnum.RIGHT_ALIGNED, "10", "Called with \"--type right\" and \"--width 10\""),
            Arguments.of(FormatEnum.CENTERED, "10", "Called with \"--type centered\" and \"--width 10\""));
    }

    @ParameterizedTest
    @MethodSource("parseModeArguments")
    public void testParseMode(FormatEnum formatEnum, String width, String description) {
        ArgumentParser parser = new ArgumentParser();
        String[] args = {"--type", formatEnum.type(), "--width", width};
        Argument argument = parser.parse(args);
        assert argument.getFormatEnum() == formatEnum;
        assert argument.getWidth().compareTo(Integer.valueOf(width)) == 0;
        assert ! argument.getHelp();
    }


    static Stream<Arguments> parseModeKeyNotFoundArguments() {
        return Stream.of(
            Arguments.of("--ty", "--width", "FormatEnum is null", "Called with wrong \"--type\" key"),
            Arguments.of("--type", "--wi", "Width is null", "Called with wrong \"--width\" key"));
    }

    @ParameterizedTest
    @MethodSource("parseModeKeyNotFoundArguments")
    public void testParseModeKeysNotFound(String typeKey, String widthKey, String expectedMessage, String description) {
        ArgumentParser parser = new ArgumentParser();
        FormatEnum someFormatEnum = FormatEnum.LEFT_ALIGNED;
        String someWidth = "10";
        String[] args = {typeKey, someFormatEnum.type(), widthKey, someWidth};
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> parser.parse(args));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    public void testParseModeUnknownFormatType() {
        ArgumentParser parser = new ArgumentParser();
        FormatEnum someFormatEnum = FormatEnum.LEFT_ALIGNED;
        String someWidth = "10";
        String anUnknownFormatType = "foobar";
        String[] args = {"--type", anUnknownFormatType, "--width", someWidth};
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(args));
        String expectedMessage = String.format("Unknown format type. type = %s", anUnknownFormatType);
        assertEquals(thrown.getMessage(), expectedMessage);
    }

    @Test
    public void testParseModeNonNumericWidth() {
        ArgumentParser parser = new ArgumentParser();
        FormatEnum someFormatEnum = FormatEnum.LEFT_ALIGNED;
        String aNonConvertibleWidth = "foobar";
        String[] args = {"--type", someFormatEnum.type(), "--width", aNonConvertibleWidth};
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(args));
        String expectedMessage = String.format("Integer conversion error. width = %s", aNonConvertibleWidth);
        assertEquals(thrown.getMessage(), expectedMessage);
    }

    static Stream<Arguments> wrongNumberOfArguments() {
        return Stream.of(
            Arguments.of(new String[]{"--type", "--width"}, "Called without \"type\" and \"width\" value"),
            Arguments.of(new String[]{"--type", "--width", "10"}, "Called only with \"width\" value"),
            Arguments.of(new String[]{"--type", "left", "--width"}, "Called only with \"type\" value"),
            Arguments.of(new String[]{"--type", "left", "--width", "10", "--help"}, "Called with \"--help\" also"));
    }

    @ParameterizedTest
    @MethodSource("wrongNumberOfArguments")
    public void testWrongNumberOfArguments(String[] args, String description) {
        ArgumentParser parser = new ArgumentParser();
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(args));
        String expectedMessage = String.format("Wrong number of arguments. Use \"--help\". arguments = %s",
                Arrays.stream(args).toList());
        assertEquals(thrown.getMessage(), expectedMessage);
    }
}