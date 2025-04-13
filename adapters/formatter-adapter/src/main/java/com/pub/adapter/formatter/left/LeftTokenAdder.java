package com.pub.adapter.formatter.left;

import com.pub.adapter.formatter.common.TokenAdder;

public class LeftTokenAdder implements TokenAdder {

    @Override
    public void add(StringBuilder sb, String token) {
        sb.append(token);
    }
}