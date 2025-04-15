package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.NewLineRemover;
import java.util.Objects;

public class RightNewLineRemover implements NewLineRemover {

    @Override
    public void remove(StringBuilder sb) {
        Objects.requireNonNull(sb, "Trying to remove newline. String builder is null.");
        if (!sb.isEmpty()) {
            if (sb.charAt(0) == '\n')
                sb.deleteCharAt(0);
        }
    }
}