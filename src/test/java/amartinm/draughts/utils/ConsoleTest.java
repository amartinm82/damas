package amartinm.draughts.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConsoleTest {

    private static final String TITLE = "title ";
    private static final String READ_STRING = "Read string";
    private static final String FORMAT_ERROR = "ERROR DE FORMATO! Introduzca un valor con formato %s.";
    private static final String STRING_FORMAT = "de cadena de caracteres";
    private static final String CHAR_FORMAT = "caracter";
    private static final char READ_CHAR = 'R';

    @InjectMocks
    private Console console;

    @Mock
    private BufferedReader bufferedReader;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void before() {
        initMocks(this);
        System.setOut(new PrintStream(this.outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(this.standardOut);
    }

    @Test
    public void testGivenConsoleWhenReadStringAndBufferReaderThrowsExceptionAtFirstAndReadStringAtSecondThenWriteErrorAtFirstAndReturnReadStringAtSecond() throws IOException {
        when(bufferedReader.readLine()).thenThrow(IOException.class).thenReturn(READ_STRING);
        assertEquals(READ_STRING, this.console.readString(TITLE));
        assertEquals(TITLE
                        + String.format(FORMAT_ERROR, STRING_FORMAT)
                        + "\n" + TITLE,
                outputStreamCaptor.toString());
    }

    @Test
    public void testGivenConsoleWhenReadCharAndReadStringAtFirstAndReadCharAtSecondThenWriteErrorAtFirstAndReturnReadCharAtSecond() throws IOException {
        when(bufferedReader.readLine()).thenReturn(READ_STRING, String.valueOf(READ_CHAR));
        assertEquals(READ_CHAR, this.console.readChar(TITLE));
        assertEquals(TITLE
                        + String.format(FORMAT_ERROR, CHAR_FORMAT)
                        + "\n" + TITLE,
                outputStreamCaptor.toString());
    }

}
