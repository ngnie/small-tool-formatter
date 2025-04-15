package com.pub.usecase;

import com.pub.domain.model.Argument;
import com.pub.domain.model.FormatEnum;
import com.pub.domain.port.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SmallToolFormatterTest {
    final private Config config = Mockito.mock(Config.class);
    final private Parser parser = Mockito.mock(Parser.class);
    final private InputReader inputReader = Mockito.mock(InputReader.class);
    final private OutputWriter outputWriter = Mockito.mock(OutputWriter.class);
    final private LineFormatter lineFormatter = Mockito.mock(LineFormatter.class);

    @BeforeEach
    public void before() {
        when(config.getArgumentParser()).thenReturn(parser);
        when(config.getInputReader()).thenReturn(inputReader);
        when(config.getLineFormatter(any())).thenReturn(lineFormatter);
        when(config.getOutputWriter()).thenReturn(outputWriter);
    }

    @Test
    public void testHelp() {
        SmallToolFormatter formatter = new SmallToolFormatter(config);
        Argument argument = new Argument(true);
        when(parser.parse(any())).thenReturn(argument);
        formatter.execute(new String[]{"some-array-that-doesn't-matter"});
        verify(config, times(1)).getHelpDescription();
        verify(inputReader, times(0)).read();
    }

    @Test
    public void testParse() {
        SmallToolFormatter formatter = new SmallToolFormatter(config);
        Argument argument = new Argument(FormatEnum.LEFT_ALIGNED, 3);
        when(parser.parse(any())).thenReturn(argument);
        formatter.execute(new String[]{"some-array-that-doesn't-matter"});
        verify(config, times(0)).getHelpDescription();
        verify(inputReader, times(1)).read();
    }
}