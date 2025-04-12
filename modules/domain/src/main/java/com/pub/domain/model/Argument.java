package com.pub.domain.model;

import java.util.Optional;

public class Argument {
    final private FormatEnum formatEnum;
    final private Integer width;
    final private Boolean help;

    public Argument(FormatEnum formatEnum, Integer width) {
        this.formatEnum = formatEnum;
        this.width = width;
        this.help = false;
    }

    public Argument(Boolean help) {
        this.formatEnum = null;
        this.width = null;
        this.help = help;
    }

    public FormatEnum getFormatEnum() {
        return formatEnum;
    }

    public Integer getWidth() {
        return width;
    }

    public Boolean getHelp() {
        return help;
    }
}