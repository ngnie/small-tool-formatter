package com.pub.domain.model;

import java.util.Arrays;
import java.util.Objects;

public enum FormatEnum {
    LEFT_ALIGNED("left"),
    RIGHT_ALIGNED("right"),
    CENTERED("centered");

    final private String type;

    FormatEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    public static FormatEnum toEnum(String type) {
        Objects.requireNonNull(type, "Format type can not be null");
        return Arrays.stream(FormatEnum.values())
            .filter(x -> x.type().equalsIgnoreCase(type))
            .findFirst().orElseThrow(() ->
                new IllegalArgumentException(String.format("Unknown format type. type = %s", type)));
    }
}