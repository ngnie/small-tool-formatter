package com.pub.format.modules.core;

import com.pub.format.modules.config.BeanConfig;
import com.pub.format.modules.usecase.SmallToolFormatter;

public class Main {

    public static void main(String[] args) {
        BeanConfig config = new BeanConfig();
        SmallToolFormatter formatter = new SmallToolFormatter(config);
        formatter.execute(args);
    }
}