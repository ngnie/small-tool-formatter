package com.pub.format.adapter.console;

import com.pub.format.modules.domain.port.OutputWriter;

public class ConsoleOutputWriter implements OutputWriter {

    @Override
    public void write(String output) {
        System.out.println(output);
    }
}