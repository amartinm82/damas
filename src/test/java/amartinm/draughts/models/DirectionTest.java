package amartinm.draughts.models;

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
    public boolean expectedIsOnDirection;

    @Parameterized.Parameter(3)
    public int distance;

    @Parameterized.Parameter(4)
    public Coordinate expectedCoordinate;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Direction.NE, notEqualsRowAndColumnCoordinate(), false, 3, new CoordinateBuilder().row(-3).column(3).build()},
                {Direction.NE, zeroRowAndColumnCoordinate(), false, 4, new CoordinateBuilder().row(-4).column(4).build()},
                {Direction.NE, NECoordinate(), true, 2, new CoordinateBuilder().row(-2).column(2).build()},
                {Direction.NE, SWCoordinate(), false, 0, new CoordinateBuilder().column(0).build()},
                {Direction.NE, NWCoordinate(), false, -2, new CoordinateBuilder().row(2).column(-2).build()},
                {Direction.SE, SECoordinate(), true, 2, new CoordinateBuilder().row(2).column(2).build()},
                {Direction.SE, NWCoordinate(), false, 0, new CoordinateBuilder().column(0).build()},
                {Direction.SE, SWCoordinate(), false, 2, new CoordinateBuilder().row(2).column(2).build()},
                {Direction.SW, SWCoordinate(), true, -2, new CoordinateBuilder().row(-2).column(2).build()},
                {Direction.SW, NWCoordinate(), false, 0, new CoordinateBuilder().column(0).build()},
                {Direction.SW, SECoordinate(), false, 2, new CoordinateBuilder().row(2).column(-2).build()},
                {Direction.NW, NWCoordinate(), true, 2, new CoordinateBuilder().row(-2).column(-2).build()},
                {Direction.NW, SWCoordinate(), false, 0, new CoordinateBuilder().column(0).build()},
                {Direction.NW, NECoordinate(), false, -2, new CoordinateBuilder().row(2).column(2).build()}
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

    private static Coordinate notEqualsRowAndColumnCoordinate() {
        return new CoordinateBuilder().build();
    }

    private static Coordinate zeroRowAndColumnCoordinate() {
        return new CoordinateBuilder().column(0).build();
    }

    private static Coordinate NECoordinate() {
        return new CoordinateBuilder().row(-7).column(7).build();
    }

    private static Coordinate SWCoordinate() {
        return new CoordinateBuilder().row(1).column(-1).build();
    }

    private static Coordinate NWCoordinate() {
        return new CoordinateBuilder().row(-2).column(-2).build();
    }

    private static Coordinate SECoordinate() {
        return new CoordinateBuilder().row(5).column(5).build();
    }

}
