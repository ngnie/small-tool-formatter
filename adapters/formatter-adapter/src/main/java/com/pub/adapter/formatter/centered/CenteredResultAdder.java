package com.pub.adapter.formatter.centered;

import com.pub.adapter.formatter.common.ResultAdder;

public class CenteredResultAdder implements ResultAdder {
    final private int width;

    public CenteredResultAdder(int width) {
        this.width = width;
    }

    @Override
    public void add(StringBuilder alignment, StringBuilder result) {
        if (!alignment.isEmpty()) {
            String[] tokens = alignment.toString().split(" ");
            String lastToken = tokens[tokens.length - 1];
            int indexOfLastToken = alignment.indexOf(lastToken);
            int textWidth = indexOfLastToken + lastToken.length();

            if (textWidth > width - 2) {
                result.append(alignment).append("\n");
            } else {
                int padding = (width - textWidth) / 2;
                StringBuilder buf = new StringBuilder();
                buf.append(" ".repeat(Math.max(0, padding)));
                buf.append(alignment.subSequence(0, indexOfLastToken + lastToken.length()));
                buf.append(" ".repeat(Math.max(0, padding)));
                if (buf.length() + 1 == width)
                    buf.append(" ");
                result.append(buf).append("\n");
            }
        }
    }

    @Override
    public void add(String alignment, StringBuilder result) {
        add(new StringBuilder(alignment), result);
    }
}