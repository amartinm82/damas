package usantatecla.draughts.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class ColorTest {

    @Parameterized.Parameter(0)
    public Color colorToTest;

    @Parameterized.Parameter(1)
    public int row;

    @Parameterized.Parameter(2)
    public Coordinate coordinate;

    @Parameterized.Parameter(3)
    public boolean expectedBoolean;

    @Parameterized.Parameter(4)
    public Color expectedColor;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Color.WHITE, 0, new CoordinateBuilder().column(2).build(), false, null},
                {Color.WHITE, 5, new CoordinateBuilder().row(4).column(1).build(), true, null},
                {Color.BLACK, 2, new CoordinateBuilder().row(4).column(1).build(), true, null},
                {Color.BLACK, 3, new CoordinateBuilder().row(2).column(1).build(), false, Color.BLACK},
        });
    }

    @Test
    public void testIsInitialRow() {
        assertEquals(expectedBoolean, colorToTest.isInitialRow(row));
    }

    @Test
    public void testGetInitialColor() {
        assertEquals(expectedColor, Color.getInitialColor(coordinate));
    }
}
