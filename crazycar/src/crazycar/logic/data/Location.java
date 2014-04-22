package crazycar.logic.data;

import crazycar.Crazycar;

public class Location {

	private final int column;
	private final int row;
	
	private Location(int column, int row) {
		this.column = column;
		this.row = row;
	}
	
	public static Location valueOf(int column, int row){
		return new Location(column ,row);
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public Location add(Location l){
		return Location.valueOf((column+l.getColumn()) % Crazycar.size.column, (row+l.getRow()) % Crazycar.size.row);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		Location other = (Location) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[column=" + column + ",row=" + row + "]";
	}

	public static Location max(Location l, Location l2) {
		return Location.valueOf(Math.max(l.getColumn(), l2.getColumn()), Math.max(l.getRow(), l2.getRow()));
	}
}
