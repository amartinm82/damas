package amartinm.draughts.models;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private Turn turn;


    Game(Board board) {
        this.turn = new Turn();
        this.board = board;
    }

    public Game() {
        this(new Board());
        this.reset();
    }

    public void reset() {
        for (int i = 0; i < Coordinate.getDimension(); i++)
            for (int j = 0; j < Coordinate.getDimension(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Color color = Color.getInitialColor(coordinate);
                Piece piece = null;
                if (color != null && !color.isNull())
                    piece = new Pawn(color);
                this.board.put(coordinate, piece);
            }
        if (this.turn.getColor() != Color.WHITE)
            this.turn.change();
    }

    public Error move(Coordinate... coordinates) {
        assert coordinates != null && coordinates.length > 0;
        Movement movement = new Movement(coordinates);
        Error error = movement.isCorrect(board, turn);
        if (error == null) {
            this.board.move(movement.getFirstCoordinate(), movement.getLastCoordinate());
            this.removePieces(movement.getCoordinatesToRemove());
            if (this.hasReachLimit(movement.getLastCoordinate())) {
                this.convertToDraught(movement.getLastCoordinate());
            }
            this.turn.change();
        }
        return error;
    }

    private void removePieces(List<Coordinate> coordinatesToRemove) {
        if (!coordinatesToRemove.isEmpty()) {
            for (Coordinate forRemoving : coordinatesToRemove) {
                this.board.remove(forRemoving);
            }
        }
    }

    private boolean hasReachLimit(Coordinate coordinate) {
        return this.board.getPiece(coordinate).isLimit(coordinate);
    }

    private void convertToDraught(Coordinate coordinate) {
        if (this.board.getPiece(coordinate) instanceof Draught)
            return;

        Color color = this.board.getColor(coordinate);
        this.board.remove(coordinate);
        this.board.put(coordinate, new Draught(color));
    }

    public boolean isBlocked() {
        for (Coordinate coordinate : this.getCoordinatesWithActualColor())
            if (!this.isBlocked(coordinate))
                return false;
        return true;
    }

    private List<Coordinate> getCoordinatesWithActualColor() {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        for (int i = 0; i < this.getDimension(); i++) {
            for (int j = 0; j < this.getDimension(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Piece piece = this.getPiece(coordinate);
                if (piece != null && piece.getColor() == this.getTurnColor())
                    coordinates.add(coordinate);
            }
        }
        return coordinates;
    }

    private boolean isBlocked(Coordinate coordinate) {
        for (int i = 1; i <= 2; i++)
            for (Coordinate target : coordinate.getDiagonalCoordinates(i))
                if (new Movement(new Coordinate[]{coordinate, target}).isCorrect(this.board, this.turn) == null)
                    return false;
        return true;
    }

    public void cancel() {
        for (Coordinate coordinate : this.getCoordinatesWithActualColor())
            this.board.remove(coordinate);
        this.turn.change();
    }

    public Color getColor(Coordinate coordinate) {
        assert coordinate != null;
        return this.board.getColor(coordinate);
    }

    public Color getTurnColor() {
        return this.turn.getColor();
    }

    public Piece getPiece(Coordinate coordinate) {
        assert coordinate != null;
        return this.board.getPiece(coordinate);
    }

    public int getDimension() {
        return Coordinate.getDimension();
    }

    @Override
    public String toString() {
        return this.board + "\n" + this.turn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((board == null) ? 0 : board.hashCode());
        result = prime * result + ((turn == null) ? 0 : turn.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (board == null) {
            if (other.board != null)
                return false;
        } else if (!board.equals(other.board))
            return false;
        if (turn == null) {
            if (other.turn != null)
                return false;
        } else if (!turn.equals(other.turn))
            return false;
        return true;
    }

}