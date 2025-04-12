package com.pub.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatEnumTest {

    @Test
    public void testToEnum() {
        assert FormatEnum.toEnum("left") == FormatEnum.LEFT_ALIGNED;
        assert FormatEnum.toEnum("right") == FormatEnum.RIGHT_ALIGNED;
        assert FormatEnum.toEnum("centered") == FormatEnum.CENTERED;
    }

    @Test()
    public void testToEnumNotFound() {
        String type = "anUnknownType";
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> FormatEnum.toEnum(type));
        String expectedExceptionMessage = String.format("Unknown format type. type = %s", type);
        assertEquals(thrown.getMessage(), expectedExceptionMessage);
    }
}