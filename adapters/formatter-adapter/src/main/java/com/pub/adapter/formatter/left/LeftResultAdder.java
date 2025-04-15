package com.pub.adapter.formatter.left;

import com.pub.adapter.formatter.common.ResultAdder;
import java.util.Objects;

public class LeftResultAdder implements ResultAdder {

    @Override
    public void add(StringBuilder alignment, StringBuilder result) {
        Objects.requireNonNull(alignment, "Trying to append alignment to result. Alignment is null");
        Objects.requireNonNull(result, "Trying to append alignment to result. Result is null");

        if (!alignment.isEmpty()) {
            result.append(alignment).append("\n");
        }
    }

    @Override
    public void add(String alignment, StringBuilder result) {
        add(new StringBuilder(alignment), result);
    }
}
