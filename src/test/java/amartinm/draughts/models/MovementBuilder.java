package amartinm.draughts.models;

public class MovementBuilder {

    private int pair;
    private Coordinate[] coordinates;

    public MovementBuilder() {
    }

    public MovementBuilder pair(int pair) {
        this.pair = pair;
        return this;
    }

    public MovementBuilder coordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Movement build() {
        Movement movement = new Movement(this.coordinates);
        for (int i = 0; i < pair; i++)
            movement.next();
        return movement;
    }

}
