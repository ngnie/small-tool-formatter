package com.pub.format.adapter.formatter.common;

public interface ResultAdder {
    void add(StringBuilder alignment, StringBuilder result);
    void add(String alignment, StringBuilder result);
}