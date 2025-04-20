package com.pub.format.adapter.formatter.right;

import com.pub.format.adapter.formatter.common.TokenAdder;
import java.util.Objects;

public class RightTokenAdder implements TokenAdder {

    @Override
    public void add(StringBuilder sb, String token) {
        Objects.requireNonNull(sb, "Trying to prepend token to result. sb is null.");
        Objects.requireNonNull(token, "Trying to prepend token to result. token is null.");
        sb.insert(0, token);
    }
}