package usantatecla.draughts.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {

    private Coordinate coordinate;

    public CoordinateTest() {
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
        assertNull(this.coordinate.getDirection(new CoordinateBuilder().row(1).build()));
    }

    @Test
    public void testGivenCoordinateWhenGetDirectionWithCoordinateWithRightDirectionThenReturnDirection() {
        assertEquals(Direction.SW, this.coordinate.getDirection(new CoordinateBuilder().row(2).column(5).build()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenCheckIsOnDiagonalWithNullCoordinateThenThrowsAssertionError() {
        this.coordinate.isOnDiagonal(null);
    }

    @Test
    public void testGivenCoordinateWhenCheckIsOnDiagonalWithANotOnDiagonalCoordinateThenReturnFalse() {
        assertFalse(coordinate.isOnDiagonal(new CoordinateBuilder().column(6).build()));
    }

    @Test
    public void testGivenCoordinateWhenCheckIsOnDiagonalWithAnOnDiagonalCoordinateThenReturnTrue() {
        assertTrue(this.coordinate.isOnDiagonal(new CoordinateBuilder().row(1).column(6).build()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetDiagonalDistanceWithNullCoordinateThenThrowsAssertionError() {
        this.coordinate.getDiagonalDistance(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetDiagonalDistanceWithNotOnDiagonalCoordinateThenThrowsAssertionError() {
        this.coordinate.getDiagonalDistance(new CoordinateBuilder().row(1).build());
    }

    @Test
    public void testGivenCoordinateWhenGetDiagonalDistanceWithOnDiagonalCoordinateThenReturnDiagonalDistance() {
        assertEquals(7, this.coordinate.getDiagonalDistance(new CoordinateBuilder().row(7).column(0).build()));
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetBetweenDiagonalCoordinatesWithNullCoordinateThenThrowsAssertionError() {
        this.coordinate.getBetweenDiagonalCoordinates(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenCoordinateWhenGetBetweenDiagonalCoordinatesWithNotOnDiagonalCoordinateThenThrowsAssertionError() {
        this.coordinate.getBetweenDiagonalCoordinates(new CoordinateBuilder().row(2).build());
    }

    @Test
    public void testGivenCoordinateWhenGetBetweenDiagonalCoordinatesWithOnDiagonalCoordinateThenReturnCoordinateList() {
        assertArrayEquals(
                new Coordinate[]{
                        new CoordinateBuilder().row(1).column(6).build(),
                        new CoordinateBuilder().row(2).column(5).build()
                },
                this.coordinate.getBetweenDiagonalCoordinates(new CoordinateBuilder().row(3).column(4).build()).toArray());
    }

    @Test
    public void testGivenCoordinateWhenGetDiagonalCoordinatesWithOutOfLimitsLevelThenReturnEmptyCoordinateList() {
        assertTrue(this.coordinate.getDiagonalCoordinates(-8).isEmpty());
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
        assertArrayEquals(
                new Coordinate[]{
                        new CoordinateBuilder().row(5).column(5).build(),
                        new CoordinateBuilder().row(1).column(5).build(),
                        new CoordinateBuilder().row(1).column(1).build(),
                        new CoordinateBuilder().row(5).column(1).build()
                },
                new CoordinateBuilder().row(3).column(3).build().getDiagonalCoordinates(2).toArray());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsBlackWithWhiteThenReturnFalse() {
        assertFalse(new CoordinateBuilder().row(6).column(0).build().isBlack());
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
        assertTrue(new CoordinateBuilder().row(7).build().isLast());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsFirstWithNotFirstCoordinateThenReturnFalse() {
        assertFalse(new CoordinateBuilder().row(7).build().isFirst());
    }

    @Test
    public void testGivenCoordinateWhenCheckIsFirstWithFirstCoordinateThenReturnTrue() {
        assertTrue(this.coordinate.isFirst());
    }

}
