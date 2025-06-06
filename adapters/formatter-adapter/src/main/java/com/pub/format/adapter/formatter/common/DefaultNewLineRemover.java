package com.pub.format.adapter.formatter.common;

import java.util.Objects;

public class DefaultNewLineRemover implements NewLineRemover {

    @Override
    public void remove(StringBuilder sb) {
        Objects.requireNonNull(sb, "Trying to remove newline. String builder is null");
        if (!sb.isEmpty()) {
            if (sb.charAt(sb.length()-1) == '\n')
                sb.deleteCharAt(sb.length()-1);
        }
    }
}