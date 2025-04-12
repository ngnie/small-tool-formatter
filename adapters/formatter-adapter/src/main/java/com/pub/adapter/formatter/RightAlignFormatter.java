package com.pub.adapter.formatter;

import com.pub.domain.port.LineFormatter;
import java.util.Arrays;
import java.util.List;

public class RightAlignFormatter implements LineFormatter {
    final private int width;

    public RightAlignFormatter(int width) {
        this.width = width;
    }

    @Override
    public String format(String input) {
        List<String> tokens = Arrays.asList(input.split("\\s+")).reversed();
        StringBuilder result = new StringBuilder();
        StringBuilder alignment = new StringBuilder();

        for (String token : tokens) {
            int len = token.length();

            if (len >= width) {
                if (!alignment.isEmpty()) {
                    addSpacesToAlignment(alignment, width - alignment.length());
                    prependToResult(alignment, result);
                    clear(alignment);
                }

                prependToResult(token, result);

            } else {
                if (alignment.length() + len < width) {
                    if (alignment.isEmpty()) {
                        alignment.insert(0, token);
                    } else {
                        addSpacesToAlignment(alignment, 1);
                        alignment.insert(0, token);
                    }
                } else {
                    addSpacesToAlignment(alignment, width - alignment.length());
                    prependToResult(alignment, result);
                    clear(alignment);
                    alignment.insert(0, token);
                }
            }
        }

        addSpacesToAlignment(alignment, width - alignment.length());
        prependToResult(alignment, result);

        removeFirstNewline(result);

        return result.toString();
    }

    private void clear(StringBuilder sb) {
        sb.setLength(0);
    }

    private void removeFirstNewline(StringBuilder sb) {
        if (!sb.isEmpty()) {
            sb.deleteCharAt(0);
        }
    }

    private void prependToResult(StringBuilder alignment, StringBuilder result) {
        if (!alignment.isEmpty()) {
            result.insert(0, alignment).insert(0, "\n");
        }
    }

    private void prependToResult(String alignment, StringBuilder result) {
        prependToResult(new StringBuilder(alignment), result);
    }

    private void addSpacesToAlignment(StringBuilder sb, int number) {
        if (!sb.isEmpty()) {
            sb.insert(0, " ".repeat(Math.max(0, number)));
        }
    }
}