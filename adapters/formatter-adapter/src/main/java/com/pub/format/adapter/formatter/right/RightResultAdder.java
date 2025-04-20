package com.pub.format.adapter.formatter.right;

import com.pub.format.adapter.formatter.common.ResultAdder;
import java.util.Objects;

public class RightResultAdder implements ResultAdder {

    @Override
    public void add(StringBuilder alignment, StringBuilder result) {
        Objects.requireNonNull(alignment, "Trying to prepend alignment to result. Alignment is null");
        Objects.requireNonNull(result, "Trying to prepend alignment to result. Result is null");

        if (!alignment.isEmpty()) {
            result.insert(0, alignment).insert(0, "\n");
        }
    }

    @Override
    public void add(String alignment, StringBuilder result) {
        add(new StringBuilder(alignment), result);
    }
}