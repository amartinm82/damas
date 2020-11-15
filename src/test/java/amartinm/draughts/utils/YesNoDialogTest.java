package amartinm.draughts.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class YesNoDialogTest {

    private static final String TITLE = "¿Queréis jugar otra";
    private static final char AFFIRMATIVE = 'y';
    private static final char NEGATIVE = 'n';
    private static final String QUESTION = "? ("+AFFIRMATIVE+"/"+NEGATIVE+"): ";
    private static final String ERROR = "The value must be '" + AFFIRMATIVE + "' or '" + NEGATIVE + "'";

    @InjectMocks
    private YesNoDialog yesNoDialog;

    @Mock
    private Console console;

    @Before
    public void before(){
        initMocks(this);
    }

    @Test
    public void testGivenAYesNoDialogWhenReadAnUnexpectedCharAtFirstTimeAndAfirmativeAtSecondThenWriteErrorMessageAtFirstAndReturnTrueAtSecond() {
        when(this.console.readChar(TITLE + QUESTION)).thenReturn('s', AFFIRMATIVE);
        assertTrue(this.yesNoDialog.read(TITLE));
        verify(this.console, times(1)).writeln(ERROR);
    }

}
