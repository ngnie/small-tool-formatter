package com.pub.adapter.formatter.common;

public class DefaultNewLineRemover implements NewLineRemover {

    @Override
    public void remove(StringBuilder sb) {
        if (!sb.isEmpty()) {
            sb.deleteCharAt(sb.length()-1);
        }
    }
}