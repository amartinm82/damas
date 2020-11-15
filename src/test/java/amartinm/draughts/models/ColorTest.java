package amartinm.draughts.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ColorTest {

    @Parameterized.Parameter(0)
    public Color colorToTest;

    @Parameterized.Parameter(1)
    public int row;

    @Parameterized.Parameter(2)
    public boolean expectedBoolean;

    @Parameterized.Parameter(3)
    public Coordinate coordinate;

    @Parameterized.Parameter(4)
    public Color expectedColor;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Color.WHITE, 0, false, blackCoordinate(), null},
                {Color.WHITE, 5, true, notInitialRowCoordinate(), null},
                {Color.BLACK, 2, true, notInitialRowCoordinate(), null},
                {Color.BLACK, 3, false, initialRowCoordinate(), Color.BLACK},
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

    private static Coordinate blackCoordinate() {
        return new CoordinateBuilder().column(2).build();
    }

    private static Coordinate notInitialRowCoordinate() {
        return new CoordinateBuilder().row(4).column(1).build();
    }

    private static Coordinate initialRowCoordinate() {
        return new CoordinateBuilder().row(2).column(1).build();
    }

}
