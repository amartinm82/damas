package usantatecla.draughts.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DirectionTest {

    @Parameterized.Parameter(0)
    public Direction directionToTest;

    @Parameterized.Parameter(1)
    public Coordinate coordinate;

    @Parameterized.Parameter(2)
    public int distance;

    @Parameterized.Parameter(3)
    public boolean expectedIsOnDirection;

    @Parameterized.Parameter(4)
    public Coordinate expectedCoordinate;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Direction.NE, new CoordinateBuilder().row(7).column(7).build(), 2, true, new CoordinateBuilder().row(2).column(2).build()},
                {Direction.NE, new CoordinateBuilder().build(), 3, false, new CoordinateBuilder().row(3).column(3).build()},
                {Direction.NE, new CoordinateBuilder().column(0).build(), 4, false, new CoordinateBuilder().row(4).column(4).build()},
                {Direction.NE, new CoordinateBuilder().row(-1).column(-1).build(), 0, false, new CoordinateBuilder().column(0).build()},
                {Direction.NE, new CoordinateBuilder().row(1).column(-1).build(), -2, false, new CoordinateBuilder().row(-2).column(-2).build()},
                {Direction.SE, new CoordinateBuilder().row(-7).column(7).build(), 2, true, new CoordinateBuilder().row(-2).column(2).build()},
                {Direction.SE, new CoordinateBuilder().row(1).column(-1).build(), 0, false, new CoordinateBuilder().column(0).build()},
                {Direction.SE, new CoordinateBuilder().row(-1).column(-1).build(), 2, false, new CoordinateBuilder().row(-2).column(2).build()},
                {Direction.SW, new CoordinateBuilder().row(-7).column(-7).build(), -2, true, new CoordinateBuilder().row(2).column(2).build()},
                {Direction.SW, new CoordinateBuilder().row(1).column(-1).build(), 0, false, new CoordinateBuilder().column(0).build()},
                {Direction.SW, new CoordinateBuilder().row(-1).column(1).build(), 2, false, new CoordinateBuilder().row(-2).column(-2).build()},
                {Direction.NW, new CoordinateBuilder().row(7).column(-7).build(), 2, true, new CoordinateBuilder().row(2).column(-2).build()},
                {Direction.NW, new CoordinateBuilder().row(-1).column(-1).build(), 0, false, new CoordinateBuilder().column(0).build()},
                {Direction.NW, new CoordinateBuilder().row(1).column(1).build(), -2, false, new CoordinateBuilder().row(-2).column(2).build()}
        });
    }

    @Test
    public void testIsOnDirection() {
        assertEquals(expectedIsOnDirection, directionToTest.isOnDirection(coordinate));
    }

    @Test
    public void testGetDistanceCoordinate() {
        assertEquals(expectedCoordinate, directionToTest.getDistanceCoordinate(distance));
    }

}
