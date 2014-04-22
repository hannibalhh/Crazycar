package crazycar.logic.data;

public class Car {

	public final static Car ferrari = new Car();
	public final static EmptyCar empty = ferrari.new EmptyCar();

	private Car() {}

	public boolean isEmpty() {
		return false;
	}

	public static Car valueOf(boolean e) {
		if (e) {
			return empty;
		} else {
			return ferrari;
		}
	}

	@Override
	public String toString() {
		return "Ferrari";
	}

	private class EmptyCar extends Car {
		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public String toString() {
			return "Empty";
		}
	}
}
