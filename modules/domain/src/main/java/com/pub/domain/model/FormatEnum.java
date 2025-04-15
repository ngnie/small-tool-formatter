package com.pub.domain.model;

import java.util.Arrays;
import java.util.Objects;

public enum FormatEnum {
    LEFT_ALIGNED(0, "left", "Left aligned"),
    RIGHT_ALIGNED(1, "right", "Left aligned"),
    CENTERED(2, "centered", "Centered");

    final private int id;
    final private String type;
    final private String description;

    FormatEnum(int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public int id() {
        return id;
    }

    public String type() {
        return type;
    }

    public String description() {
        return description;
    }

    public static FormatEnum toEnum(String type) {
        Objects.requireNonNull(type, "Format type can not be null");
        return Arrays.stream(FormatEnum.values())
            .filter(x -> x.type().equalsIgnoreCase(type))
            .findFirst().orElseThrow(() ->
                new IllegalArgumentException(String.format("Unknown format type. type = %s", type)));
    }
}