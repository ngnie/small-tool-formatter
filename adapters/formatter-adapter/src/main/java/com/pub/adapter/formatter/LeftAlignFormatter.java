package com.pub.adapter.formatter;

import com.pub.domain.port.LineFormatter;

public class LeftAlignFormatter implements LineFormatter {
    final private int width;

    public LeftAlignFormatter(int width) {
        this.width = width;
    }

    @Override
    public String format(String input) {
        String[] tokens = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        StringBuilder alignment = new StringBuilder();

        for (String token : tokens) {
            int len = token.length();

            if (len >= width) {
                if (!alignment.isEmpty()) {
                    addSpacesToAlignment(alignment, width - alignment.length());
                    appendToResult(alignment, result);
                    clear(alignment);
                }

                appendToResult(token, result);

            } else {
                if (alignment.length() + len < width) {
                    if (alignment.isEmpty()) {
                        alignment.append(token);
                    } else {
                        addSpacesToAlignment(alignment, 1);
                        alignment.append(token);
                    }
                } else {
                    addSpacesToAlignment(alignment, width - alignment.length());
                    appendToResult(alignment, result);
                    clear(alignment);
                    alignment.append(token);
                }
            }
        }

        addSpacesToAlignment(alignment, width - alignment.length());
        appendToResult(alignment, result);

        removeLastNewline(result);

        return result.toString();
    }

    private void clear(StringBuilder sb) {
        sb.setLength(0);
    }

    private void removeLastNewline(StringBuilder sb) {
        if (!sb.isEmpty()) {
            sb.deleteCharAt(sb.length()-1);
        }
    }

    private void appendToResult(StringBuilder left, StringBuilder result) {
        if (!left.isEmpty()) {
            result.append(left).append("\n");
        }
    }

    private void appendToResult(String left, StringBuilder result) {
        appendToResult(new StringBuilder(left), result);
    }

    private void addSpacesToAlignment(StringBuilder sb, int number) {
        if (!sb.isEmpty()) {
            sb.append(" ".repeat(Math.max(0, number)));
        }
    }
}