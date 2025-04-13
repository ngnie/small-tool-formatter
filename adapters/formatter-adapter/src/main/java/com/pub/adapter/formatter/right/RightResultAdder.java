package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.ResultAdder;

public class RightResultAdder implements ResultAdder {

    @Override
    public void add(StringBuilder alignment, StringBuilder result) {
        if (!alignment.isEmpty()) {
            result.insert(0, alignment).insert(0, "\n");
        }
    }

    @Override
    public void add(String alignment, StringBuilder result) {
        add(new StringBuilder(alignment), result);
    }
}