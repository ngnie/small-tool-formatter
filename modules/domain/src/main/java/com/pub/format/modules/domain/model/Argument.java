package com.pub.format.modules.domain.model;

import java.util.Objects;
import java.util.Optional;

public class Argument {
    final private FormatEnum formatEnum;
    final private Integer width;
    final private boolean help;

    public Argument(FormatEnum formatEnum, Integer width) {
        Objects.requireNonNull(formatEnum, "FormatEnum is null");
        Objects.requireNonNull(width, "Width is null");
        this.formatEnum = formatEnum;
        this.width = width;
        this.help = false;
    }

    public Argument(boolean help) {
        this.formatEnum = null;
        this.width = null;
        this.help = help;
    }

    public Optional<FormatEnum> getFormatEnum() {
        return Objects.nonNull(formatEnum) ? Optional.of(formatEnum) : Optional.empty();
    }

    public Optional<Integer> getWidth() {
        return Objects.nonNull(width) ? Optional.of(width) : Optional.empty();
    }

    public boolean getHelp() {
        return help;
    }
}