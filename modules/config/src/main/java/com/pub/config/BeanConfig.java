package com.pub.config;

import com.pub.adapter.argument.ArgumentParser;
import com.pub.adapter.console.ConsoleInputReader;
import com.pub.adapter.console.ConsoleOutputWriter;
import com.pub.adapter.formatter.CenteredFormatter;
import com.pub.adapter.formatter.LeftAlignFormatter;
import com.pub.adapter.formatter.RightAlignFormatter;
import com.pub.domain.model.Argument;
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
        return switch (argument.getFormatEnum()) {
            case LEFT_ALIGNED -> new LeftAlignFormatter(argument.getWidth());
            case RIGHT_ALIGNED -> new RightAlignFormatter(argument.getWidth());
            case CENTERED -> new CenteredFormatter();
            default -> throw new IllegalArgumentException(
                String.format("Failed to create line formatter. type = %s", argument.getFormatEnum().type()));
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