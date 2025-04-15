package com.pub.config;

import com.pub.adapter.argument.ArgumentParser;
import com.pub.adapter.console.ConsoleInputReader;
import com.pub.adapter.console.ConsoleOutputWriter;
import com.pub.adapter.formatter.AlignFormatter;
import com.pub.adapter.formatter.centered.CenteredResultAdder;
import com.pub.adapter.formatter.left.LeftAligner;
import com.pub.adapter.formatter.common.DefaultNewLineRemover;
import com.pub.adapter.formatter.common.DefaultLineOrder;
import com.pub.adapter.formatter.left.LeftTokenAdder;
import com.pub.adapter.formatter.left.LeftResultAdder;
import com.pub.adapter.formatter.right.*;
import com.pub.domain.model.Argument;
import com.pub.domain.model.FormatEnum;
import com.pub.domain.port.*;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class BeanConfig implements Config {
    final private Properties properties = new Properties();

    public BeanConfig() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            if (Objects.nonNull(inputStream))
                inputStream.close();
        } catch (Exception e) {
            System.out.println("Failed to load properties");
        }
    }

    public Parser getArgumentParser() {
        return new ArgumentParser();
    }

    public LineFormatter getLineFormatter(Argument argument) {
        FormatEnum formatEnum = argument.getFormatEnum().orElseThrow(() -> new NullPointerException("FormatEnum is null"));
        Integer width = argument.getWidth().orElseThrow(() -> new NullPointerException("Width is null"));
        return switch (formatEnum) {
            case LEFT_ALIGNED -> new AlignFormatter(
                    new DefaultLineOrder(),
                    new LeftAligner(),
                    new LeftTokenAdder(),
                    new LeftResultAdder(),
                    new DefaultNewLineRemover(),
                    width);
            case RIGHT_ALIGNED -> new AlignFormatter(
                    new ReverseLineOrder(),
                    new RightAligner(),
                    new RightTokenAdder(),
                    new RightResultAdder(),
                    new RightNewLineRemover(),
                    width);
            case CENTERED -> new AlignFormatter(
                    new DefaultLineOrder(),
                    new LeftAligner(),
                    new LeftTokenAdder(),
                    new CenteredResultAdder(width),
                    new DefaultNewLineRemover(),
                    width);
            default -> throw new IllegalArgumentException(
                String.format("Failed to create line formatter. type = %s", formatEnum.type()));
        };
    }

    public InputReader getInputReader() {
        return new ConsoleInputReader();
    }

    public OutputWriter getOutputWriter() {
        return new ConsoleOutputWriter();
    }

    public String getHelpDescription() {
        return properties.getProperty("help.description");
    }
}