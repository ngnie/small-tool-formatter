package com.pub.format.adapter.formatter.left;

import com.pub.format.adapter.formatter.common.TokenAdder;
import java.util.Objects;

public class LeftTokenAdder implements TokenAdder {

    @Override
    public void add(StringBuilder sb, String token) {
        Objects.requireNonNull(sb, "Trying to append token to result. sb is null.");
        Objects.requireNonNull(token, "Trying to append token to result. token is null.");
        sb.append(token);
    }
}