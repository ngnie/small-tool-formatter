package com.pub.format.adapter.formatter;

import com.pub.format.modules.domain.port.LineFormatter;
import com.pub.format.adapter.formatter.common.*;
import java.util.List;

public class AlignFormatter implements LineFormatter {
    final LineOrder lineOrder;
    final Aligner aligner;
    final TokenAdder tokenAdder;
    final ResultAdder resultAdder;
    final NewLineRemover newLineRemover;
    final private int width;

    public AlignFormatter(
            LineOrder lineOrder,
            Aligner aligner,
            TokenAdder tokenAdder,
            ResultAdder resultAdder,
            NewLineRemover newLineRemover,
            int width) {
        this.lineOrder = lineOrder;
        this.aligner = aligner;
        this.tokenAdder = tokenAdder;
        this.resultAdder = resultAdder;
        this.newLineRemover = newLineRemover;
        this.width = width;
    }

    @Override
    public String format(String input) {
        StringBuilder result = new StringBuilder();
        StringBuilder alignment = new StringBuilder();

        List<String> tokens = lineOrder.order(input);

        for (String token : tokens) {
            int len = token.length();

            if (len >= width) {
                if (!alignment.isEmpty()) {
                    aligner.align(alignment, width - alignment.length());
                    resultAdder.add(alignment, result);
                    clear(alignment);
                }

                resultAdder.add(token, result);

            } else {
                if (alignment.length() + len < width) {
                    if (alignment.isEmpty()) {
                        tokenAdder.add(alignment, token);
                    } else {
                        aligner.align(alignment, 1);
                        tokenAdder.add(alignment, token);
                    }
                } else {
                    aligner.align(alignment, width - alignment.length());
                    resultAdder.add(alignment, result);
                    clear(alignment);
                    tokenAdder.add(alignment, token);
                }
            }
        }

        aligner.align(alignment, width - alignment.length());
        resultAdder.add(alignment, result);

        newLineRemover.remove(result);

        return result.toString();
    }

    private void clear(StringBuilder sb) {
        sb.setLength(0);
    }
}
