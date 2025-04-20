package com.pub.format.modules.usecase;

import com.pub.format.modules.domain.model.Argument;
import com.pub.format.modules.domain.model.Executor;
import com.pub.format.modules.domain.port.*;

public class SmallToolFormatter implements Executor<String[]> {
    final private Config config;

    public SmallToolFormatter(Config config) {
        this.config = config;
    }

    @Override
    public void execute(String[] args) {
        Parser parser = config.getArgumentParser();
        InputReader inputReader = config.getInputReader();
        OutputWriter outputWriter = config.getOutputWriter();
        Argument argument = parser.parse(args);
        if (argument.getHelp()) {
            outputWriter.write(config.getHelpDescription());
        } else {
            String input = inputReader.read();
            LineFormatter lineFormatter = config.getLineFormatter(argument);
            String output = lineFormatter.format(input);
            outputWriter.write(output);
        }
    }
}