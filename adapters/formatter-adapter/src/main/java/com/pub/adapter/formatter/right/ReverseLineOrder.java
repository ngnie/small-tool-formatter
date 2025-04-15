package com.pub.adapter.formatter.right;

import com.pub.adapter.formatter.common.LineOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReverseLineOrder implements LineOrder {

    @Override
    public List<String> order(String input) {
        if (Objects.isNull(input) || input.trim().isEmpty())
            return new ArrayList<>();

        return Arrays.asList(input.trim().split("\\s+")).reversed();
    }
}