package com.pub.format.modules.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatEnumTest {

    static Stream<Arguments> errantArguments() {
        return Stream.of(
                Arguments.of(null, NullPointerException.class, "Format type can not be null"),
                Arguments.of("aUnknownEnumType", IllegalArgumentException.class, "Unknown format type. type = aUnknownEnumType"));
    }

    @ParameterizedTest
    @MethodSource("errantArguments")
    public void testToEnumErrantArguments(String type, Class<Exception> cls, String expectedMessage) {
        Exception thrown = assertThrows(cls, () -> FormatEnum.toEnum(type));
        assertEquals(thrown.getMessage(), expectedMessage);
    }

    @Test
    public void testToEnum() {
        assert FormatEnum.toEnum("left") == FormatEnum.LEFT_ALIGNED;
        assert FormatEnum.toEnum("right") == FormatEnum.RIGHT_ALIGNED;
        assert FormatEnum.toEnum("centered") == FormatEnum.CENTERED;
    }
}