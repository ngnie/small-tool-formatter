package com.pub.core;

import com.pub.config.BeanConfig;
import com.pub.usecase.SmallToolFormatter;

public class Main {

    public static void main(String[] args) {
        BeanConfig config = new BeanConfig();
        SmallToolFormatter formatter = new SmallToolFormatter(config);
        formatter.execute(args);
    }
}