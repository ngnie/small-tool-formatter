package com.pub.domain.port;

import com.pub.domain.model.Argument;

public interface Parser {
    Argument parse(String[] args);
}