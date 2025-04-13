package com.pub.adapter.formatter.common;

import java.util.Arrays;
import java.util.List;

public class DefaultLineOrder implements LineOrder {

    @Override
    public List<String> order(String input) {
        return Arrays.asList(input.split("\\s+"));
    }
}