package com.pub.adapter.argument;

import com.pub.domain.model.Argument;
import com.pub.domain.model.FormatEnum;
import com.pub.domain.port.Parser;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class ArgumentParser implements Parser {
    final private static int HELP_MODE_ARG_NUMBER = 1;
    final private static int PARSE_MODE_ARG_NUMBER = 4;

    public Argument parse(String[] args) {
        FormatEnum formatEnum = null;
        Integer width = null;

        if (validateHelp(args)) {
            return new Argument(true);
        } else if (validateParseMode(args)) {
            Iterator<String> itr = Arrays.stream(args).iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                if (key.equalsIgnoreCase("--type")) {
                    formatEnum = FormatEnum.toEnum(itr.next());
                } else if (key.equalsIgnoreCase("--width")) {
                    width = getWidth(itr.next());
                }
            }

            Objects.requireNonNull(formatEnum, "FormatEnum is null");
            Objects.requireNonNull(width, "Width is null");

            return new Argument(formatEnum, width);

        } else {
            throw new IllegalArgumentException(
                String.format("Wrong number of arguments. Use \"--help\". arguments = %s",
                    Arrays.stream(args).toList()));
        }
    }

    private Integer getWidth(String width) {
        try {
            return Integer.valueOf(width);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Integer conversion error. width = %s", width), e);
        }
    }

    private boolean validateParseMode(String[] args) {
        return PARSE_MODE_ARG_NUMBER == args.length;
    }

    private boolean validateHelp(String[] args) {
        if (args.length == 0)
            return true;

        return (Arrays.stream(args).filter(x ->x.equalsIgnoreCase("--help")).count() == 1) &&
                (HELP_MODE_ARG_NUMBER == args.length);
    }
}