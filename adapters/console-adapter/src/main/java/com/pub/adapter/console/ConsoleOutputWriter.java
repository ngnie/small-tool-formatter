package com.pub.adapter.console;

import com.pub.domain.port.OutputWriter;

public class ConsoleOutputWriter implements OutputWriter {

    @Override
    public void write(String output) {
        System.out.println(output);
    }
}