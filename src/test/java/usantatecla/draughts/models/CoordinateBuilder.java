package usantatecla.draughts.models;

public class CoordinateBuilder {

    private int row;
    private int column;

    public CoordinateBuilder() {
        this.row = 0;
        this.column = Coordinate.getDimension() - 1;
    }

    public CoordinateBuilder row(int row) {
        this.row = row;
        return this;
    }

    public CoordinateBuilder column(int column) {
        this.column = column;
        return this;
    }

    public Coordinate build() {
        return new Coordinate(this.row, this.column);
    }

}
