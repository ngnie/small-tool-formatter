package com.pub.format.modules.domain.port;

import com.pub.format.modules.domain.model.Argument;

public interface Config {
    InputReader getInputReader();
    OutputWriter getOutputWriter();
    Parser getArgumentParser();
    LineFormatter getLineFormatter(Argument argument);
    String getHelpDescription();
}