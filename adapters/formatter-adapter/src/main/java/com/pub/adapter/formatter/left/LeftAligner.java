package com.pub.adapter.formatter.left;

import com.pub.adapter.formatter.common.Aligner;

public class LeftAligner implements Aligner {

    @Override
    public void align(StringBuilder sb, int width) {
        if (!sb.isEmpty()) {
            sb.append(" ".repeat(Math.max(0, width)));
        }
    }
}