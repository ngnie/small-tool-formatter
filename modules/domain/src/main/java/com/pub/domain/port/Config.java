package com.pub.domain.port;

import com.pub.domain.model.Argument;

public interface Config {
    InputReader getInputReader();
    OutputWriter getOutputWriter();
    Parser getArgumentParser();
    LineFormatter getLineFormatter(Argument argument);
    String getHelpDescription();
}