package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.TokenAdder;

public class RightTokenAdder implements TokenAdder {

    @Override
    public void add(StringBuilder sb, String token) {
        sb.insert(0, token);
    }
}