package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.LineOrder;
import java.util.Arrays;
import java.util.List;

public class ReverseLineOrder implements LineOrder {

    @Override
    public List<String> order(String input) {
        return Arrays.asList(input.split("\\s+")).reversed();
    }
}