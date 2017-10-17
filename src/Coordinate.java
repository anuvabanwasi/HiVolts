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
	 * Override equals and hashcode to check for object equivalence
	 * https://stackoverflow.com/questions/13134050/when-do-i-need-to-override-equals-and-hashcode-methods
	 * @author anuva
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
