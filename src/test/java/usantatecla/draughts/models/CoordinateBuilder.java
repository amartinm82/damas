package usantatecla.draughts.models;

class CoordinateBuilder {

    private int row;
    private int column;

    CoordinateBuilder() {
        this.row = 0;
        this.column = 7;
    }

    CoordinateBuilder row(int row) {
        this.row = row;
        return this;
    }

    CoordinateBuilder column(int column) {
        this.column = column;
        return this;
    }

    Coordinate build() {
        return new Coordinate(this.row, this.column);
    }

}
