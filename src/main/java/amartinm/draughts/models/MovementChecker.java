package amartinm.draughts.models;

public interface MovementChecker {

    Error check(Board board, Turn turn, int pair, Coordinate[] coordinates);

}
