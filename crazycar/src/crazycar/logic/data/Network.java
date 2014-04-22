package crazycar.logic.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Network {
	/**
	 * Paritionierung nach Kacheln (ortsbezogen), immer eine Krezung darin, es
	 * kšnnen beliebig viele Kacheln auf einem Host ausgefŸhrt werden -> Space
	 * Routing Annotation
	 */

	private final Set<Roxel> grid;
	private final Location size;

	public Network(Set<Roxel> grid, Location size) {
		this.grid = grid;
		this.size = size;
	}

	public static final Network createSimple(int size) {
		if (size % 3 != 0)
			throw new UnsupportedOperationException("size have to be a multiple of 3");
		Set<Roxel> grid = new HashSet<Roxel>();
		for (int column = 0; column < size; column += 1) {
			for (int row = 0; row < size; row += 1) {
				// not both are even numbers than there must be a street
				if (row % 2 != 0 || column % 2 != 0)
					grid.add(Roxel.empty(decideSimpleDirection(column, row), Location.valueOf(column, row)));
			}
		}
		return new Network(grid, Location.valueOf(size, size));
	}

	public static Direction decideSimpleDirection(int column, int row) {
		// both odd then croassroads
		if (row % 2 != 0 && column % 2 != 0)
			return Direction.nodecide;
		else if (column % 2 != 0) {
			return Direction.south;
		} else {
			return Direction.east;
		}
	}

	public static final Network create(Location size) {
		return create(size, 1);
	}

	public static final Network create(Location size, int crossroadsDistance) {
		Set<Roxel> grid = new HashSet<Roxel>();
		int i = 0;
		List<Integer> currents = new ArrayList<Integer>();
		int current = streetByCrossroadsDistance(0, crossroadsDistance);
		currents.add(current);
		while (current <= Math.max(size.getColumn(), size.getRow())) {
			for (int column = 0; column < size.getColumn(); column += 1) {
				for (int row = 0; row < size.getRow(); row += 1) {
					if (current == column || current == row)
						grid.add(Roxel.empty(decideDirection(column, row, currents), Location.valueOf(column, row)));
				}
			}
			i += 1;
			current = streetByCrossroadsDistance(i, crossroadsDistance);
			currents.add(current);
		}
		return new Network(grid, size);
	}

	public static Direction decideDirection(int column, int row, List<Integer> currents) {
		if (currents.contains(column) && currents.contains(row)) {
			return Direction.nodecide;
		} else if (row < column) {
			return Direction.south;
		} else {
			return Direction.east;
		}
	}

	public static int streetByCrossroadsDistance(int n, int crossroadsDistance) {
		return n * crossroadsDistance + crossroadsDistance + n;
	}

	public Network add(Network n) {
		Set<Roxel> g = new HashSet<Roxel>();
		g.addAll(grid);
		g.addAll(n.getGrid());
		return new Network(g, Location.max(n.getSize(), size));
	}

	public Set<Roxel> getGrid() {
		return grid;
	}

	public Location getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "Network[size=" + size + "]" + toString(grid);
	}

	public String toStringWithCount() {
		return "Network[size=" + size + ",  count(Direction.nodecide)=" + countDirection(grid, Direction.nodecide) + "] with" + toString(grid, Direction.nodecide);
	}

	public <E> String toString(Iterable<E> l) {
		String s = "";
		for (E e : l) {
			s += "\n" + e;
		}
		return s;
	}

	public String toString(Iterable<Roxel> l, Direction d) {
		String s = "";
		for (Roxel r : l) {
			if (r.getDirection().equals(d)) {
				s += "\n" + r;
			}
		}
		return s;
	}

	public int countDirection(Iterable<Roxel> l, Direction d) {
		int i = 0;
		if (d == null) {
			return i;
		}
		for (Roxel r : l) {
			if (r.getDirection().equals(d)) {
				i += 1;
			}
		}
		return i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grid == null) ? 0 : grid.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
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
		Network other = (Network) obj;
		if (grid == null) {
			if (other.grid != null)
				return false;
		} else if (!grid.equals(other.grid))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}

}
