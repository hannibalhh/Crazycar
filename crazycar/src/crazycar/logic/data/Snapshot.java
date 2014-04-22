package crazycar.logic.data;

import java.util.List;

public class Snapshot {

	private final List<Roxel> roxels;

	private Snapshot(List<Roxel> roxels) {
		this.roxels = roxels;
	}

	public List<Roxel> getRoxels() {
		return roxels;
	}

	@Override
	public String toString() {
		return "Snapshot [roxels=" + roxels + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roxels == null) ? 0 : roxels.hashCode());
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
		Snapshot other = (Snapshot) obj;
		if (roxels == null) {
			if (other.roxels != null)
				return false;
		} else if (!roxels.equals(other.roxels))
			return false;
		return true;
	}

	public static Snapshot valueOf(List<Roxel> l) {
		return new Snapshot(l);
	}
}
