package amartinm.draughts.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CoordinateTest {

    private static final int UPPER_LIMIT = Coordinate.getDimension() - 1;

    private Coordinate coordinate;

    @Before
    public void before() {
        this.coordinate = new CoordinateBuilder().build();
    }

    @Test(expected = AssertionError.class)
    public void testGivenAFormatWhenIsNullThenThrowsAssertionError() {
        Coordinate.getInstance(null);
    }

    @Test
    public void testGivenAFormatWhenIsNotANumberThenReturnNull() {
        assertNull(Coordinate.getInstance("ab"));
    }

    @Test
    public void testGivenAFormatWhenIsOutOfLimitsThenReturnNull() {
        assertNull(Coordinate.getInstance("-9"));
    }

    @Test
    public void testGivenAFormatWhenIsOkThenReturnCoordinate() {
        assertEquals(new CoordinateBuilder().row(1).build(),
                Coordinate.getInstance("28"));
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetDirectionWithNullCoordinateThenThrowsAssertionError() {
        this.coordinate.getDirection(null);
    }

    @Test
    public void testGivenCoordinateWhenGetDirectionWithCoordinateWithWrongDirectionThenReturnNull() {
        assertNull(this.coordinate.getDirection(notOnDiagonalCoordinate()));
    }

    @Test
    public void testGivenCoordinateWhenGetDirectionWithCoordinateWithRightDirectionThenReturnDirection() {
        assertEquals(Direction.SW, this.coordinate.getDirection(onDiagonalCoordinates(2)[1]));
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenCheckIsOnDiagonalWithNullCoordinateThenThrowsAssertionError() {
        this.coordinate.isOnDiagonal(null);
    }

    @Test
    public void testGivenCoordinateWhenCheckIsOnDiagonalWithANotOnDiagonalCoordinateThenReturnFalse() {
        assertFalse(coordinate.isOnDiagonal(notOnDiagonalCoordinate()));
    }

    @Test
    public void testGivenCoordinateWhenCheckIsOnDiagonalWithAnOnDiagonalCoordinateThenReturnTrue() {
        assertTrue(this.coordinate.isOnDiagonal(onDiagonalCoordinates(1)[0]));
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetDiagonalDistanceWithNullCoordinateThenThrowsAssertionError() {
        this.coordinate.getDiagonalDistance(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetDiagonalDistanceWithNotOnDiagonalCoordinateThenThrowsAssertionError() {
        this.coordinate.getDiagonalDistance(notOnDiagonalCoordinate());
    }

    @Test
    public void testGivenCoordinateWhenGetDiagonalDistanceWithOnDiagonalCoordinateThenReturnDiagonalDistance() {
        assertEquals(UPPER_LIMIT, this.coordinate.getDiagonalDistance(lastCoordinate()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetBetweenDiagonalCoordinatesWithNullCoordinateThenThrowsAssertionError() {
        this.coordinate.getBetweenDiagonalCoordinates(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetBetweenDiagonalCoordinatesWithNotOnDiagonalCoordinateThenThrowsAssertionError() {
        this.coordinate.getBetweenDiagonalCoordinates(notOnDiagonalCoordinate());
    }

    @Test
    public void testGivenCoordinateWhenGetBetweenDiagonalCoordinatesWithOnDiagonalCoordinateThenReturnCoordinateList() {
        Coordinate[] diagonalCoordinates = onDiagonalCoordinates(3);
        assertArrayEquals(
                Arrays.copyOf(diagonalCoordinates, 2),
                this.coordinate.getBetweenDiagonalCoordinates(diagonalCoordinates[2]).toArray());
    }

    @Test
    public void testGivenCoordinateWhenGetDiagonalCoordinatesWithOutOfLimitsLevelThenReturnEmptyCoordinateList() {
        assertTrue(this.coordinate.getDiagonalCoordinates(UPPER_LIMIT + 1).isEmpty());
    }

    @Test
    public void testGivenCoordinateWhenGetDiagonalCoordinatesWithLevelOnLimitThenReturnAllowedDirectionsCoordinateList() {
        assertArrayEquals(
                new Coordinate[]{
                        new CoordinateBuilder().row(2).column(5).build()
                },
                this.coordinate.getDiagonalCoordinates(2).toArray());
    }

    @Test
    public void testGivenCoordinateWhenGetDiagonalCoordinatesWithMiddleLevelThenReturnAllDirectionsCoordinateList() {
        Coordinate centerCoordinate = centerCoordinate();
        assertArrayEquals(
                allDiagonalCoordinates(centerCoordinate, 2),
                centerCoordinate.getDiagonalCoordinates(2).toArray());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsBlackWithWhiteThenReturnFalse() {
        assertFalse(whiteCoordinate().isBlack());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsBlackWithBlackThenReturnTrue() {
        assertTrue(this.coordinate.isBlack());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsLastWithNotLastCoordinateThenReturnFalse() {
        assertFalse(this.coordinate.isLast());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsLastWithLastCoordinateThenReturnTrue() {
        assertTrue(lastCoordinate().isLast());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsFirstWithNotFirstCoordinateThenReturnFalse() {
        assertFalse(lastCoordinate().isFirst());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsFirstWithFirstCoordinateThenReturnTrue() {
        assertTrue(this.coordinate.isFirst());
    }

    private static Coordinate notOnDiagonalCoordinate() {
        return new CoordinateBuilder().row(1).build();
    }

    private static Coordinate[] onDiagonalCoordinates(int distance) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 1; i <= distance; i++) {
            coordinates.add(new CoordinateBuilder().row(i).column(UPPER_LIMIT - i).build());
        }
        return coordinates.toArray(new Coordinate[distance]);
    }

    private static Coordinate centerCoordinate() {
        return new CoordinateBuilder().row(3).column(3).build();
    }

    private static Coordinate[] allDiagonalCoordinates(Coordinate origin, int distance) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinate distanceCoordinate = direction.getDistanceCoordinate(distance);
            Coordinate diagonalCoordinate = new CoordinateBuilder()
                    .row(origin.getRow() + distanceCoordinate.getRow())
                    .column(origin.getColumn() + distanceCoordinate.getColumn())
                    .build();
            coordinates.add(diagonalCoordinate);
        }
        return coordinates.toArray(new Coordinate[Direction.values().length]);
    }

    private static Coordinate whiteCoordinate() {
        return new CoordinateBuilder().row(6).column(0).build();
    }

    private static Coordinate lastCoordinate() {
        return new CoordinateBuilder().row(UPPER_LIMIT).column(0).build();
    }
}
