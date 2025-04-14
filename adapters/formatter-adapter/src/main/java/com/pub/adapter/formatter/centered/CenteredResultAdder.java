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
            int textWidth = indexOfLastNonSpaceChar(alignment) + 1;
            if (textWidth > width - 2) {
                result.append(alignment).append("\n");
            } else {
                int padding = (width - textWidth) / 2;
                StringBuilder buf = new StringBuilder();
                buf.append(" ".repeat(Math.max(0, padding)));
                buf.append(alignment.subSequence(0, textWidth));
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

    private int indexOfLastNonSpaceChar(StringBuilder sb) {
        int index = sb.length() - 1;
        while ((sb.charAt(index) == ' ') && (index != 0)) {
            index--;
        }

        return index;
    }
}