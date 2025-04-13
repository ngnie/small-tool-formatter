package com.pub.adapter.formatter.left;

import com.pub.adapter.formatter.common.ResultAdder;

public class LeftResultAdder implements ResultAdder {

    @Override
    public void add(StringBuilder alignment, StringBuilder result) {
        if (!alignment.isEmpty()) {
            result.append(alignment).append("\n");
        }
    }

    @Override
    public void add(String alignment, StringBuilder result) {
        add(new StringBuilder(alignment), result);
    }
}
