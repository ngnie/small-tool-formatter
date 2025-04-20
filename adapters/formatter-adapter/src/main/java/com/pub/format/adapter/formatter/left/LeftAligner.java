package com.pub.format.adapter.formatter.left;

import com.pub.format.adapter.formatter.common.Aligner;
import java.util.Objects;

public class LeftAligner implements Aligner {

    @Override
    public void align(StringBuilder sb, int width) {

        if (Objects.isNull(sb))
            throw new IllegalArgumentException("Trying to append spaces. String builder is null");

        if (width < 0)
            throw new IllegalArgumentException("Trying to append spaces. Width is less than 0");

        if (!sb.isEmpty()) {
            sb.append(" ".repeat(width));
        }
    }
}