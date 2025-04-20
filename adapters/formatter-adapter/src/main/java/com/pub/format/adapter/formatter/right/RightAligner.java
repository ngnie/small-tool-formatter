package com.pub.format.adapter.formatter.right;

import com.pub.format.adapter.formatter.common.Aligner;
import java.util.Objects;

public class RightAligner implements Aligner {

    @Override
    public void align(StringBuilder sb, int width) {

        if (Objects.isNull(sb))
            throw new IllegalArgumentException("Trying to insert spaces. String builder is null");

        if (width < 0)
            throw new IllegalArgumentException("Trying to insert spaces. Width is less than 0");


        if (!sb.isEmpty()) {
            sb.insert(0, " ".repeat(width));
        }
    }
}