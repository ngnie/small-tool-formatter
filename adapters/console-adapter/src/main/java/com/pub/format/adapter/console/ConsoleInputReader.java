package com.pub.format.adapter.console;

import com.pub.format.modules.domain.port.InputReader;
import java.util.Scanner;

public class ConsoleInputReader implements InputReader {

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }
}
