package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.Aligner;

public class RightAligner implements Aligner {

    @Override
    public void align(StringBuilder sb, int width) {
        if (!sb.isEmpty()) {
            sb.insert(0, " ".repeat(Math.max(0, width)));
        }
    }
}