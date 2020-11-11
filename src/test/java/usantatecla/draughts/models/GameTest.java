package usantatecla.draughts.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {

    private Game game;
    private Board board;

    @Before
    public void before() {
        this.board = new Board();
        this.game = new Game(board);
    }

    @Test(expected = AssertionError.class)
    public void testGivenAGameWhenMoveWithNullCoordinatesThenThrowsAssertionError() {
        this.game.move(null);
    }

    @Test(expected = AssertionError.class)
    public void testGivenAGameWhenMoveWithEmptyCoordinatesThenThrowsAssertionError() {
        this.game.move(new Coordinate[]{});
    }

    @Test(expected = AssertionError.class)
    public void testGivenAGameWhenMoveWithCoordinatesWithNullElementThenThrowAssertionError() {
        assertEquals(Error.EMPTY_ORIGIN, this.game.move(new Coordinate[]{null}));
    }

    @Test
    public void testGivenAGameWhenMoveWithEmptyOriginThenReturnEmptyOriginError() {
        Coordinate[] coordinates = new Coordinate[]{
                new CoordinateBuilder().row(2).column(7).build(),
                new CoordinateBuilder().row(3).column(6).build(),
        };
        assertEquals(Error.EMPTY_ORIGIN, this.game.move(coordinates));
    }

    @Test
    public void testGivenAGameWhenMoveOppositeColorThenReturnOppositePieceError() {
        Coordinate originCoordinate = new CoordinateBuilder().row(2).column(7).build();
        Coordinate targetCoordinate = new CoordinateBuilder().row(3).column(6).build();
        this.board.put(originCoordinate, new Pawn(Color.BLACK));
        Coordinate[] coordinates = new Coordinate[]{originCoordinate, targetCoordinate};
        assertEquals(Error.OPPOSITE_PIECE, this.game.move(coordinates));
    }

    @Test
    public void testGivenAGameWhenMoveToNotEmptyCoordinateThenReturnNotEmptyTargetError() {
        Coordinate originCoordinate = new CoordinateBuilder().row(2).column(7).build();
        Coordinate targetCoordinate = new CoordinateBuilder().row(3).column(6).build();
        this.board.put(originCoordinate, new Pawn(Color.WHITE));
        this.board.put(targetCoordinate, new Pawn(Color.BLACK));
        Coordinate[] coordinates = new Coordinate[]{originCoordinate, targetCoordinate};
        assertEquals(Error.NOT_EMPTY_TARGET, this.game.move(coordinates));
    }

    @Test
    public void testGivenAGameWhenMoveNotOnDiagonalThenReturnNotDiagonalError() {
        Coordinate originCoordinate = new CoordinateBuilder().row(2).column(7).build();
        Coordinate targetCoordinate = new CoordinateBuilder().row(3).column(7).build();
        this.board.put(originCoordinate, new Pawn(Color.WHITE));
        Coordinate[] coordinates = new Coordinate[]{originCoordinate, targetCoordinate};
        assertEquals(Error.NOT_DIAGONAL, this.game.move(coordinates));
    }

    @Test
    public void testGivenAGameWhenMoveEatingAColleagueThenReturnColleagueEatingError() {
        Coordinate originCoordinate = new CoordinateBuilder().row(2).column(7).build();
        Coordinate targetCoordinate = new CoordinateBuilder().row(0).column(5).build();
        this.board.put(originCoordinate, new Pawn(Color.WHITE));
        this.board.put(new CoordinateBuilder().row(1).column(6).build(), new Pawn(Color.WHITE));
        Coordinate[] coordinates = new Coordinate[]{originCoordinate, targetCoordinate};
        assertEquals(Error.COLLEAGUE_EATING, this.game.move(coordinates));
    }

    //TODO: pending tests of move since isCorrectPairMove


}
