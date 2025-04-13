package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.NewLineRemover;

public class RightNewLineRemover implements NewLineRemover {

    @Override
    public void remove(StringBuilder sb) {
        if (!sb.isEmpty()) {
            sb.deleteCharAt(0);
        }
    }
}