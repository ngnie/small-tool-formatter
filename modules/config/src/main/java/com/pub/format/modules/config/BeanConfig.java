package com.pub.format.modules.config;

import com.pub.format.adapter.argument.ArgumentParser;
import com.pub.format.adapter.console.ConsoleInputReader;
import com.pub.format.adapter.console.ConsoleOutputWriter;
import com.pub.format.adapter.formatter.AlignFormatter;
import com.pub.format.adapter.formatter.centered.CenteredResultAdder;
import com.pub.format.adapter.formatter.left.LeftAligner;
import com.pub.format.adapter.formatter.common.DefaultNewLineRemover;
import com.pub.format.adapter.formatter.common.DefaultLineOrder;
import com.pub.format.adapter.formatter.left.LeftTokenAdder;
import com.pub.format.adapter.formatter.left.LeftResultAdder;
import com.pub.format.modules.domain.model.Argument;
import com.pub.format.modules.domain.model.FormatEnum;
import com.pub.format.adapter.formatter.right.*;
import com.pub.format.modules.domain.port.*;
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