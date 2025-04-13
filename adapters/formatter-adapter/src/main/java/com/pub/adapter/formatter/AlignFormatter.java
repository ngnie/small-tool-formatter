package com.pub.adapter.formatter;

import com.pub.adapter.formatter.common.*;
import com.pub.domain.port.LineFormatter;
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
        //String[] tokens = input.split("\\s+");
        List<String> tokens = lineOrder.order(input);
        StringBuilder result = new StringBuilder();
        StringBuilder alignment = new StringBuilder();

        for (String token : tokens) {
            int len = token.length();

            if (len >= width) {
                if (!alignment.isEmpty()) {
                    //align(alignment, width - alignment.length());
                    aligner.align(alignment, width - alignment.length());
                    //appendToResult(alignment, result);
                    resultAdder.add(alignment, result);
                    clear(alignment);
                }

                //appendToResult(token, result);
                resultAdder.add(token, result);

            } else {
                if (alignment.length() + len < width) {
                    if (alignment.isEmpty()) {
                        //alignment.append(token);
                        tokenAdder.add(alignment, token);
                    } else {
                        //align(alignment, 1);
                        aligner.align(alignment, 1);
                        //alignment.append(token);
                        tokenAdder.add(alignment, token);
                    }
                } else {
                    //align(alignment, width - alignment.length());
                    aligner.align(alignment, width - alignment.length());
                    //appendToResult(alignment, result);
                    resultAdder.add(alignment, result);
                    clear(alignment);
                    //alignment.append(token);
                    tokenAdder.add(alignment, token);
                }
            }
        }

        //align(alignment, width - alignment.length());
        aligner.align(alignment, width - alignment.length());
        //appendToResult(alignment, result);
        resultAdder.add(alignment, result);

        //removeLastNewline(result);
        newLineRemover.remove(result);

        return result.toString();
    }

    private void clear(StringBuilder sb) {
        sb.setLength(0);
    }

    /*private void removeLastNewline(StringBuilder sb) {
        if (!sb.isEmpty()) {
            sb.deleteCharAt(sb.length()-1);
        }
    }*/

    /*private void appendToResult(StringBuilder left, StringBuilder result) {
        if (!left.isEmpty()) {
            result.append(left).append("\n");
        }
    }

    private void appendToResult(String left, StringBuilder result) {
        appendToResult(new StringBuilder(left), result);
    }*/

    /*private void align(StringBuilder sb, int number) {
        if (!sb.isEmpty()) {
            sb.append(" ".repeat(Math.max(0, number)));
        }
    }*/
}