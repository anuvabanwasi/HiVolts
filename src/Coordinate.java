public class Coordinate {

    public int x, y;

    public Coordinate(int x, int y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate: {" + Integer.toString(x) + "," + Integer.toString(y) + "}";
    }
}
