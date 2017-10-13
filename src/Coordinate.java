/**
 * Represents the coordinate of each cell.
 * Each coordinate is identified by the row and column of the cell.
 *
 * @author Quintin Dwight
 * @author Anuva Banwasi
 */
public class Coordinate {

    public int x, y;

    public Coordinate(int x, int y) {

        this.x = x;
        this.y = y;
    }


	public void setY(int y) {
		this.y = y;
	}

	/**
	 * We override the equals method because this is a value class (like a struct).
	 * Coordinates with the same x and y are treated as equal.
	 *
	 * @param obj Other object
	 * @return Whether or not they are equal
	 */
	@Override public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "Coordinate: {" + Integer.toString(x) + "," + Integer.toString(y) + "}";
    }
}
