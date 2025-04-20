package com.pub.format.modules.domain.port;

import com.pub.format.modules.domain.model.Argument;

public interface Parser {
    Argument parse(String[] args);
}